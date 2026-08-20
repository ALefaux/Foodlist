package io.github.alefaux.foodlist.core.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberGoogleSignInContext(): GoogleSignInContext {
    val context = LocalContext.current
    return remember(context) { GoogleSignInContext(context) }
}
