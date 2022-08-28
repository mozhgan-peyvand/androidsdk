package ir.part.sdk.ara.home.utils.navigation

import androidx.navigation.NavHostController
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeNavigate

fun NavHostController.navigateToUserHomeScreen() {
    safeNavigate(
        destinationScreen = UiUserSharedIds.UserHomeScreen.router,
        popUpTo = HomeRouter.SplashScreen.router,
        inclusiveScreen = true
    )
}

fun NavHostController.navigateToUserRegisterScreen() {
    safeNavigate(
        destinationScreen = HomeRouter.UserRegisterScreen.router,
    )
}

fun NavHostController.navigateToUserLoginScreen() {
    safeNavigate(
        destinationScreen = HomeRouter.UserLoginScreen.router,
    )
}