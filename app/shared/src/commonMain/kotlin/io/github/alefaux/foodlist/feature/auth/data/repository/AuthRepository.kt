package io.github.alefaux.foodlist.feature.auth.data.repository

import io.github.alefaux.foodlist.feature.auth.domain.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(name: String, email: String, password: String): Result<AuthUser>
    suspend fun signIn(email: String, password: String): Result<AuthUser>
    suspend fun signInWithGoogle(idToken: String): Result<AuthUser>
    suspend fun signOut()
    fun observeCurrentUser(): Flow<AuthUser?>
}
