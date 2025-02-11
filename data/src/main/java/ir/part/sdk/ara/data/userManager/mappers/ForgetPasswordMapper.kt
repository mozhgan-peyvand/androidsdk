package ir.part.sdk.ara.data.userManager.mappers

import ir.part.sdk.ara.data.userManager.entities.CaptchaModel
import ir.part.sdk.ara.data.userManager.entities.ForgetPasswordParamModel
import ir.part.sdk.ara.domain.user.entities.ForgetPasswordParam

fun ForgetPasswordParam.toForgetPasswordParamModel() = ForgetPasswordParamModel(
    username = nationalCode,
    captcha = CaptchaModel(value = captchaValue, token = captchaToken)
)