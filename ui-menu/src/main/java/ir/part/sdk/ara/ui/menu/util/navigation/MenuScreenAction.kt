package ir.part.sdk.ara.ui.menu.util.navigation

import androidx.navigation.NavHostController
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeNavigate

fun NavHostController.navigateToChangePasswordScreen() =
    safeNavigate(UiUserSharedIds.UserChangePassword.router)

fun NavHostController.navigateToTermsAndConditionScreen() =
    safeNavigate(MenuRouter.TermsAndConditionsScreen.router)

fun NavHostController.navigateToAboutUsScreen() =
    safeNavigate(MenuRouter.AboutUsScreen.router)

fun NavHostController.navigateToDisclaimerScreen() =
    safeNavigate(MenuRouter.DisclaimerScreen.router)

fun NavHostController.navigateToCallCenterScreen() =
    safeNavigate(MenuRouter.CallCenterScreen.router)

fun NavHostController.navigateToGuideScreen() =
    safeNavigate(MenuRouter.GuideScreen.router)

fun NavHostController.navigateToSubmitCommentScreen() =
    safeNavigate(MenuRouter.SubmitCommentScreen.router)

fun NavHostController.navigateToRahyarScreen() =
    safeNavigate(MenuRouter.RahyarAddressScreen.router)