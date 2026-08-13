package io.github.alefaux.foodlist.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.feature.auth.domain.ObserveCurrentUserUseCase
import io.github.alefaux.foodlist.feature.auth.presentation.model.SessionState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SessionViewModel(
    observeCurrentUserUseCase: ObserveCurrentUserUseCase
) : ViewModel() {

    val sessionState: StateFlow<SessionState> = observeCurrentUserUseCase()
        .map { user ->
            if (user != null) SessionState.Authenticated(user) else SessionState.Unauthenticated
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SessionState.Loading
        )
}
