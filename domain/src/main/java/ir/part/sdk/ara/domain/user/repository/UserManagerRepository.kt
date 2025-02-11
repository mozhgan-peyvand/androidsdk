package ir.part.sdk.ara.domain.user.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.entities.*

interface UserManagerRepository {

    suspend fun getLoginRemote(loginParam: LoginParam): InvokeStatus<Boolean>
    suspend fun forgetPasswordRemote(forgetPasswordParam: ForgetPasswordParam): InvokeStatus<Boolean>
    suspend fun changePasswordRemote(changePasswordParam: ChangePasswordParam): InvokeStatus<Boolean>
    suspend fun forgetPasswordVerificationCodeRemote(
        nationalCode: String,
        verificationCode: String
    ): InvokeStatus<Boolean>

    suspend fun registerUserRemote(registerParam: RegisterParam): InvokeStatus<Boolean>
    suspend fun getCaptchaRemote(): InvokeStatus<Captcha?>

    fun getNationalCode(): String
    fun getPhoneNumber(): String

    suspend fun logout(): InvokeStatus<Boolean>
    fun getToken(): String
}