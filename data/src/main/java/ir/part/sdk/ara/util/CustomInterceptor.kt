package ir.part.sdk.ara.util


import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.util.api.ApiUrlHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class CustomInterceptor @Inject constructor(
    @SK private val sk: String
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
        val nationalCode = pref.getString("CurrentUserNationalCode", null)?.let {
            AesEncryptor()
                .decrypt(it, sk)
        } ?: ""
        if (request.url().toString().contains(urls.userManager.login) ||
            request.url().toString().contains(urls.userManager.forgetPassword) ||
            request.url().toString().contains(urls.userManager.forgetPasswordVerification) ||
            request.url().toString().contains(urls.userManager.signUp) ||
            request.url().toString().contains(urls.userManager.captcha)
        ) {
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("system", "araMerat")

        }
        if (request.url().toString().contains(urls.userManager.changeAuthenticatePack)) {
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("userName", nationalCode)
            requestBuilder.addHeader("gateway-token",token)
            requestBuilder.addHeader("system", "araMerat")
            requestBuilder.addHeader("token", token)
        }

        if (request.url().toString().contains(urls.barjavand.setHasUnReadMessage)) {
            requestBuilder.addHeader("system", "araMerat")
            requestBuilder.addHeader("token", token)
        }
        if (request.url().toString().contains(urls.barjavand.getDocumentOverView) ||
            request.url().toString().contains(urls.barjavand.getUnion) ||
            request.url().toString().contains( urls.barjavand.getConstant)
        /* || urls.barjavand.version.contains(request.url().toString())*/
        ) {
            requestBuilder.addHeader("token", token)
        }

        if (request.url().toString().contains(urls.stateService.getBaseStateObject)
        ) {
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("gateway-token", token)
            requestBuilder.addHeader("token", token)
            requestBuilder.addHeader("userName", nationalCode)
        }
        if (request.url().toString().contains(urls.dashboard.newDocumentProcess)
        ) {
            requestBuilder.addHeader("user", "demoActor")
            requestBuilder.addHeader("pass", "7MQZ!fT4f!RHL62")
            requestBuilder.addHeader("org", "demo")
            requestBuilder.addHeader("gateway-token", token)
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("token", token)
            requestBuilder.addHeader("system", "araMerat")
            requestBuilder.addHeader("host", "usermanager-v1-dev.apipart.ir")
            requestBuilder.addHeader("userName", nationalCode)
        }
        if (
            request.url().toString().contains(urls.dashboard.getTask)
        ) {
            requestBuilder.addHeader("user", "demoActor")
            requestBuilder.addHeader("pass", "7MQZ!fT4f!RHL62")
            requestBuilder.addHeader("org", "demo")
            requestBuilder.addHeader("gateway-token", token)
            requestBuilder.addHeader("gateway-system", "araMerat")
        }
        return chain.proceed(requestBuilder.build())
    }
}