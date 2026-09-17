package io.github.alefaux.foodlist.feature.profile.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.profile_guest_create_account_button
import foodlist.app.shared.generated.resources.profile_guest_message
import foodlist.app.shared.generated.resources.profile_guest_sign_in_button
import foodlist.app.shared.generated.resources.profile_guest_title
import foodlist.app.shared.generated.resources.profile_top_bar_title
import io.github.alefaux.foodlist.core.component.HomeHeader
import org.jetbrains.compose.resources.stringResource

@Composable
fun GuestProfilePane(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        HomeHeader(
            title = stringResource(Res.string.profile_top_bar_title)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = stringResource(Res.string.profile_guest_title),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = stringResource(Res.string.profile_guest_message),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSignInClick
                ) {
                    Text(stringResource(Res.string.profile_guest_sign_in_button))
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSignUpClick
                ) {
                    Text(stringResource(Res.string.profile_guest_create_account_button))
                }
            }
        }
    }
}
