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
        }

        //todo it will be fix in get task feature

        if (request.url().toString().contains(urls.userManager.changeAuthenticatePack)) {
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("process-instance-id", "")
            requestBuilder.addHeader("gateway-token", token)
            requestBuilder.addHeader("task-instance-id", "")
        }

        if (request.url().toString().contains(urls.stateService.getBaseStateObject)
        ) {
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("gateway-token", token)
            requestBuilder.addHeader("token", token)
            requestBuilder.addHeader("userName", nationalCode)
        }

        if (request.url().toString().contains(urls.barjavand.submitComment)
        ) {
            requestBuilder.addHeader("gateway-system", "araMerat")
        }

        if (request.url().toString().contains(urls.dashboard.newDocumentProcess)
        ) {
            // todo : use dynamic headers instead of static when task feature is implemented
            requestBuilder.addHeader("gateway-token", token)
            requestBuilder.addHeader("userName", nationalCode)
            requestBuilder.addHeader("user", "araActor")
            requestBuilder.addHeader("pass", "12bqMVPU7nBg7Cn9S3lK")
            requestBuilder.addHeader("org", "araMerat-testValidator")
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("task-instance-id", "8dcf12bd-ff58-450d-9585-255ec9e41b62")
            requestBuilder.addHeader("process-instance-id", "3ce6f4b4-f347-4bb4-bf38-200d28576dfe")
        }

        if (
            request.url().toString().contains(urls.dashboard.getTask)
        ) {
            requestBuilder.addHeader("gateway-token", token)
            requestBuilder.addHeader("gateway-system", "araMerat")
        }

        if (
            request.url().toString().contains(urls.barjavand.getApplicationInformation) ||
            request.url().toString().contains(urls.barjavand.getUnion) ||
            request.url().toString().contains(urls.barjavand.getDocumentOverView) ||
            request.url().toString().contains(urls.barjavand.getConstant) ||
            request.url().toString().contains(urls.barjavand.setHasUnReadMessage) ||
            request.url().toString().contains(urls.barjavand.removeDocument)
        ) {
            requestBuilder.addHeader("gateway-token", token)
            requestBuilder.addHeader("userName", nationalCode)
            requestBuilder.addHeader("gateway-system", "araMerat")
        }

        if (request.url().toString().contains(urls.barjavand.getRahyar) || request.url().toString()
                .contains(urls.barjavand.getConstant)
        ) {
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader(
                "gateway-token",
                token
            )
        }
        if (request.url().toString().contains(urls.barjavand.getVersion)) {
            requestBuilder.addHeader("gateway-system", "araMerat")
        }
        return chain.proceed(requestBuilder.build())
    }
}