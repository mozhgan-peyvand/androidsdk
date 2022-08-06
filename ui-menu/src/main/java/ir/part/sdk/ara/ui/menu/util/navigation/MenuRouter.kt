package ir.part.sdk.ara.ui.menu.util.navigation

import ir.part.sdk.ara.common.ui.view.model.AraUiModules

val moduleName = AraUiModules.MODULE_UI_MENU.value

sealed class MenuRouter(val router: String) {
    object Graph : MenuRouter(router = "$moduleName://menuGraph")

    object MainMenuScreen : MenuRouter(router = "$moduleName://mainMenuScreen")
    object GuideScreen : MenuRouter(router = "$moduleName://guideScreen")
    object AboutUsScreen : MenuRouter(router = "$moduleName://aboutUsScreen")
    object TermsAndConditionsScreen : MenuRouter(router = "$moduleName://termsAndConditionsScreen")
    object DisclaimerScreen : MenuRouter(router = "$moduleName://disclaimerScreen")
    object RahyarAddressScreen : MenuRouter(router = "$moduleName://rahyarAddressScreen")
    object SubmitCommentScreen : MenuRouter(router = "$moduleName://submitCommentScreen")
    object CallCenterScreen : MenuRouter(router = "$moduleName://callCenterScreen")
}