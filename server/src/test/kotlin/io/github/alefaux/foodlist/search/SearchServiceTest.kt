package io.github.alefaux.foodlist.search

import io.github.alefaux.foodlist.database.DatabaseFactory
import io.github.alefaux.foodlist.database.ProductRepository
import io.github.alefaux.foodlist.database.Products
import io.github.alefaux.foodlist.search.openfoodfacts.OpenFoodFactsClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class SearchServiceTest {

    private fun uniqueDbUrl() = "jdbc:h2:mem:search-test-${Random.nextLong()};DB_CLOSE_DELAY=-1"

    private fun openFoodFactsClientRespondingWith(hitsJson: String) = OpenFoodFactsClient(
        HttpClient(
            MockEngine {
                respond(
                    content = """{ "hits": $hitsJson }""",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                )
            }
        ) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    )

    private fun insertProduct(name: String, ean: String) {
        transaction {
            Products.insert {
                it[userId] = 1L
                it[Products.name] = name
                it[expirationDate] = null
                it[Products.ean] = ean
                it[discardedDate] = null
                it[storageUnitId] = null
                it[quantity] = "1"
                it[category] = "Other"
            }
        }
    }

    @Test
    fun `throws when the query is blank`() {
        DatabaseFactory.init(uniqueDbUrl())
        val searchService = SearchService(ProductRepository(), openFoodFactsClientRespondingWith("[]"))

        assertFailsWith<InvalidQueryException> {
            runBlocking { searchService.search("   ") }
        }
    }

    @Test
    fun `throws when the query is null`() {
        DatabaseFactory.init(uniqueDbUrl())
        val searchService = SearchService(ProductRepository(), openFoodFactsClientRespondingWith("[]"))

        assertFailsWith<InvalidQueryException> {
            runBlocking { searchService.search(null) }
        }
    }

    @Test
    fun `returns OpenFoodFacts results when available, without querying the database`() = runBlocking {
        DatabaseFactory.init(uniqueDbUrl())
        insertProduct(name = "Nutella from DB", ean = "111")

        val searchService = SearchService(
            ProductRepository(),
            openFoodFactsClientRespondingWith(
                """
                [
                  {
                    "code": "3017620422003",
                    "product_name": "Nutella",
                    "quantity": "400 g",
                    "brands": ["Nutella"],
                    "categories_tags": ["fr:pates-a-tartiner"]
                  }
                ]
                """.trimIndent()
            )
        )

        val results = searchService.search("nutella")

        assertEquals(1, results.size)
        assertEquals("3017620422003", results.first().ean)
        assertEquals("Nutella", results.first().name)
        assertTrue(results.none { it.name == "Nutella from DB" })
    }

    @Test
    fun `falls back to the database when OpenFoodFacts has no results`() = runBlocking {
        DatabaseFactory.init(uniqueDbUrl())
        insertProduct(name = "Homemade Jam", ean = "222")

        val searchService = SearchService(ProductRepository(), openFoodFactsClientRespondingWith("[]"))

        val results = searchService.search("Homemade Jam")

        assertEquals(1, results.size)
        assertEquals("222", results.first().ean)
        assertEquals("Homemade Jam", results.first().name)
    }

    @Test
    fun `falls back to the database and returns nothing when neither source has a match`() = runBlocking {
        DatabaseFactory.init(uniqueDbUrl())

        val searchService = SearchService(ProductRepository(), openFoodFactsClientRespondingWith("[]"))

        assertTrue(searchService.search("nonexistent-product").isEmpty())
    }
}
