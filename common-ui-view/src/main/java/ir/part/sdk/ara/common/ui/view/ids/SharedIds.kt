package ir.part.sdk.ara.common.ui.view.ids

import ir.part.sdk.ara.common.ui.view.model.AraUiModules

enum class UiUserSharedIds(val router: String) {
    UserRegister(AraUiModules.MODULE_UI_USER.value + "://userRegister"),
    UserLogin(AraUiModules.MODULE_UI_USER.value + "://userLoginScreen"),
    UserChangePassword(AraUiModules.MODULE_UI_USER.value + "://userChangePasswordScreen"),
    SubmitDocument(AraUiModules.MODULE_UI_DOCUMENT.value + "://documentSubmitScreen"),
    UserHomeScreen(AraUiModules.MODULE_UI_HOME.value + "://userHomeScreen")
}