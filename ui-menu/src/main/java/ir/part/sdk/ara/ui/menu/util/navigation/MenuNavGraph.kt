package ir.part.sdk.ara.ui.menu.util.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.ui.menu.screens.*

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
            }, onCallCenterClick = {
                navController.navigateToCallCenterScreen()
            }, onGuideClick = {
                navController.navigateToGuideScreen()
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

        callCenterScreen {
            CallCenterScreen(onNavigateUp = {
                navController.navigateUp()
            })
        }

        guideScreen {
            GuideScreen(onNavigateUp = {
                navController.navigateUp()
            })
        }

    }
}
