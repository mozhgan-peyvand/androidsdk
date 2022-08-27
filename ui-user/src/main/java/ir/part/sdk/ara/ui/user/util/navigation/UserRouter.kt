package ir.part.sdk.ara.ui.user.util.navigation

import ir.part.sdk.ara.common.ui.view.model.AraUiModules

val moduleName = AraUiModules.MODULE_UI_USER.value

sealed class UserRouter(val router: String) {
    object Graph : UserRouter(router = "$moduleName://userGraph")

    object UserLoginScreen : UserRouter(router = "$moduleName://userLoginScreen")
    object UserRegisterScreen : UserRouter(router = "$moduleName://userRegister")
    object UserForgetPasswordScreen : UserRouter(router = "$moduleName://userForgetPasswordScreen")
    object UserForgetPasswordVerificationScreen :
        UserRouter(router = "$moduleName://userForgetPasswordVerificationScreen")

    object UserChangePasswordScreen : UserRouter(router = "$moduleName://userChangePasswordScreen")
}