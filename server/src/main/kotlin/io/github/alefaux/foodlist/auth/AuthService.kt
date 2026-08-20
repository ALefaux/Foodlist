package io.github.alefaux.foodlist.auth

import io.github.alefaux.foodlist.auth.dto.AuthResponse
import io.github.alefaux.foodlist.auth.dto.UserResponse
import io.github.alefaux.foodlist.database.UserRecord
import io.github.alefaux.foodlist.database.UserRepository

class AuthService(
    private val userRepository: UserRepository,
    private val googleAuthVerifier: GoogleAuthVerifier = GoogleAuthVerifier()
) {
    fun register(name: String, email: String, password: String): AuthResponse {
        val normalizedEmail = email.trim().lowercase()
        val trimmedName = name.trim()

        if (trimmedName.isBlank()) {
            throw ValidationException("Please enter your name.")
        }
        if (normalizedEmail.isBlank() || !normalizedEmail.contains("@")) {
            throw ValidationException("Please enter a valid email.")
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            throw ValidationException("Password must be at least $MIN_PASSWORD_LENGTH characters.")
        }
        if (userRepository.findByEmail(normalizedEmail) != null) {
            throw EmailAlreadyExistsException(normalizedEmail)
        }

        val user = userRepository.insert(
            name = trimmedName,
            email = normalizedEmail,
            passwordHash = PasswordHasher.hash(password),
            createdAt = System.currentTimeMillis()
        )

        return AuthResponse(
            token = JwtConfig.generateToken(user.id, user.email),
            user = user.toResponse()
        )
    }

    fun login(email: String, password: String): AuthResponse {
        val normalizedEmail = email.trim().lowercase()
        val user = userRepository.findByEmail(normalizedEmail) ?: throw InvalidCredentialsException()

        if (user.passwordHash == null || !PasswordHasher.verify(password, user.passwordHash)) {
            throw InvalidCredentialsException()
        }

        return AuthResponse(
            token = JwtConfig.generateToken(user.id, user.email),
            user = user.toResponse()
        )
    }

    fun loginWithGoogle(idToken: String): AuthResponse {
        val payload = googleAuthVerifier.verify(idToken)
        val email = payload.email?.trim()?.lowercase() ?: throw InvalidGoogleTokenException()

        val user = userRepository.findByEmail(email) ?: userRepository.insert(
            name = (payload["name"] as? String)?.trim()?.takeIf { it.isNotBlank() } ?: email.substringBefore("@"),
            email = email,
            passwordHash = null,
            createdAt = System.currentTimeMillis()
        )

        return AuthResponse(
            token = JwtConfig.generateToken(user.id, user.email),
            user = user.toResponse()
        )
    }

    fun getUser(id: Long): UserResponse? = userRepository.findById(id)?.toResponse()

    private fun UserRecord.toResponse() = UserResponse(id = id, name = name, email = email)

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }
}
