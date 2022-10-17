package ir.part.sdk.ara.data.dashboard.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.util.AesEncryptor
import javax.inject.Inject

class DashboardLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    @SK val sk: String
) {

    fun saveTaskId(taskId: String?) {
        pref.edit().putString(
            "taskId",
            AesEncryptor().encrypt(taskId ?: "", sk)
        )
            .apply()
    }

    fun saveTaskInstanceId(taskInstanceId: String?) {
        pref.edit().putString(
            "taskInstanceId",
            AesEncryptor().encrypt(taskInstanceId ?: "", sk)
        )
            .apply()
    }

    fun saveTaskName(taskName: String?) {
        pref.edit().putString(
            "taskName",
            AesEncryptor().encrypt(taskName ?: "", sk)
        )
            .apply()
    }

    fun getTaskId(): String = pref.getString("taskId", null)?.let {
        AesEncryptor().decrypt(it, sk)
    } ?: ""

    fun getTaskInstanceId(): String = pref.getString("taskInstanceId", null)?.let {
        AesEncryptor().decrypt(it, sk)
    } ?: ""

    fun getTaskName(): String = pref.getString("taskName", null)?.let {
        AesEncryptor().decrypt(it, sk)
    } ?: ""
}