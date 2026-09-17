package io.github.alefaux.foodlist.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.theme.ThemeRepository
import io.github.alefaux.foodlist.feature.auth.domain.ObserveCurrentUserUseCase
import io.github.alefaux.foodlist.feature.auth.domain.SignOutUseCase
import io.github.alefaux.foodlist.feature.profile.presentation.model.ProfileUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val themeRepository: ThemeRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = combine(
        observeCurrentUserUseCase(),
        themeRepository.isDarkThemeEnabled
    ) { user, isDarkThemeEnabled ->
        ProfileUiState(user = user, isDarkThemeEnabled = isDarkThemeEnabled)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState()
    )

    fun signOut() {
        viewModelScope.launch(dispatcher) {
            signOutUseCase()
        }
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        themeRepository.setDarkThemeEnabled(enabled)
    }
}
