package io.github.alefaux.foodlist

import io.github.alefaux.foodlist.auth.dto.AuthResponse
import io.github.alefaux.foodlist.auth.dto.LoginRequest
import io.github.alefaux.foodlist.auth.dto.RegisterRequest
import io.github.alefaux.foodlist.auth.dto.UserResponse
import io.github.alefaux.foodlist.database.DatabaseFactory
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json

class AuthRoutesTest {

    private val json = Json { ignoreUnknownKeys = true }

    private fun uniqueDbUrl() = "jdbc:h2:mem:auth-test-${Random.nextLong()};DB_CLOSE_DELAY=-1"

    @Test
    fun testRegisterAndLoginFlow() = testApplication {
        DatabaseFactory.init(uniqueDbUrl())
        application { module() }

        val registerBody = json.encodeToString(
            RegisterRequest.serializer(),
            RegisterRequest(name = "Ada Lovelace", email = "ada@example.com", password = "password123")
        )

        val registerResponse = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(registerBody)
        }
        assertEquals(HttpStatusCode.Created, registerResponse.status)

        val registerAuth = json.decodeFromString(AuthResponse.serializer(), registerResponse.bodyAsText())
        assertEquals("ada@example.com", registerAuth.user.email)
        assertTrue(registerAuth.token.isNotBlank())

        val duplicateResponse = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(registerBody)
        }
        assertEquals(HttpStatusCode.Conflict, duplicateResponse.status)

        val loginBody = json.encodeToString(
            LoginRequest.serializer(),
            LoginRequest(email = "ada@example.com", password = "password123")
        )
        val loginResponse = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(loginBody)
        }
        assertEquals(HttpStatusCode.OK, loginResponse.status)

        val loginAuth = json.decodeFromString(AuthResponse.serializer(), loginResponse.bodyAsText())
        assertEquals(registerAuth.user.id, loginAuth.user.id)

        val wrongLoginBody = json.encodeToString(
            LoginRequest.serializer(),
            LoginRequest(email = "ada@example.com", password = "wrong-password")
        )
        val wrongLoginResponse = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(wrongLoginBody)
        }
        assertEquals(HttpStatusCode.Unauthorized, wrongLoginResponse.status)

        val unauthorizedMe = client.get("/auth/me")
        assertEquals(HttpStatusCode.Unauthorized, unauthorizedMe.status)

        val meResponse = client.get("/auth/me") {
            header("Authorization", "Bearer ${loginAuth.token}")
        }
        assertEquals(HttpStatusCode.OK, meResponse.status)

        val me = json.decodeFromString(UserResponse.serializer(), meResponse.bodyAsText())
        assertEquals("ada@example.com", me.email)
    }

    @Test
    fun testRegisterWithWeakPasswordFails() = testApplication {
        DatabaseFactory.init(uniqueDbUrl())
        application { module() }

        val body = json.encodeToString(
            RegisterRequest.serializer(),
            RegisterRequest(name = "Weak Pass", email = "weak@example.com", password = "short")
        )

        val response = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}
