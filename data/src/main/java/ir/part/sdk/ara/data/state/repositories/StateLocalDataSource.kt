package ir.part.sdk.ara.data.state.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.util.AesEncryptor
import javax.inject.Inject

class StateLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    @SK val sk: String

) {

    fun getProcessInstanceId(): String = pref.getString("processInstanceId", null)?.let {
        AesEncryptor().decrypt(it, sk)
    } ?: ""
}