package ir.part.sdk.ara.builder.ui.bottomnavigation

import androidx.navigation.NavHostController
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeNavigate

fun NavHostController.navigateToRequestValidation() {
    safeNavigate(
        destinationScreen = BottomNavigationItems.SubmitRequest.route,
    )
}

fun NavHostController.navigateToMenu() {
    safeNavigate(
        destinationScreen = BottomNavigationItems.Menu.route,
    )
}

fun NavHostController.navigateToDocumentList() {
    safeNavigate(
        destinationScreen = BottomNavigationItems.Document.route,
    )
}

fun NavHostController.navigateToNamabar() {
    safeNavigate(
        destinationScreen = BottomNavigationItems.PersonalInfo.route
    )
}

fun NavHostController.navigateToChangePass() {
    safeNavigate(
        destinationScreen = UiUserSharedIds.UserChangePassword.router,
    )
}

fun NavHostController.navigateToLogin() {
    popBackStack()
    safeNavigate(
        destinationScreen = UiUserSharedIds.UserLogin.router,
        popUpTo = UiUserSharedIds.UserLogin.router,
        inclusiveScreen = true
    )
}