package io.github.alefaux.foodlist.feature.storage.domain

interface AddStorageUnitUseCase {
    suspend operator fun invoke(name: String)
}
