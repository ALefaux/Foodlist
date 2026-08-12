package io.github.alefaux.foodlist.feature.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = onGoogleClick,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Google")
        }

        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = onAppleClick,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Apple")
        }
    }
}
