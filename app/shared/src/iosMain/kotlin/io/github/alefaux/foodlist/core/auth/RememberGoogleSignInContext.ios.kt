package io.github.alefaux.foodlist.core.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberGoogleSignInContext(): GoogleSignInContext = remember { GoogleSignInContext() }
