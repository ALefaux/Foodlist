package io.github.alefaux.foodlist.feature.auth.data.repository

import io.github.alefaux.foodlist.core.security.PasswordHasher
import io.github.alefaux.foodlist.database.dao.SessionDao
import io.github.alefaux.foodlist.database.dao.UserDao
import io.github.alefaux.foodlist.database.entity.SessionEntity
import io.github.alefaux.foodlist.database.entity.UserEntity
import io.github.alefaux.foodlist.feature.auth.domain.AuthUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class AuthRepositoryImpl(
    private val userDao: UserDao,
    private val sessionDao: SessionDao
) : AuthRepository {

    override suspend fun signUp(name: String, email: String, password: String): Result<AuthUser> {
        val normalizedEmail = email.trim().lowercase()

        if (userDao.getByEmail(normalizedEmail) != null) {
            return Result.failure(IllegalStateException("An account with this email already exists."))
        }

        val salt = PasswordHasher.generateSalt()
        val hash = PasswordHasher.hash(password, salt)
        val trimmedName = name.trim()

        val userId = userDao.insert(
            UserEntity(
                name = trimmedName,
                email = normalizedEmail,
                passwordHash = hash,
                passwordSalt = salt,
                createdAt = Clock.System.now()
            )
        )

        sessionDao.upsert(SessionEntity(userId = userId))

        return Result.success(AuthUser(id = userId, name = trimmedName, email = normalizedEmail))
    }

    override suspend fun signIn(email: String, password: String): Result<AuthUser> {
        val normalizedEmail = email.trim().lowercase()
        val user = userDao.getByEmail(normalizedEmail)
            ?: return Result.failure(IllegalStateException("No account found for this email."))

        if (!PasswordHasher.verify(password, user.passwordSalt, user.passwordHash)) {
            return Result.failure(IllegalStateException("Incorrect password."))
        }

        sessionDao.upsert(SessionEntity(userId = user.id))

        return Result.success(AuthUser(id = user.id, name = user.name, email = user.email))
    }

    override suspend fun signOut() {
        sessionDao.upsert(SessionEntity(userId = null))
    }

    override fun observeCurrentUser(): Flow<AuthUser?> =
        sessionDao.observe().map { session ->
            val userId = session?.userId ?: return@map null
            userDao.getById(userId)?.let {
                AuthUser(id = it.id, name = it.name, email = it.email)
            }
        }
}
