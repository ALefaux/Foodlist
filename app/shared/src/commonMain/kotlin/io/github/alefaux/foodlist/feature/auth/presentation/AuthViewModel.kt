package io.github.alefaux.foodlist.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.auth.GoogleAuthProvider
import io.github.alefaux.foodlist.core.auth.GoogleSignInContext
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.auth.domain.SignInUseCase
import io.github.alefaux.foodlist.feature.auth.domain.SignInWithGoogleUseCase
import io.github.alefaux.foodlist.feature.auth.domain.SignUpUseCase
import io.github.alefaux.foodlist.feature.auth.presentation.model.AuthUiState
import io.github.alefaux.foodlist.feature.sync.domain.SyncLocalDataUseCase
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
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val googleAuthProvider: GoogleAuthProvider,
    private val syncLocalDataUseCase: SyncLocalDataUseCase,
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
                    syncLocalData()
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
                    syncLocalData()
                }
                .onFailure { error ->
                    AppLogging.e(error, "Sign up failed")
                    _uiState.update { AuthUiState.Error(error.message ?: "Couldn't create account.") }
                }
        }
    }

    fun signInWithGoogle(context: GoogleSignInContext) {
        _uiState.update { AuthUiState.Loading }

        viewModelScope.launch(dispatcher) {
            googleAuthProvider.signIn(context)
                .fold(
                    onSuccess = { idToken ->
                        signInWithGoogleUseCase(idToken)
                            .onSuccess {
                                _uiState.update { AuthUiState.Success }
                                syncLocalData()
                            }
                            .onFailure { error ->
                                AppLogging.e(error, "Google sign in failed")
                                _uiState.update { AuthUiState.Error(error.message ?: "Couldn't sign in with Google.") }
                            }
                    },
                    onFailure = { error ->
                        AppLogging.e(error, "Google sign in was cancelled or failed")
                        _uiState.update { AuthUiState.Idle }
                    }
                )
        }
    }

    fun resetState() {
        _uiState.update { AuthUiState.Idle }
    }

    private fun syncLocalData() {
        viewModelScope.launch(dispatcher) {
            syncLocalDataUseCase()
                .onFailure { error -> AppLogging.e(error, "Couldn't sync local data to the server") }
        }
    }
}
