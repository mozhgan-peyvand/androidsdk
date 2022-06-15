package ir.part.sdk.ara.ui.user.screens.register

import ir.part.sdk.ara.domain.user.entities.RegisterParam


data class RegisterView(
    var nationalCode: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var captchaToken: String? = null,
    var captchaValue: String? = null
){
    fun toRegisterParam()= RegisterParam(
        nationalCode = nationalCode,
        email = email,
        mobile = phone,
        captchaToken = captchaToken,
        captchaValue = captchaValue
    )
}
