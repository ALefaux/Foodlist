package io.github.alefaux.foodlist.search.openfoodfacts

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OpenFoodFactsClientTest {

    private fun clientWith(engine: MockEngine) = HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    @Test
    fun `maps matching hits into search results`() = runBlocking {
        val engine = MockEngine {
            respond(
                content = """
                    {
                      "hits": [
                        {
                          "code": "3017620422003",
                          "product_name": "Nutella",
                          "quantity": "400 g",
                          "brands": ["Nutella", "Ferrero"],
                          "categories_tags": ["en:spreads", "fr:pates-a-tartiner"]
                        }
                      ]
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = OpenFoodFactsClient(clientWith(engine))

        val results = client.search("nutella")

        assertEquals(1, results.size)
        val result = results.first()
        assertEquals("3017620422003", result.ean)
        assertEquals("Nutella", result.name)
        assertEquals("400 g", result.quantity)
        assertEquals("Nutella", result.brand)
        assertEquals("pates-a-tartiner", result.category)
    }

    @Test
    fun `filters out hits without a product name`() = runBlocking {
        val engine = MockEngine {
            respond(
                content = """
                    {
                      "hits": [
                        { "code": "1", "product_name": null, "brands": [], "categories_tags": [] },
                        { "code": "2", "product_name": "", "brands": [], "categories_tags": [] },
                        { "code": "3", "product_name": "Real Product", "brands": [], "categories_tags": [] }
                      ]
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = OpenFoodFactsClient(clientWith(engine))

        val results = client.search("product")

        assertEquals(1, results.size)
        assertEquals("Real Product", results.first().name)
    }

    @Test
    fun `returns an empty list when there are no hits`() = runBlocking {
        val engine = MockEngine {
            respond(
                content = """{ "hits": [] }""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = OpenFoodFactsClient(clientWith(engine))

        assertTrue(client.search("nonexistent").isEmpty())
    }

    @Test
    fun `returns an empty list when OpenFoodFacts is unavailable`() = runBlocking {
        val engine = MockEngine {
            respondError(HttpStatusCode.ServiceUnavailable)
        }
        val client = OpenFoodFactsClient(clientWith(engine))

        assertTrue(client.search("nutella").isEmpty())
    }

    @Test
    fun `returns an empty list when the response body is unexpected`() = runBlocking {
        val engine = MockEngine {
            respond(
                content = "<html><body>not json</body></html>",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Text.Html.toString())
            )
        }
        val client = OpenFoodFactsClient(clientWith(engine))

        assertTrue(client.search("nutella").isEmpty())
    }
}
