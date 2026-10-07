package io.github.alefaux.foodlist.feature.profile.presentation.model

import io.github.alefaux.foodlist.feature.auth.domain.AuthUser

data class ProfileUiState(
    val user: AuthUser? = null,
    val isDarkThemeEnabled: Boolean? = null,
    val isDebug: Boolean = false
)
