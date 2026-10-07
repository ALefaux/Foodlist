package io.github.alefaux.foodlist.feature.profile.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.profile_debug_test_data_created
import foodlist.app.shared.generated.resources.profile_debug_test_data_deleted
import foodlist.app.shared.generated.resources.profile_top_bar_title
import io.github.alefaux.foodlist.feature.profile.panes.GuestProfilePane
import io.github.alefaux.foodlist.feature.profile.panes.ProfilePane
import io.github.alefaux.foodlist.feature.profile.presentation.model.ProfileEvent
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                ProfileEvent.TestDataCreated -> snackbarHostState.showSnackbar(
                    getString(Res.string.profile_debug_test_data_created)
                )
                ProfileEvent.TestDataDeleted -> snackbarHostState.showSnackbar(
                    getString(Res.string.profile_debug_test_data_deleted)
                )
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        val user = state.user
        if (user != null) {
            ProfilePane(
                modifier = Modifier.padding(padding),
                userName = user.name,
                isDarkThemeEnabled = state.isDarkThemeEnabled,
                onInviteMemberClick = {},
                onAccountClick = {},
                onSignOutClick = viewModel::signOut,
                onDarkThemeChange = viewModel::setDarkThemeEnabled,
                isDebug = state.isDebug,
                onCreateTestDataClick = viewModel::createTestData,
                onDeleteTestDataClick = viewModel::deleteTestData
            )
        } else {
            GuestProfilePane(
                modifier = Modifier.padding(padding),
                onSignInClick = onSignInClick,
                onSignUpClick = onSignUpClick,
                isDebug = state.isDebug,
                onCreateTestDataClick = viewModel::createTestData,
                onDeleteTestDataClick = viewModel::deleteTestData
            )
        }
    }
}
