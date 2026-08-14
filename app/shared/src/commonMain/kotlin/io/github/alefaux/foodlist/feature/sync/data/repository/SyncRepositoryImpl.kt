package io.github.alefaux.foodlist.feature.sync.data.repository

import io.github.alefaux.foodlist.core.network.NetworkConfig
import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.SessionDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.feature.sync.data.remote.SyncApiException
import io.github.alefaux.foodlist.feature.sync.data.remote.SyncProductDto
import io.github.alefaux.foodlist.feature.sync.data.remote.SyncRequestDto
import io.github.alefaux.foodlist.feature.sync.data.remote.SyncStorageUnitDto
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException

class SyncRepositoryImpl(
    private val httpClient: HttpClient,
    private val productDao: ProductDao,
    private val storageDao: StorageDao,
    private val sessionDao: SessionDao
) : SyncRepository {

    override suspend fun syncLocalDataToServer(): Result<Unit> {
        val token = sessionDao.get()?.token
            ?: return Result.failure(SyncApiException("You must be signed in to sync."))

        return try {
            val storageUnits = storageDao.getAll()
            val products = productDao.getAll()

            val request = SyncRequestDto(
                storageUnits = storageUnits.map { SyncStorageUnitDto(localId = it.id, name = it.name) },
                products = products.map { product ->
                    SyncProductDto(
                        localId = product.id,
                        name = product.name,
                        expirationDate = product.expirationDate?.toEpochMilliseconds(),
                        ean = product.ean,
                        discardedDate = product.discardedDate?.toEpochMilliseconds(),
                        localStorageId = product.storageId,
                        quantity = product.quantity,
                        category = product.category
                    )
                }
            )

            val response = httpClient.post("${NetworkConfig.baseUrl}/sync") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                Result.failure(SyncApiException("Couldn't sync your data to the server."))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(SyncApiException("Couldn't sync your data to the server."))
        }
    }
}
