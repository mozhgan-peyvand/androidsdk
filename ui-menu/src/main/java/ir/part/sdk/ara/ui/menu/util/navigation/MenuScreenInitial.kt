package ir.part.sdk.ara.ui.menu.util.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeScreenInitial

fun NavGraphBuilder.mainMenuScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = MenuRouter.MainMenuScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

fun NavGraphBuilder.termsAndConditionsScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = MenuRouter.TermsAndConditionsScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

fun NavGraphBuilder.aboutUsScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = MenuRouter.AboutUsScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

fun NavGraphBuilder.disclaimerScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = MenuRouter.DisclaimerScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

fun NavGraphBuilder.callCenterScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = MenuRouter.CallCenterScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}