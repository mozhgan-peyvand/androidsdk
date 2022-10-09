package ir.part.sdk.ara.home.utils.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.home.di.HomeComponent
import ir.part.sdk.ara.home.ui.SplashScreen
import ir.part.sdk.ara.home.ui.UserHomeScreen
import ir.part.sdk.ara.home.version.VersionViewModel

fun NavGraphBuilder.addHomeGraph(navController: NavHostController) {

    navigation(
        route = HomeRouter.HomeGraph.router,
        startDestination = HomeRouter.SplashScreen.router
    ) {

        splashScreen {
            SplashScreen(navigateToUserHomeScreen = {
                navController.navigateToUserHomeScreen()
            })
        }
        userHomeScreen {
            val versionViewModel: VersionViewModel = viewModel(
                factory = HomeComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getVersion(),
                viewModelStoreOwner = it
            )

            UserHomeScreen(versionViewModel = versionViewModel,
                navigateToLoginScreen = {
                    navController.navigateToUserLoginScreen()
                }, navigateToRegisterScreen = {
                    navController.navigateToUserRegisterScreen()
                }
            )
        }
    }
}