package ir.part.sdk.ara.util


import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.event.MeratEvent
import ir.part.sdk.ara.base.event.MeratEventPublisher
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.util.api.ApiUrlHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class CustomInterceptor @Inject constructor(
    @SK private val sk: String,
) : Interceptor {

    @Inject
    lateinit var urls: ApiUrlHelper

    @Inject
    lateinit var pref: SharedPreferences

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()

        val token = pref.getString("token", null)?.let {
            AesEncryptor()
                .decrypt(it, sk)
        } ?: ""
        // todo : check if setting gateway token for user manager request in prod causes problems!
        requestBuilder.addHeader("gateway-system", "araMerat")
        requestBuilder.addHeader("gateway-token", token)

        val response = chain.proceed(requestBuilder.build())
        if (response.code() == 401) {
            MeratEventPublisher.publishEvent(officeEvent = MeratEvent.TokenExpired)
        }
        return response
    }
}