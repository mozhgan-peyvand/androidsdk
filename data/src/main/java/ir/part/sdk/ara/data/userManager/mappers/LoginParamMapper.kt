package ir.part.sdk.ara.data.userManager.mappers

import ir.part.sdk.ara.data.userManager.entities.CaptchaModel
import ir.part.sdk.ara.data.userManager.entities.LoginParamModel
import ir.part.sdk.ara.domain.user.entities.LoginParam

fun LoginParam.toLoginParamModel() = LoginParamModel(
    username = nationalCode,
    password = password,
    captcha = CaptchaModel(value = captchaValue, captchaToken)
)