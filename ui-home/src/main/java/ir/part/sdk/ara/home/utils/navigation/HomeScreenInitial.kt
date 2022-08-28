package ir.part.sdk.ara.home.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeScreenInitial


fun NavGraphBuilder.splashScreen(screen: @Composable (NavBackStackEntry) -> Unit) =
    safeScreenInitial(
        sourceScreen = HomeRouter.SplashScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )

fun NavGraphBuilder.userHomeScreen(screen: @Composable (NavBackStackEntry) -> Unit) =
    safeScreenInitial(
        sourceScreen = UiUserSharedIds.UserHomeScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )

