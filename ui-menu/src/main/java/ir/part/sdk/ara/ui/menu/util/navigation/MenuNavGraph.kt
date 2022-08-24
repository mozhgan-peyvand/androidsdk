package ir.part.sdk.ara.ui.menu.util.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.common.ui.view.di.daggerViewModel
import ir.part.sdk.ara.ui.menu.di.SubmitCommentScreenComponent
import ir.part.sdk.ara.ui.menu.screens.*
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentScreen
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentViewModel

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
            }, onSubmitCommentClick = {
                navController.navigateToSubmitCommentScreen()
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

        submitScreenScreen {
            val commentViewModel: SubmitCommentViewModel = daggerViewModel {
                SubmitCommentScreenComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getViewModel()
            }

            SubmitCommentScreen(onNavigateUp = {
                navController.navigateUp()
            }, viewModel = commentViewModel)
        }

    }
}
