package io.github.alefaux.foodlist.core.theme

import platform.Foundation.NSUserDefaults

class IosThemePreferencesStorage : ThemePreferencesStorage {

    private val defaults = NSUserDefaults.standardUserDefaults

    override fun getDarkThemeEnabled(): Boolean? =
        if (defaults.objectForKey(KEY_DARK_THEME_ENABLED) != null) {
            defaults.boolForKey(KEY_DARK_THEME_ENABLED)
        } else {
            null
        }

    override fun setDarkThemeEnabled(enabled: Boolean) {
        defaults.setBool(enabled, forKey = KEY_DARK_THEME_ENABLED)
    }

    private companion object {
        const val KEY_DARK_THEME_ENABLED = "dark_theme_enabled"
    }
}
