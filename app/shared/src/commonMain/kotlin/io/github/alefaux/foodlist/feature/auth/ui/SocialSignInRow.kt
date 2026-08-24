package io.github.alefaux.foodlist.feature.auth.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.google_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SocialSignInRow(
    onGoogleClick: () -> Unit,
    onAppleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GoogleSignInButton(
            modifier = Modifier.weight(1f),
            onClick = onGoogleClick
        )

        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = onAppleClick,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Apple")
        }
    }
}

// Colors and layout follow Google's "Sign in with Google" branding guidelines
// (https://developers.google.com/identity/branding-guidelines) so the button stays
// instantly recognizable rather than blending into the app's own theme.
@Composable
private fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val backgroundColor = if (darkTheme) Color(0xFF131314) else Color.White
    val borderColor = if (darkTheme) Color(0xFF8E918F) else Color(0xFF747775)
    val contentColor = if (darkTheme) Color(0xFFE3E3E3) else Color(0xFF1F1F1F)

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Image(
            painter = painterResource(Res.drawable.google_logo),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Google",
            fontWeight = FontWeight.Medium
        )
    }
}
