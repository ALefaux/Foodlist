package io.github.alefaux.foodlist.core.theme

interface ThemePreferencesStorage {
    /** Returns the persisted preference, or `null` when the user has never set one (follow system). */
    fun getDarkThemeEnabled(): Boolean?
    fun setDarkThemeEnabled(enabled: Boolean)
}
