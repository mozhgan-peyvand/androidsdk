package ir.part.sdk.ara.data.userManager.repositories

import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.base.model.InvokeError
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.model.InvokeSuccess
import ir.part.sdk.ara.data.userManager.entities.*
import ir.part.sdk.ara.util.api.BaseRemoteDataSource
import ir.part.sdk.ara.util.api.safeApiCall
import javax.inject.Inject

/**
 * Data source class that handles work with User API.
 */
class UserManagerRemoteDataSource @Inject constructor(
    private val service: UserManagerService,
) : BaseRemoteDataSource() {

    suspend fun getLogin(loginParamModel: LoginParamModel) =
        safeApiCall(
            call = { requestGetLogin(loginParamModel) },
            errorMessage = "Error getting login"
        )

    private suspend fun requestGetLogin(loginParamModel: LoginParamModel): InvokeStatus<UserManagerEntity> {
        val result = checkApiResult(
            service.getLogin(
                url = urls.userManager.login,
                loginParamModel = loginParamModel
            )
        )

        if (result is InvokeSuccess) {
            result.data.item?.let { it ->
                return InvokeSuccess(
                    UserManagerEntity(
                        nationalCode = loginParamModel.username,
                        token = it.token,
                        cookie = "", // TODO: how to get cookie?,
                        mobilePhone = it.cellphoneNumbers[0].value
                    )
                )
            }
        }

        return InvokeError(
            Exceptions.RemoteDataSourceExceptionUser(
                message = ((result as InvokeError).exception as Exceptions.RemoteDataSourceException).message
            )
        )
    }


    suspend fun forgetPassword(forgetPasswordParamModel: ForgetPasswordParamModel) =
        safeApiCall(
            call = { requestForgetPassword(forgetPasswordParamModel) },
            errorMessage = "Error getting Forget Password"
        )

    private suspend fun requestForgetPassword(forgetPasswordParamModel: ForgetPasswordParamModel) =
        checkApiResult(
            service.getForgetPassword(
                url = urls.userManager.forgetPassword,
                forgetPasswordParamModel = forgetPasswordParamModel
            )
        )

    suspend fun forgetPasswordVerificationCode(forgetPasswordVerificationParamModel: ForgetPasswordVerificationParamModel) =
        safeApiCall(
            call = { requestForgetPasswordVerificationCode(forgetPasswordVerificationParamModel) },
            errorMessage = "Error getting Forget Password Verification"
        )

    private suspend fun requestForgetPasswordVerificationCode(forgetPasswordVerificationParamModel: ForgetPasswordVerificationParamModel) =
        checkApiResult(
            service.getForgetPasswordVerification(
                url = urls.userManager.forgetPasswordVerification,
                forgetPasswordVerificationParamModel = forgetPasswordVerificationParamModel
            )
        )

    suspend fun registerUser(registerParamModel: RegisterParamModel) =
        safeApiCall(
            call = { requestRegisterUser(registerParamModel) },
            errorMessage = "Error getting register"
        )


    private suspend fun requestRegisterUser(registerParamModel: RegisterParamModel) =
        checkApiResult(
            service.signUp(
                url = urls.userManager.signUp,
                data = RegisterParamModel(
                    username = registerParamModel.username,
                    cellphoneNumbers = registerParamModel.cellphoneNumbers,
                    email = registerParamModel.email,
                    captcha = registerParamModel.captcha
                )

            )
        )

    suspend fun changePassword(
        changePasswordParamModel: ChangePasswordParamModel,
        processInstanceId: String,
        taskInstanceId: String
    ) =
        safeApiCall(
            call = {
                requestChangePassword(
                    changePasswordParamModel = changePasswordParamModel,
                    processInstanceId = processInstanceId,
                    taskInstanceId = taskInstanceId,
                )
            },
            errorMessage = "Error getting register"
        )


    private suspend fun requestChangePassword(
        changePasswordParamModel: ChangePasswordParamModel,
        processInstanceId: String,
        taskInstanceId: String
    ) =
        checkApiResult(
            service.getChangePassword(
                url = urls.userManager.changeAuthenticatePack,
                processInstanceId = processInstanceId,
                taskInstanceId = taskInstanceId,
                changePasswordParamModel = changePasswordParamModel
            )
        )

    suspend fun captcha() = safeApiCall(
        call = { requestCaptcha() },
        errorMessage = "Error logout"
    )

    private suspend fun requestCaptcha() =
        checkApiResult(service.getCaptcha(url = urls.userManager.captcha))


    suspend fun logout() = safeApiCall(
        call = { requestLogout() },
        errorMessage = "Error logging out"
    )

    private suspend fun requestLogout() = checkApiResult(service.logout(urls.userManager.logout))
}