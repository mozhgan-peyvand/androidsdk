package ir.part.sdk.ara.builder.ui.bottomnavigation

import androidx.navigation.NavHostController
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeNavigate

fun NavHostController.navigateToRequestValidation() {
    // TODO: when validation task in done using screen hear
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
    // TODO: when documentList task in done using screen hear
    safeNavigate(
        destinationScreen = BottomNavigationItems.Document.route,
    )
}

fun navigateToPersonalInfo() {
    // TODO: setup namabar builder

}

fun NavHostController.navigateToLogin() {
    popBackStack()
    safeNavigate(
        destinationScreen = UiUserSharedIds.UserLogin.router,
        popUpTo = UiUserSharedIds.UserLogin.router,
        inclusiveScreen = true
    )
}