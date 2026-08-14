package io.github.alefaux.foodlist.feature.auth.data.repository

import io.github.alefaux.foodlist.core.network.NetworkConfig
import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.SessionDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.entity.SessionEntity
import io.github.alefaux.foodlist.feature.auth.data.remote.AuthApiException
import io.github.alefaux.foodlist.feature.auth.data.remote.AuthResponseDto
import io.github.alefaux.foodlist.feature.auth.data.remote.ErrorResponseDto
import io.github.alefaux.foodlist.feature.auth.data.remote.LoginRequestDto
import io.github.alefaux.foodlist.feature.auth.data.remote.RegisterRequestDto
import io.github.alefaux.foodlist.feature.auth.domain.AuthUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionDao: SessionDao,
    private val productDao: ProductDao,
    private val storageDao: StorageDao
) : AuthRepository {

    override suspend fun signUp(name: String, email: String, password: String): Result<AuthUser> =
        authenticate {
            httpClient.post("${NetworkConfig.baseUrl}/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequestDto(name = name, email = email, password = password))
            }
        }

    override suspend fun signIn(email: String, password: String): Result<AuthUser> =
        authenticate {
            httpClient.post("${NetworkConfig.baseUrl}/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequestDto(email = email, password = password))
            }
        }

    override suspend fun signOut() {
        sessionDao.upsert(SessionEntity(token = null, userId = null, userName = null, userEmail = null))
        productDao.deleteAll()
        storageDao.deleteAll()
    }

    override fun observeCurrentUser(): Flow<AuthUser?> =
        sessionDao.observe().map { session ->
            if (session?.token != null && session.userId != null) {
                AuthUser(
                    id = session.userId,
                    name = session.userName.orEmpty(),
                    email = session.userEmail.orEmpty()
                )
            } else {
                null
            }
        }

    private suspend fun authenticate(request: suspend () -> HttpResponse): Result<AuthUser> {
        val response = try {
            request()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(
                AuthApiException("Couldn't connect to the server. Check your network and try again.")
            )
        }

        if (!response.status.isSuccess()) {
            val error = runCatching { response.body<ErrorResponseDto>() }.getOrNull()
            return Result.failure(AuthApiException(error?.message ?: "Something went wrong."))
        }

        return try {
            val auth = response.body<AuthResponseDto>()
            sessionDao.upsert(
                SessionEntity(
                    token = auth.token,
                    userId = auth.user.id,
                    userName = auth.user.name,
                    userEmail = auth.user.email
                )
            )
            Result.success(AuthUser(id = auth.user.id, name = auth.user.name, email = auth.user.email))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(AuthApiException("Received an unexpected response from the server."))
        }
    }
}
