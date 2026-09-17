package io.github.alefaux.foodlist.core.theme

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeRepository(private val storage: ThemePreferencesStorage) {

    private val _isDarkThemeEnabled = MutableStateFlow(storage.getDarkThemeEnabled())
    val isDarkThemeEnabled: StateFlow<Boolean?> = _isDarkThemeEnabled.asStateFlow()

    fun setDarkThemeEnabled(enabled: Boolean) {
        storage.setDarkThemeEnabled(enabled)
        _isDarkThemeEnabled.value = enabled
    }
}
