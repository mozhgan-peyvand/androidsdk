package ir.part.sdk.ara.ui.user.util.navigation

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.ui.shared.feature.di.SharedFeatureComponent
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel
import ir.part.sdk.ara.ui.user.di.UserComponent
import ir.part.sdk.ara.ui.user.screens.changePassword.ChangePasswordScreen
import ir.part.sdk.ara.ui.user.screens.changePassword.ChangePasswordViewModel
import ir.part.sdk.ara.ui.user.screens.forgetPassword.ForgetPasswordScreen
import ir.part.sdk.ara.ui.user.screens.forgetPassword.ForgetPasswordViewModel
import ir.part.sdk.ara.ui.user.screens.forgetPasswordVerification.ForgetPasswordVerificationScreen
import ir.part.sdk.ara.ui.user.screens.forgetPasswordVerification.ForgetPasswordVerificationViewModel
import ir.part.sdk.ara.ui.user.screens.login.LoginScreen
import ir.part.sdk.ara.ui.user.screens.login.LoginViewModel
import ir.part.sdk.ara.ui.user.screens.register.RegisterScreen
import ir.part.sdk.ara.ui.user.screens.register.RegisterViewModel

fun NavGraphBuilder.addUserGraph(
    navController: NavHostController,
    tasksManagerViewModel: TasksManagerViewModel
) {
    navigation(
        route = UserRouter.Graph.router,
        startDestination = UserRouter.UserRegisterScreen.router
    ) {

        registerScreen {

            val viewModelStoreOwnerCaptcha = remember {
                navController.getBackStackEntry(UserRouter.UserRegisterScreen.router)
            }

            val captchaViewModel: CaptchaViewModel = viewModel(
                factory = SharedFeatureComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getCaptchaViewModel(),
                viewModelStoreOwner = viewModelStoreOwnerCaptcha

            )

            val registerViewModel: RegisterViewModel = viewModel(
                factory = UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getRegisterViewModel(),
                viewModelStoreOwner = it
            )

            RegisterScreen(
                captchaViewModel = captchaViewModel,
                navigateToLogin = {
                    navController.navigateToLoginScreen(
                        popUpTo = UserRouter.UserRegisterScreen.router,
                        inclusive = true
                    )
                },
                registerViewModel = registerViewModel,
                onNavigateUp = { navController.navigateUp() }
            )
        }
        loginScreen {

            val viewModelStoreOwnerCaptcha = remember {
                navController.getBackStackEntry(UserRouter.UserLoginScreen.router)
            }

            val captchaViewModel: CaptchaViewModel = viewModel(
                factory = SharedFeatureComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getCaptchaViewModel(),
                viewModelStoreOwner = viewModelStoreOwnerCaptcha

            )

            val loginViewModel: LoginViewModel = viewModel(
                factory = UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getLoginViewModel(),
                viewModelStoreOwner = it
            )

            LoginScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                captchaViewModel = captchaViewModel,
                navigateToForgetPassword = { navController.navigateToForgetPasswordScreen() },
                loginViewModel = loginViewModel,
                tasksManagerViewModel = tasksManagerViewModel
            )
        }

        forgetPasswordScreen {
            val viewModelStoreOwnerCaptcha = remember {
                navController.getBackStackEntry(UserRouter.UserForgetPasswordScreen.router)
            }

            val captchaViewModel: CaptchaViewModel = viewModel(
                factory = SharedFeatureComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getCaptchaViewModel(),
                viewModelStoreOwner = viewModelStoreOwnerCaptcha
            )

            val forgetPasswordViewModel: ForgetPasswordViewModel = viewModel(
                factory = UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getForgetPasswordViewModel(),
                viewModelStoreOwner = it
            )

            ForgetPasswordScreen(
                captchaViewModel = captchaViewModel,
                navigateToVerificationForgetPassword = { nationalCode ->
                    navController.navigateToVerificationPasswordScreen(
                        nationalCode
                    )
                },
                forgetPasswordViewModel = forgetPasswordViewModel,
                onNavigateUp = { navController.navigateUp() }
            )
        }
        forgetPasswordVerificationScreen {
            val forgetPasswordVerificationViewModel: ForgetPasswordVerificationViewModel =
                viewModel(
                    factory = UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                        .getForgetPasswordVerificationViewModel(),
                    viewModelStoreOwner = it
                )
            ForgetPasswordVerificationScreen(
                nationalCode = it.arguments?.get("nationalCode")?.toString(),
                navigateToLogin = {
                    navController.navigateToLoginScreen(
                        popUpTo = UserRouter.UserLoginScreen.router,
                        inclusive = true
                    )
                },
                navigateToForgetPassword = { navController.navigateToForgetPasswordScreen() },
                forgetPasswordVerificationViewModel = forgetPasswordVerificationViewModel,
                onNavigateUp = { navController.navigateUp() }
            )
        }
        changePasswordScreen {

            val changePasswordViewModel: ChangePasswordViewModel = viewModel(
                factory = UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getChangePasswordViewModel(),
                viewModelStoreOwner = it
            )

            ChangePasswordScreen(
                changePasswordViewModel = changePasswordViewModel, onNavigateUp = {
                    navController.navigateUp()
                },
                tasksManagerViewModel = tasksManagerViewModel
            )
        }
    }
}
