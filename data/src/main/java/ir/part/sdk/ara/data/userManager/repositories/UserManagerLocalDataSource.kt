package ir.part.sdk.ara.data.userManager.repositories

import android.content.SharedPreferences
import androidx.room.withTransaction
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.base.util.CoroutinesDispatcherProvider
import ir.part.sdk.ara.data.userManager.entities.UserManagerEntity
import ir.part.sdk.ara.db.AraDb
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserManagerLocalDataSource @Inject constructor(
    private val araDb: AraDb,
    private val dao: UserManagerDao,
    private val pref: SharedPreferences,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    @SK val sk: String
) {

    fun clearAllTables() {
        araDb.clearAllTables()
    }

    suspend fun saveUser(userEntity: UserManagerEntity) =
        withContext(dispatcherProvider.io) {
            requestSaveUser(userEntity)
        }

    private suspend fun requestSaveUser(userEntity: UserManagerEntity) {
        try {
            araDb.withTransaction {
                dao.removeUsers()
                pref.edit().putString(
                    "mobilePhone",
                    userEntity.mobilePhone.let { AesEncryptor().encrypt(it, sk) }
                )
                pref.edit().putString(
                    "token",
                    userEntity.token.let { AesEncryptor().encrypt(it, sk) }
                ).apply()
                pref.edit().putString(
                    "CurrentUserNationalCode",
                    AesEncryptor().encrypt(userEntity.nationalCode, sk)
                ).apply()
                dao.insertUser(userEntity)
            }
        } catch (e: Throwable) {
            //Timber.d(e)
        }
    }


    suspend fun removeUsers() =
        withContext(dispatcherProvider.io) {
            requestRemoveUsers()
        }

    private suspend fun requestRemoveUsers(): Boolean {
        var removeResult: Int? = null
        var result = false
        try {
            removeResult = dao.removeUsers()
        } catch (e: Throwable) {
            Exceptions.LocalDataSourceException(cause = e)
        } finally {
            if (removeResult != null && removeResult != 0) {
                pref.edit().putString(
                    "token",
                    null
                ).apply()

                pref.edit().putString("CurrentUserNationalCode", null).apply()
                result = true
            }
        }
        return result
    }

    fun getUser(nationalCode: String = ""): UserManagerEntity? {


        if (nationalCode.isEmpty()) {
            dao.loadUser().let {
                pref.edit().putString(
                    "CurrentUserNationalCode",
                    it?.let { AesEncryptor().encrypt(it.nationalCode, sk) } ?: it?.nationalCode
                ).apply()
                return it
            }
        } else {
            dao.loadUser(nationalCode).let {
                pref.edit().putString(
                    "token",
                    it?.token?.let { token -> AesEncryptor().encrypt(token, sk) }
                ).apply()
                pref.edit().putString(
                    "CurrentUserNationalCode",
                    it?.let { AesEncryptor().encrypt(it.nationalCode, sk) } ?: it?.nationalCode
                ).apply()
                return it
            }
        }
    }

    fun getNationalCode(): String {
        return pref.getString("CurrentUserNationalCode", null)?.let {
            AesEncryptor()
                .decrypt(it, sk)
        } ?: ""
    }

}