package ir.part.sdk.ara.ui.menu.util.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.ui.menu.screens.AboutUsScreen
import ir.part.sdk.ara.ui.menu.screens.DisclaimerScreen
import ir.part.sdk.ara.ui.menu.screens.MenuScreen
import ir.part.sdk.ara.ui.menu.screens.TermsAndConditionScreen

fun NavGraphBuilder.addMenuGraph(navController: NavHostController) {
    navigation(
        route = MenuRouter.Graph.router,
        startDestination = MenuRouter.MainMenuScreen.router
    ) {

        mainMenuScreen {
            MenuScreen(onChangePasswordClick = {
                navController.navigateToChangePasswordScreen()
            }, onTermsAndConditionClick = {
                navController.navigateToTermsAndConditionScreen()
            }, onAboutUsClick = {
                navController.navigateToAboutUsScreen()
            }, onDisclaimerClick = {
                navController.navigateToDisclaimerScreen()
            }) // todo add remained navigation from main menu screen
        }

        termsAndConditionsScreen {
            TermsAndConditionScreen(onNavigateUp = {
                navController.navigateUp()
            })
        }

        aboutUsScreen {
            AboutUsScreen(onNavigateUp = {
                navController.navigateUp()
            })
        }

        disclaimerScreen {
            DisclaimerScreen(onNavigateUp = {
                navController.navigateUp()
            })
        }

    }
}
