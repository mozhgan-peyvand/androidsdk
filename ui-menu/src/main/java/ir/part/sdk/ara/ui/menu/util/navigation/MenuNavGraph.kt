package ir.part.sdk.ara.ui.menu.util.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.ui.menu.screens.MenuScreen
import ir.part.sdk.ara.ui.user.util.navigation.navigateToChangePasswordScreen

fun NavGraphBuilder.addMenuGraph(navController: NavHostController) {
    navigation(
        route = MenuRouter.Graph.router,
        startDestination = MenuRouter.MainMenuScreen.router
    ) {

        mainMenuScreen {
            MenuScreen(onChangePasswordClick = {
                navController.navigateToChangePasswordScreen()
            }) // todo add remained navigation from main menu screen
        }

    }
}
