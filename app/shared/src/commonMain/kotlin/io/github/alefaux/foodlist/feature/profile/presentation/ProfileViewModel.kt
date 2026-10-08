package io.github.alefaux.foodlist.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.build.AppBuildInfo
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.core.theme.ThemeRepository
import io.github.alefaux.foodlist.feature.auth.domain.ObserveCurrentUserUseCase
import io.github.alefaux.foodlist.feature.auth.domain.SignOutUseCase
import io.github.alefaux.foodlist.feature.profile.domain.CreateTestDataUseCase
import io.github.alefaux.foodlist.feature.profile.domain.DeleteTestDataUseCase
import io.github.alefaux.foodlist.feature.profile.presentation.model.ProfileEvent
import io.github.alefaux.foodlist.feature.profile.presentation.model.ProfileUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val createTestDataUseCase: CreateTestDataUseCase,
    private val deleteTestDataUseCase: DeleteTestDataUseCase,
    private val themeRepository: ThemeRepository,
    private val appBuildInfo: AppBuildInfo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _events = Channel<ProfileEvent>(Channel.BUFFERED)
    val events: Flow<ProfileEvent> = _events.receiveAsFlow()

    val uiState: StateFlow<ProfileUiState> = combine(
        observeCurrentUserUseCase(),
        themeRepository.isDarkThemeEnabled
    ) { user, isDarkThemeEnabled ->
        ProfileUiState(
            user = user,
            isDarkThemeEnabled = isDarkThemeEnabled,
            isDebug = appBuildInfo.isDebug
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState(isDebug = appBuildInfo.isDebug)
    )

    fun signOut() {
        viewModelScope.launch(dispatcher) {
            signOutUseCase()
        }
    }

    fun createTestData() {
        if (!appBuildInfo.isDebug) return
        viewModelScope.launch(dispatcher) {
            try {
                createTestDataUseCase()
                _events.send(ProfileEvent.TestDataCreated)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                AppLogging.e(e, "Failed to create test data")
            }
        }
    }

    fun deleteTestData() {
        if (!appBuildInfo.isDebug) return
        viewModelScope.launch(dispatcher) {
            try {
                deleteTestDataUseCase()
                _events.send(ProfileEvent.TestDataDeleted)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                AppLogging.e(e, "Failed to delete test data")
            }
        }
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        themeRepository.setDarkThemeEnabled(enabled)
    }
}
