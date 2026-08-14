package io.github.alefaux.foodlist.feature.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class UserResponseDto(
    val id: Long,
    val name: String,
    val email: String
)

@Serializable
data class AuthResponseDto(
    val token: String,
    val user: UserResponseDto
)

@Serializable
data class ErrorResponseDto(
    val message: String
)
