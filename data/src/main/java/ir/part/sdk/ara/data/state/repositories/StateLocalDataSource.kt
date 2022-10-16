package ir.part.sdk.ara.data.state.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.util.AesEncryptor
import javax.inject.Inject

class StateLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    @SK val sk: String

) {

    fun saveProcessId(processId: String?) {
        pref.edit().putString(
            "processId",
            AesEncryptor().encrypt(processId ?: "", sk)
        )
            .apply()
    }

    fun saveProcessInstanceId(processInstanceId: String?) {
        pref.edit().putString(
            "processInstanceId",
            AesEncryptor().encrypt(processInstanceId ?: "", sk)
        )
            .apply()
    }

    fun getProcessInstanceId(): String = pref.getString("processInstanceId", null)?.let {
        AesEncryptor().decrypt(it, sk)
    } ?: ""

    fun getProcessId(): String = pref.getString("processId", null)?.let {
        AesEncryptor().decrypt(it, sk)
    } ?: ""
}