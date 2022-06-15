package ir.part.sdk.ara.ui.user.screens.login

import ir.part.sdk.ara.domain.user.entities.LoginParam

data class LoginView(
    var nationalCode: String,
    var password: String,
    var captchaValue: String,
    var captchaToken: String
){
    fun toLoginParam() = LoginParam(
        nationalCode = nationalCode,
        password = password,
        captchaToken = captchaToken,
        captchaValue =  captchaValue
    )
}