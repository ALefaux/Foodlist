package io.github.alefaux.foodlist.core.theme

import android.content.Context

class AndroidThemePreferencesStorage(context: Context) : ThemePreferencesStorage {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getDarkThemeEnabled(): Boolean? =
        if (prefs.contains(KEY_DARK_THEME_ENABLED)) prefs.getBoolean(KEY_DARK_THEME_ENABLED, false) else null

    override fun setDarkThemeEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_THEME_ENABLED, enabled).apply()
    }

    private companion object {
        const val PREFS_NAME = "foodlist_theme_prefs"
        const val KEY_DARK_THEME_ENABLED = "dark_theme_enabled"
    }
}
