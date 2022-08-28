package ir.part.sdk.ara.ui.menu.util.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.common.ui.view.di.daggerViewModel
import ir.part.sdk.ara.ui.menu.di.MenuScreenComponent
import ir.part.sdk.ara.ui.menu.di.RahyarScreenComponent
import ir.part.sdk.ara.ui.menu.di.SubmitCommentScreenComponent
import ir.part.sdk.ara.ui.menu.screens.*
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentScreen
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentViewModel
import ir.part.sdk.ara.ui.menu.screens.rahyar.RahyarScreen
import ir.part.sdk.ara.ui.menu.screens.rahyar.RahyarViewModel
import ir.part.sdk.ara.ui.shared.feature.di.SharedFeatureComponent
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel

fun NavGraphBuilder.addMenuGraph(navController: NavHostController) {
    navigation(
        route = MenuRouter.Graph.router,
        startDestination = MenuRouter.MainMenuScreen.router
    ) {

        mainMenuScreen {
            val menuViewModel: MenuViewModel = daggerViewModel {
                MenuScreenComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getMenuViewModel()
            }

            MenuScreen(
                menuViewModel = menuViewModel,
                onChangePasswordClick = {
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
                }, onRahyarClick = {
                    navController.navigateToRahyarScreen()
                })
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
            val captchaViewModel: CaptchaViewModel = daggerViewModel {
                SharedFeatureComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getCaptchaViewModel()
            }

            val commentViewModel: SubmitCommentViewModel = daggerViewModel {
                SubmitCommentScreenComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getViewModel()
            }

            SubmitCommentScreen(onNavigateUp = {
                navController.navigateUp()
            }, submitCommentViewModel = commentViewModel, captchaViewModel = captchaViewModel)
        }

        rahyarScreen {
            val rahyarViewModel: RahyarViewModel = daggerViewModel {
                RahyarScreenComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getRahyarViewModel()
            }
            RahyarScreen(rahyarViewModel = rahyarViewModel) {
                navController.popBackStack()
            }
        }
    }
}
