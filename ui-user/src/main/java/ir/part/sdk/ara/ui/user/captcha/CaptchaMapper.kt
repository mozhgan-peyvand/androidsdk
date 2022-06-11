package ir.part.sdk.ara.ui.user.captcha

import ir.part.sdk.ara.domain.user.entities.Captcha

fun Captcha.toCaptchaView() = CaptchaViewState(
    img = img,
    token = token,
    version = version
)