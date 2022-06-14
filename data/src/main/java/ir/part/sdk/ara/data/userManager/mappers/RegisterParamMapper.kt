package ir.part.sdk.ara.data.userManager.mappers

import ir.part.sdk.ara.data.userManager.entities.CaptchaModel
import ir.part.sdk.ara.data.userManager.entities.CellPhoneNumbers
import ir.part.sdk.ara.data.userManager.entities.RegisterParamModel
import ir.part.sdk.ara.domain.user.entities.RegisterParam

fun RegisterParam.toRegisterParamModel() = RegisterParamModel(
    username = nationalCode,
    cellphoneNumbers = listOf(CellPhoneNumbers(mobile)),
    email = if (email.isNullOrEmpty()) null else email,
    captcha = CaptchaModel(
        value = captchaValue,
        token = captchaToken
    )
)

