package ir.part.sdk.ara.common.ui.view.ids

import ir.part.sdk.ara.common.ui.view.model.AraUiModules

enum class UiUserSharedIds(val screenRouter: String) {
    UserRegister(AraUiModules.MODULE_UI_USER.value + "://userRegister"),
    UserLogin(AraUiModules.MODULE_UI_USER.value + "://userLoginScreen"),
    UserChangePassword(AraUiModules.MODULE_UI_USER.value + "://userChangePasswordScreen")
}