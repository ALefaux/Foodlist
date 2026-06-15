package io.github.alefaux.foodlist.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Light palette ────────────────────────────────────────────────────────────

private val md_light_primary = Color(0xFF37602C)
private val md_light_onPrimary = Color(0xFFFFFFFF)
private val md_light_primaryContainer = Color(0xFF4F7942)
private val md_light_onPrimaryContainer = Color(0xFFD3FFC1)
private val md_light_inversePrimary = Color(0xFFA4D393)
private val md_light_secondary = Color(0xFF8D4F11)
private val md_light_onSecondary = Color(0xFFFFFFFF)
private val md_light_secondaryContainer = Color(0xFFFEAC67)
private val md_light_onSecondaryContainer = Color(0xFF773E00)
private val md_light_tertiary = Color(0xFF884400)
private val md_light_onTertiary = Color(0xFFFFFFFF)
private val md_light_tertiaryContainer = Color(0xFFAC5800)
private val md_light_onTertiaryContainer = Color(0xFFFFF0E8)
private val md_light_error = Color(0xFFBA1A1A)
private val md_light_onError = Color(0xFFFFFFFF)
private val md_light_errorContainer = Color(0xFFFFDAD6)
private val md_light_onErrorContainer = Color(0xFF93000A)
private val md_light_background = Color(0xFFF9F9F8)
private val md_light_onBackground = Color(0xFF191C1C)
private val md_light_surface = Color(0xFFF9F9F8)
private val md_light_surfaceDim = Color(0xFFD9DAD9)
private val md_light_surfaceBright = Color(0xFFF9F9F8)
private val md_light_surfaceContainerLowest = Color(0xFFFFFFFF)
private val md_light_surfaceContainerLow = Color(0xFFF3F4F3)
private val md_light_surfaceContainer = Color(0xFFEDEEED)
private val md_light_surfaceContainerHigh = Color(0xFFE7E8E7)
private val md_light_surfaceContainerHighest = Color(0xFFE1E3E2)
private val md_light_onSurface = Color(0xFF191C1C)
private val md_light_surfaceVariant = Color(0xFFE1E3E2)
private val md_light_onSurfaceVariant = Color(0xFF42493E)
private val md_light_inverseSurface = Color(0xFF2E3131)
private val md_light_inverseOnSurface = Color(0xFFF0F1F0)
private val md_light_outline = Color(0xFF73796D)
private val md_light_outlineVariant = Color(0xFFC2C9BB)
private val md_light_surfaceTint = Color(0xFF3F6833)

// ── Dark palette (M3-derived) ────────────────────────────────────────────────

private val md_dark_primary = Color(0xFFA4D393)
private val md_dark_onPrimary = Color(0xFF0A3909)
private val md_dark_primaryContainer = Color(0xFF28501E)
private val md_dark_onPrimaryContainer = Color(0xFFC0F0AD)
private val md_dark_inversePrimary = Color(0xFF37602C)
private val md_dark_secondary = Color(0xFFFFB77D)
private val md_dark_onSecondary = Color(0xFF4A2700)
private val md_dark_secondaryContainer = Color(0xFF6E3900)
private val md_dark_onSecondaryContainer = Color(0xFFFFDCC3)
private val md_dark_tertiary = Color(0xFFFFB783)
private val md_dark_onTertiary = Color(0xFF4A2200)
private val md_dark_tertiaryContainer = Color(0xFF713700)
private val md_dark_onTertiaryContainer = Color(0xFFFFDCC5)
private val md_dark_error = Color(0xFFFFB4AB)
private val md_dark_onError = Color(0xFF690005)
private val md_dark_errorContainer = Color(0xFF93000A)
private val md_dark_onErrorContainer = Color(0xFFFFDAD6)
private val md_dark_background = Color(0xFF111413)
private val md_dark_onBackground = Color(0xFFE1E3E2)
private val md_dark_surface = Color(0xFF111413)
private val md_dark_surfaceDim = Color(0xFF111413)
private val md_dark_surfaceBright = Color(0xFF363939)
private val md_dark_surfaceContainerLowest = Color(0xFF0C0F0E)
private val md_dark_surfaceContainerLow = Color(0xFF191C1C)
private val md_dark_surfaceContainer = Color(0xFF1D2020)
private val md_dark_surfaceContainerHigh = Color(0xFF282B2A)
private val md_dark_surfaceContainerHighest = Color(0xFF323535)
private val md_dark_onSurface = Color(0xFFE1E3E2)
private val md_dark_surfaceVariant = Color(0xFF42493E)
private val md_dark_onSurfaceVariant = Color(0xFFC2C9BB)
private val md_dark_inverseSurface = Color(0xFFE1E3E2)
private val md_dark_inverseOnSurface = Color(0xFF2E3131)
private val md_dark_outline = Color(0xFF8C9386)
private val md_dark_outlineVariant = Color(0xFF42493E)
private val md_dark_surfaceTint = Color(0xFFA4D393)

