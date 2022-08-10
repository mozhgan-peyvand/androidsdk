package ir.part.sdk.ara.ui.menu.util.navigation

import androidx.navigation.NavHostController
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeNavigate

fun NavHostController.navigateToChangePasswordScreen() =
    safeNavigate(UiUserSharedIds.UserChangePassword.screenRouter)

fun NavHostController.navigateToTermsAndConditionScreen() =
    safeNavigate(MenuRouter.TermsAndConditionsScreen.router)