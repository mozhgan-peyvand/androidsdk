package ir.part.sdk.ara.data.userManager.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.data.userManager.entities.UserManagerEntity
import javax.inject.Inject

class UserManagerLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    @SK val sk: String
) {
    fun saveUser(userEntity: UserManagerEntity) {
        pref.edit().putString(
            "mobilePhone",
            userEntity.mobilePhone.let { AesEncryptor().encrypt(it, sk) }
        ).apply()
        pref.edit().putString(
            "token",
            userEntity.token.let { AesEncryptor().encrypt(it, sk) }
        ).apply()
        pref.edit().putString(
            "CurrentUserNationalCode",
            AesEncryptor().encrypt(userEntity.nationalCode, sk)
        ).apply()

    }

    fun logout() {
        pref.edit().remove("CurrentUserNationalCode").apply()
        pref.edit().remove("token").apply()
        pref.edit().remove("mobilePhone").apply()
    }

    fun getNationalCode(): String {
        return pref.getString("CurrentUserNationalCode", null)?.let {
            AesEncryptor()
                .decrypt(it, sk)
        } ?: ""
    }

    fun getPhoneNumber(): String {
        return pref.getString("mobilePhone", null)?.let {
            AesEncryptor()
                .decrypt(it, sk)
        } ?: ""
    }

    fun getToken(): String {
        return pref.getString("token", null)?.let {
            AesEncryptor()
                .decrypt(it, sk)
        } ?: ""
    }
}