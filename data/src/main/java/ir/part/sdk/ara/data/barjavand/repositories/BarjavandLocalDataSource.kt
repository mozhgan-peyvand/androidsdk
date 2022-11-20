package ir.part.sdk.ara.data.barjavand.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.util.AesEncryptor
import javax.inject.Inject

class BarjavandLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    @SK val sk: String
) {

    fun saveLastShownUpdateVersion(version: Int?) {
        pref.edit().putString(
            "lastShownUpdateVersion",
            version?.toString()?.let { AesEncryptor().encrypt(it, sk) }
        ).apply()

    }

    fun getLastShownUpdateVersion(): Int? {
        return pref.getString("lastShownUpdateVersion", null)?.let {
            AesEncryptor()
                .decrypt(it, sk)
        }?.toInt()
    }
}