package io.github.alefaux.foodlist.feature.profile.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alefaux.foodlist.feature.profile.panes.GuestProfilePane
import io.github.alefaux.foodlist.feature.profile.panes.ProfilePane
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        val user = state.user
        if (user != null) {
            ProfilePane(
                modifier = Modifier.padding(padding),
                userName = user.name,
                onInviteMemberClick = {},
                onAccountClick = {},
                onSignOutClick = viewModel::signOut
            )
        } else {
            GuestProfilePane(
                modifier = Modifier.padding(padding),
                onSignInClick = onSignInClick,
                onSignUpClick = onSignUpClick
            )
        }
    }
}
