package ir.part.sdk.ara.ui.user.util.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeScreenInitial


fun NavGraphBuilder.homeScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = UserRouter.Graph.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

fun NavGraphBuilder.registerScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = UserRouter.UserRegisterScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

fun NavGraphBuilder.loginScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = UserRouter.UserLoginScreen.router,
        screenSetUp = { screen.invoke(it) }
    )
}

fun NavGraphBuilder.forgetPasswordScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = UserRouter.UserForgetPasswordScreen.router,
        screenSetUp = { screen.invoke(it) }
    )
}

fun NavGraphBuilder.forgetPasswordVerificationScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = UserRouter.UserForgetPasswordVerificationScreen.router,
        screenSetUp = {
            screen.invoke(it)
        },
        arguments = listOf(
            navArgument("nationalCode") {
                type = NavType.StringType
                nullable = true
                defaultValue = ""
            }
        ),
        getArgumentName = listOf(
            "nationalCode"
        )
    )
}

fun NavGraphBuilder.changePasswordScreen(screen: @Composable (NavBackStackEntry) -> Unit) {
    safeScreenInitial(
        sourceScreen = UserRouter.UserChangePasswordScreen.router,
        screenSetUp = {
            screen.invoke(it)
        })
}