package ir.part.sdk.ara.data.userManager.repositories

import androidx.room.*
import ir.part.sdk.ara.data.userManager.entities.UserManagerEntity
import ir.part.sdk.ara.util.TN

@Dao
interface UserManagerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserManagerEntity)

    @Transaction
    @Query("SELECT * FROM ${TN.UserEntity} WHERE unc = :nationalCode")
    fun loadUser(nationalCode: String): UserManagerEntity?

    @Transaction
    @Query("SELECT * FROM ${TN.UserEntity} LIMIT 1")
    fun loadUser(): UserManagerEntity?

    @Transaction
    @Query("DELETE FROM ${TN.UserEntity}")
    suspend fun removeUsers(): Int
}