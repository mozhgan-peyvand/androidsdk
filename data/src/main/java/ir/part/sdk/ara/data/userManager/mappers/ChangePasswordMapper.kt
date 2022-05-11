package ir.part.sdk.ara.data.userManager.mappers

import ir.part.sdk.ara.data.userManager.entities.ChangePasswordParamModel
import ir.part.sdk.ara.domain.user.entities.ChangePasswordParam

fun ChangePasswordParam.toChangePasswordParamModel() = ChangePasswordParamModel(
    currentPassword = currentPassword,
    newPassword = newPassword,
    renewPassword = renewPassword
)