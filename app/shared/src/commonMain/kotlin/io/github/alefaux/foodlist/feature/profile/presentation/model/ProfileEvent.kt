package io.github.alefaux.foodlist.feature.profile.presentation.model

sealed interface ProfileEvent {
    data object TestDataCreated : ProfileEvent
    data object TestDataDeleted : ProfileEvent
}
