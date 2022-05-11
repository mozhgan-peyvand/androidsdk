package ir.part.sdk.ara.domain.user.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.entities.*

interface UserManagerRepository {

    suspend fun getLoginRemote(loginParam: LoginParam): InvokeStatus<Boolean>
    suspend fun forgetPasswordRemote(forgetPasswordParam: ForgetPasswordParam): InvokeStatus<Unit>
    suspend fun changePasswordRemote(changePasswordParam: ChangePasswordParam): InvokeStatus<Unit>
    suspend fun forgetPasswordVerificationCodeRemote(
        nationalCode: String,
        verificationCode: String
    ): InvokeStatus<Unit>

    suspend fun registerUserRemote(registerParam: RegisterParam): InvokeStatus<Unit>
    suspend fun getCaptchaRemote(): InvokeStatus<Captcha?>
    suspend fun getLogOut(): InvokeStatus<Boolean>
    suspend fun removeUser()

    fun clearAllTables()
    fun getNationalCode(): String
    fun getCurrentUser(): User?
}