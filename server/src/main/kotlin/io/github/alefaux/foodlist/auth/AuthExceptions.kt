package io.github.alefaux.foodlist.auth

class EmailAlreadyExistsException(email: String) : Exception("An account with email $email already exists.")

class InvalidCredentialsException : Exception("Invalid email or password.")

class ValidationException(message: String) : Exception(message)
