package ir.part.sdk.ara.db


import androidx.room.Database
import androidx.room.RoomDatabase
import ir.part.sdk.ara.data.userManager.entities.UserManagerEntity
import ir.part.sdk.ara.data.userManager.repositories.UserManagerDao

@Database(
    entities = [UserManagerEntity::class],

    version = 1,
    exportSchema = false
)
abstract class AraDb : RoomDatabase() {
    abstract fun userManagerDao(): UserManagerDao
}