// ── Color schemes ────────────────────────────────────────────────────────────

private val LightColorScheme = lightColorScheme(
    primary = md_light_primary,
    onPrimary = md_light_onPrimary,
    primaryContainer = md_light_primaryContainer,
    onPrimaryContainer = md_light_onPrimaryContainer,
    inversePrimary = md_light_inversePrimary,
    secondary = md_light_secondary,
    onSecondary = md_light_onSecondary,
    secondaryContainer = md_light_secondaryContainer,
    onSecondaryContainer = md_light_onSecondaryContainer,
    tertiary = md_light_tertiary,
    onTertiary = md_light_onTertiary,
    tertiaryContainer = md_light_tertiaryContainer,
    onTertiaryContainer = md_light_onTertiaryContainer,
    error = md_light_error,
    onError = md_light_onError,
    errorContainer = md_light_errorContainer,
    onErrorContainer = md_light_onErrorContainer,
    background = md_light_background,
    onBackground = md_light_onBackground,
    surface = md_light_surface,
    onSurface = md_light_onSurface,
    surfaceVariant = md_light_surfaceVariant,
    onSurfaceVariant = md_light_onSurfaceVariant,
    surfaceTint = md_light_surfaceTint,
    inverseSurface = md_light_inverseSurface,
    inverseOnSurface = md_light_inverseOnSurface,
    outline = md_light_outline,
    outlineVariant = md_light_outlineVariant,
    surfaceBright = md_light_surfaceBright,
    surfaceDim = md_light_surfaceDim,
    surfaceContainerLowest = md_light_surfaceContainerLowest,
    surfaceContainerLow = md_light_surfaceContainerLow,
    surfaceContainer = md_light_surfaceContainer,
    surfaceContainerHigh = md_light_surfaceContainerHigh,
    surfaceContainerHighest = md_light_surfaceContainerHighest,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_dark_primary,
    onPrimary = md_dark_onPrimary,
    primaryContainer = md_dark_primaryContainer,
    onPrimaryContainer = md_dark_onPrimaryContainer,
    inversePrimary = md_dark_inversePrimary,
    secondary = md_dark_secondary,
    onSecondary = md_dark_onSecondary,
    secondaryContainer = md_dark_secondaryContainer,
    onSecondaryContainer = md_dark_onSecondaryContainer,
    tertiary = md_dark_tertiary,
    onTertiary = md_dark_onTertiary,
    tertiaryContainer = md_dark_tertiaryContainer,
    onTertiaryContainer = md_dark_onTertiaryContainer,
    error = md_dark_error,
    onError = md_dark_onError,
    errorContainer = md_dark_errorContainer,
    onErrorContainer = md_dark_onErrorContainer,
    background = md_dark_background,
    onBackground = md_dark_onBackground,
    surface = md_dark_surface,
    onSurface = md_dark_onSurface,
    surfaceVariant = md_dark_surfaceVariant,
    onSurfaceVariant = md_dark_onSurfaceVariant,
    surfaceTint = md_dark_surfaceTint,
    inverseSurface = md_dark_inverseSurface,
    inverseOnSurface = md_dark_inverseOnSurface,
    outline = md_dark_outline,
    outlineVariant = md_dark_outlineVariant,
    surfaceBright = md_dark_surfaceBright,
    surfaceDim = md_dark_surfaceDim,
    surfaceContainerLowest = md_dark_surfaceContainerLowest,
    surfaceContainerLow = md_dark_surfaceContainerLow,
    surfaceContainer = md_dark_surfaceContainer,
    surfaceContainerHigh = md_dark_surfaceContainerHigh,
    surfaceContainerHighest = md_dark_surfaceContainerHighest,
)

// ── Typography ───────────────────────────────────────────────────────────────
// Inter font files should be placed in commonMain/composeResources/font/ and
// wired up here once available. FontFamily.Default is used in the meantime.

private val FoodlistTypography = Typography(
    // headline-lg: 32 / 700 / lh 40 / ls -0.02em
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.64).sp,
    ),
    // headline-lg-mobile: 26 / 700 / lh 32 / ls -0.01em
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.26).sp,
    ),
    // headline-md: 24 / 600 / lh 32
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    // body-lg: 16 / 400 / lh 24
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    ),
    // body-md: 14 / 400 / lh 20
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    // label-md: 12 / 600 / lh 16 / ls 0.05em
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.6.sp,
    ),
    // label-sm: 11 / 500 / lh 14
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp,
    ),
)

// ── Theme entry point ────────────────────────────────────────────────────────

@Composable
fun FoodlistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = FoodlistTypography,
        content = content,
    )
}