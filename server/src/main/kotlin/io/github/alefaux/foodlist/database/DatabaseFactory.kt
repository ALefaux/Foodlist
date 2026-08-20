package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

object DatabaseFactory {
    fun init(
        rawUrl: String = System.getenv("DATABASE_URL") ?: "jdbc:h2:./data/foodlist;AUTO_SERVER=TRUE",
        envUser: String = System.getenv("DATABASE_USER") ?: "",
        envPassword: String = System.getenv("DATABASE_PASSWORD") ?: ""
    ) {
        val (jdbcUrl, user, password) = toJdbc(rawUrl, envUser, envPassword)
        val driver = if (jdbcUrl.startsWith("jdbc:postgresql")) "org.postgresql.Driver" else "org.h2.Driver"

        Database.connect(
            url = jdbcUrl,
            driver = driver,
            user = user,
            password = password
        )

        transaction {
            SchemaUtils.create(Users, StorageUnits, Products)
        }
    }

    /**
     * Render (and most managed Postgres providers) hand out connection strings as plain
     * `postgres://user:password@host:port/db` URIs rather than JDBC URLs. Convert those on
     * the fly so DATABASE_URL can be pasted in as-is.
     */
    internal fun toJdbc(rawUrl: String, envUser: String, envPassword: String): Triple<String, String, String> {
        if (rawUrl.startsWith("jdbc:")) return Triple(rawUrl, envUser, envPassword)
        if (!rawUrl.startsWith("postgres://") && !rawUrl.startsWith("postgresql://")) return Triple(rawUrl, envUser, envPassword)

        val uri = URI(rawUrl)
        val (userInfoUser, userInfoPassword) = uri.userInfo?.split(":", limit = 2)
            ?.let { it[0] to it.getOrElse(1) { "" } }
            ?: ("" to "")
        val port = if (uri.port == -1) 5432 else uri.port

        val jdbcUrl = "jdbc:postgresql://${uri.host}:$port${uri.path}"
        val user = envUser.ifEmpty { userInfoUser }
        val password = envPassword.ifEmpty { userInfoPassword }
        return Triple(jdbcUrl, user, password)
    }
}
