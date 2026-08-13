package io.github.alefaux.foodlist.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import io.github.alefaux.foodlist.database.converter.Converters
import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.SessionDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.dao.UserDao
import io.github.alefaux.foodlist.database.entity.ProductEntity
import io.github.alefaux.foodlist.database.entity.SessionEntity
import io.github.alefaux.foodlist.database.entity.StorageEntity
import io.github.alefaux.foodlist.database.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        StorageEntity::class,
        ProductEntity::class,
        UserEntity::class,
        SessionEntity::class
    ],
    version = 3
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun storageDao(): StorageDao
    abstract fun userDao(): UserDao
    abstract fun sessionDao(): SessionDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

private val MIGRATION_1_2 = object : Migration(startVersion = 1, endVersion = 2) {
    override fun migrate(connection: androidx.sqlite.SQLiteConnection) {
        connection.execSQL("ALTER TABLE ProductEntity ADD COLUMN storageId INTEGER")
        connection.execSQL("ALTER TABLE ProductEntity ADD COLUMN quantity TEXT NOT NULL DEFAULT ''")
        connection.execSQL("ALTER TABLE ProductEntity ADD COLUMN category TEXT NOT NULL DEFAULT 'Other'")
    }
}

private val MIGRATION_2_3 = object : Migration(startVersion = 2, endVersion = 3) {
    override fun migrate(connection: androidx.sqlite.SQLiteConnection) {
        connection.execSQL(
            "CREATE TABLE IF NOT EXISTS `UserEntity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`name` TEXT NOT NULL, `email` TEXT NOT NULL, `passwordHash` TEXT NOT NULL, " +
                "`passwordSalt` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)"
        )
        connection.execSQL(
            "CREATE TABLE IF NOT EXISTS `SessionEntity` (`id` INTEGER NOT NULL, `userId` INTEGER, " +
                "PRIMARY KEY(`id`))"
        )
    }
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
    .build()