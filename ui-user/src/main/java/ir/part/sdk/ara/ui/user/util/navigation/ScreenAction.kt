package ir.part.sdk.ara.ui.user.util.navigation

import androidx.navigation.NavHostController
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeNavigate


fun NavHostController.navigateToLoginScreen(popUpTo: String? = null, inclusive: Boolean = false) =
    safeNavigate(
       destinationScreen = UserRouter.UserLoginScreen.router,
        popUpTo = popUpTo,
        inclusiveScreen = inclusive
    )

fun NavHostController.navigateToForgetPasswordScreen() =
    safeNavigate(UserRouter.UserForgetPasswordScreen.router)

fun NavHostController.navigateToChangePasswordScreen() =
    safeNavigate(UserRouter.UserChangePasswordScreen.router)

fun NavHostController.navigateToVerificationPasswordScreen(param: String) =
    safeNavigate(UserRouter.UserForgetPasswordVerificationScreen.router, args = listOf(param))