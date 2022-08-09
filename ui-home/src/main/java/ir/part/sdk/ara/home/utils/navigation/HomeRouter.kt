package ir.part.sdk.ara.home.utils.navigation

import ir.part.sdk.ara.common.ui.view.model.AraUiModules

val moduleName = AraUiModules.MODULE_UI_HOME.value

sealed class HomeRouter(val router: String) {
    object HomeGraph : HomeRouter(router = "$moduleName://homeGraph")
    object SplashScreen : HomeRouter(router = "$moduleName://splashScreen")
    object UserHomeScreen : HomeRouter(router = "$moduleName://userHomeScreen")
    object UserLoginScreen :
        HomeRouter(router = "${AraUiModules.MODULE_UI_USER.value}://userLoginScreen")

    object UserRegisterScreen :
        HomeRouter(router = "${AraUiModules.MODULE_UI_USER.value}://userRegister")

}