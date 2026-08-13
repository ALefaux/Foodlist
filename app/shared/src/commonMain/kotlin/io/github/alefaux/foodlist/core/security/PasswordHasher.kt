package io.github.alefaux.foodlist.core.security

import org.kotlincrypto.hash.sha2.SHA256
import kotlin.random.Random

object PasswordHasher {
    fun generateSalt(): String =
        Random.nextBytes(SALT_SIZE_BYTES).toHex()

    fun hash(password: String, salt: String): String =
        SHA256().digest((salt + password).encodeToByteArray()).toHex()

    fun verify(password: String, salt: String, expectedHash: String): Boolean =
        hash(password, salt) == expectedHash

    private fun ByteArray.toHex(): String =
        joinToString("") { byte -> ((byte.toInt() and 0xFF) + 0x100).toString(16).substring(1) }

    private const val SALT_SIZE_BYTES = 16
}
