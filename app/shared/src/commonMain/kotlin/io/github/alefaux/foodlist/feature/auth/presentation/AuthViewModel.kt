package io.github.alefaux.foodlist.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.auth.domain.SignInUseCase
import io.github.alefaux.foodlist.feature.auth.domain.SignUpUseCase
import io.github.alefaux.foodlist.feature.auth.presentation.model.AuthUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun signIn(email: String, password: String) {
        _uiState.update { AuthUiState.Loading }

        viewModelScope.launch(dispatcher) {
            signInUseCase(email, password)
                .onSuccess {
                    _uiState.update { AuthUiState.Success }
                }
                .onFailure { error ->
                    AppLogging.e(error, "Sign in failed")
                    _uiState.update { AuthUiState.Error(error.message ?: "Couldn't sign in.") }
                }
        }
    }

    fun signUp(name: String, email: String, password: String, confirmPassword: String) {
        _uiState.update { AuthUiState.Loading }

        viewModelScope.launch(dispatcher) {
            signUpUseCase(name, email, password, confirmPassword)
                .onSuccess {
                    _uiState.update { AuthUiState.Success }
                }
                .onFailure { error ->
                    AppLogging.e(error, "Sign up failed")
                    _uiState.update { AuthUiState.Error(error.message ?: "Couldn't create account.") }
                }
        }
    }

    fun resetState() {
        _uiState.update { AuthUiState.Idle }
    }
}
