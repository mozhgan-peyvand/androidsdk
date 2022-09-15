package ir.part.sdk.ara.data.dashboard.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.util.AesEncryptor
import javax.inject.Inject

class DashboardLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    @SK val sk: String
) {

    fun getTaskInstanceId(): String = pref.getString("taskInstanceId", null)?.let {
        AesEncryptor().decrypt(it, sk)
    } ?: ""
}