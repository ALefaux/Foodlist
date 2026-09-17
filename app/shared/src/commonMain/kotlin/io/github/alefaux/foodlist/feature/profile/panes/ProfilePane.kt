package io.github.alefaux.foodlist.feature.profile.panes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.profile_footer
import foodlist.app.shared.generated.resources.profile_household_member_count
import foodlist.app.shared.generated.resources.profile_household_title
import foodlist.app.shared.generated.resources.profile_invite_member_button
import foodlist.app.shared.generated.resources.profile_member_owner
import foodlist.app.shared.generated.resources.profile_member_you
import foodlist.app.shared.generated.resources.profile_settings_account
import foodlist.app.shared.generated.resources.profile_settings_notifications
import foodlist.app.shared.generated.resources.profile_settings_notifications_on
import foodlist.app.shared.generated.resources.profile_settings_theme
import foodlist.app.shared.generated.resources.profile_settings_title
import foodlist.app.shared.generated.resources.profile_settings_units
import foodlist.app.shared.generated.resources.profile_settings_units_metric
import foodlist.app.shared.generated.resources.profile_sign_out_button
import io.github.alefaux.foodlist.feature.profile.ui.HouseholdMemberRow
import io.github.alefaux.foodlist.feature.profile.ui.ProfileHeaderCard
import io.github.alefaux.foodlist.feature.profile.ui.SettingsRow
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfilePane(
    userName: String,
    isDarkThemeEnabled: Boolean?,
    onInviteMemberClick: () -> Unit,
    onAccountClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkThemeChecked = isDarkThemeEnabled ?: isSystemInDarkTheme()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileHeaderCard(name = userName)

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.profile_household_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(Res.string.profile_household_member_count),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            ) {
                HouseholdMemberRow(
                    initials = userInitials(userName),
                    name = stringResource(Res.string.profile_member_you),
                    role = stringResource(Res.string.profile_member_owner)
                )
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onInviteMemberClick,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(Res.string.profile_invite_member_button),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = stringResource(Res.string.profile_settings_title),
                style = MaterialTheme.typography.headlineSmall
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            ) {
                Column {
                    SettingsRow(
                        icon = Icons.Filled.ManageAccounts,
                        label = stringResource(Res.string.profile_settings_account),
                        onClick = onAccountClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    HorizontalDivider()
                    SettingsRow(
                        icon = Icons.Filled.Notifications,
                        label = stringResource(Res.string.profile_settings_notifications)
                    ) {
                        Text(
                            text = stringResource(Res.string.profile_settings_notifications_on),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    HorizontalDivider()
                    SettingsRow(
                        icon = Icons.Filled.DarkMode,
                        label = stringResource(Res.string.profile_settings_theme)
                    ) {
                        Switch(
                            checked = isDarkThemeChecked,
                            onCheckedChange = onDarkThemeChange,
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    HorizontalDivider()
                    SettingsRow(
                        icon = Icons.Filled.Straighten,
                        label = stringResource(Res.string.profile_settings_units)
                    ) {
                        Text(
                            text = stringResource(Res.string.profile_settings_units_metric),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSignOutClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = null
            )
            Text(
                text = stringResource(Res.string.profile_sign_out_button),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            text = stringResource(Res.string.profile_footer),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
    }
}

private fun userInitials(name: String): String =
    name.trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
        .ifBlank { "?" }
