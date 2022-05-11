package ir.part.sdk.ara.util


import ir.part.sdk.ara.util.api.ApiUrlHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class CustomInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var urls: ApiUrlHelper

    private var token: String? = null
    private var nationalCode: String? = null


    //TODO remove cookie
    fun setValues(token: String?, nationalCode: String?) {
        this.token = token
        this.nationalCode = nationalCode
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()

        if (urls.userManager.login.contains(request.url().toString()) ||
            urls.userManager.forgetPassword.contains(request.url().toString()) ||
            urls.userManager.forgetPasswordVerification.contains(request.url().toString()) ||
            urls.userManager.signUp.contains(request.url().toString()) ||
            urls.userManager.captcha.contains(request.url().toString())
        ) {
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("system", "araMerat")

        }
        if (urls.userManager.forgetPasswordVerification.contains(request.url().toString())) {
            requestBuilder.addHeader("host", "usermanager-v1-dev.apipart.ir")
        }
        if (urls.userManager.changeAuthenticatePack.contains(request.url().toString())) {
            requestBuilder.addHeader("host", "usermanager-v1-dev.apipart.ir")
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("userName", nationalCode.toString())
            requestBuilder.addHeader("gateway-token", token.toString())
            requestBuilder.addHeader("system", "araMerat")
            requestBuilder.addHeader("token", token.toString())
        }

        if (urls.barjavand.setHasUnReadMessage.contains(request.url().toString())) {
            requestBuilder.addHeader("system", "araMerat")
            requestBuilder.addHeader("token", token.toString())
        }
        if (urls.barjavand.getDocumentOverView.contains(request.url().toString()) ||
            urls.barjavand.getUnion.contains(request.url().toString()) ||
            urls.barjavand.getConstant.contains(request.url().toString())
        /* || urls.barjavand.version.contains(request.url().toString())*/
        ) {
            requestBuilder.addHeader("token", token.toString())
        }

        if (urls.stateService.getBaseStateObject.contains(request.url().toString())
        ) {
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("gateway-token", token.toString())
            requestBuilder.addHeader("token", token.toString())
            requestBuilder.addHeader("userName", nationalCode.toString())
        }
        if (urls.dashboard.newDocumentProcess.contains(request.url().toString())
        ) {
            requestBuilder.addHeader("user", "demoActor")
            requestBuilder.addHeader("pass", "7MQZ!fT4f!RHL62")
            requestBuilder.addHeader("org", "demo")
            requestBuilder.addHeader("gateway-token", token.toString())
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("token", token.toString())
            requestBuilder.addHeader("system", "araMerat")
            requestBuilder.addHeader("host", "usermanager-v1-dev.apipart.ir")
            requestBuilder.addHeader("userName", nationalCode.toString())
        }
        if (
            urls.dashboard.getTask.contains(request.url().toString())
        ) {
            requestBuilder.addHeader("user", "demoActor")
            requestBuilder.addHeader("pass", "7MQZ!fT4f!RHL62")
            requestBuilder.addHeader("org", "demo")
            requestBuilder.addHeader("gateway-token", token.toString())
            requestBuilder.addHeader("gateway-system", "araMerat")
        }
        if (
            urls.dashboard.doingTask.contains(request.url().toString()) ||
            urls.dashboard.doneTask.contains(request.url().toString())
        ) {
            requestBuilder.addHeader("user", "demoActor")
            requestBuilder.addHeader("pass", "7MQZ!fT4f!RHL62")
            requestBuilder.addHeader("org", "demo")
            requestBuilder.addHeader("gateway-token", token.toString())
            requestBuilder.addHeader("gateway-system", "araMerat")
            requestBuilder.addHeader("host", "usermanager-v1-dev.apipart.ir")
        }
        return chain.proceed(requestBuilder.build())
    }
}