package ir.part.sdk.ara.util.api

import android.content.Context
import ir.part.sdk.ara.data.R
import javax.inject.Inject

class ApiUrlHelper @Inject constructor(
    val context: Context
) {
    interface ApiUrl {
        val baseUrl: String
        fun update()
    }

    lateinit var userManager: UserManager
    lateinit var barjavand: Barjavand
    lateinit var dashboard: Dashboard
    lateinit var stateService: StateService

    init {
        update()
    }

    private fun update() {

        userManager = UserManager(context.getString(R.string.API_URL_UserManager))
        barjavand = Barjavand(context.getString(R.string.API_URL_Barjavand))
        dashboard = Dashboard(context.getString(R.string.API_URL_Dashboard))
        stateService = StateService(context.getString(R.string.API_URL_STATE_SERVICE))

        userManager.update()
        barjavand.update()
        dashboard.update()
        stateService.update()

    }

    class UserManager(override var baseUrl: String = "") : ApiUrl {
        lateinit var captcha: String
        lateinit var login: String
        lateinit var forgetPassword: String
        lateinit var forgetPasswordVerification: String
        lateinit var changeAuthenticatePack: String
        lateinit var signUp: String
        lateinit var logout: String

        override fun update() {
            signUp = "$baseUrl/service/userManager@1/signUp"
            captcha = "$baseUrl/service/userManager@1/getCaptcha"
            forgetPassword = "$baseUrl/service/userManager@1/forgetPassword"
            forgetPasswordVerification = "$baseUrl/service/userManager@1/setAuthenticatePack"
            logout = "$baseUrl/service/userManager@1/logout"
            login = "$baseUrl/service/userManager@1/login"
            changeAuthenticatePack = "$baseUrl/service/userManager@1/changeAuthenticatePack"
        }
    }


    class Barjavand(override var baseUrl: String = "") : ApiUrl {
        lateinit var getApplicationInformation: String

        lateinit var setHasUnReadMessage: String

        lateinit var getDocumentOverView: String

        lateinit var getUnion: String

        lateinit var getConstant: String

        lateinit var removeDocument: String

        lateinit var submitComment: String

        override fun update() {
            getApplicationInformation =
                "$baseUrl/service/barjavand@3/data"
            setHasUnReadMessage = "$baseUrl/service/barjavand@3/data"
            getDocumentOverView = "$baseUrl/service/barjavand@3/data"
            getUnion = "$baseUrl/service/barjavand@3/data"
            getConstant = "$baseUrl/service/barjavand@3/data"
            submitComment = "$baseUrl/service/barjavand@3/data/comments"
            removeDocument = "$baseUrl/service/barjavand@3/data"
        }
    }

    class Dashboard(override var baseUrl: String = "") : ApiUrl {
        lateinit var newDocumentProcess: String
        lateinit var getTask: String
        lateinit var doingTask: String
        lateinit var doneTask: String

        override fun update() {
            newDocumentProcess = "$baseUrl/service/dashboard@2/process"
            getTask = "$baseUrl/service/dashboard@2/tasks"
            doneTask = "$baseUrl/service/dashboard@2/task"
            doingTask = "$baseUrl/service/dashboard@2/task"
        }
    }

    class StateService(override val baseUrl: String) : ApiUrl {
        lateinit var getBaseStateObject: String

        override fun update() {
            getBaseStateObject = "$baseUrl/service/atlas@3/readByKey/test"
        }
    }
}
