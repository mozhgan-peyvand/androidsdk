package ir.part.sdk.ara.ui.user.util.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.ui.shared.feature.di.SharedFeatureComponent
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel
import ir.part.sdk.ara.ui.user.di.UserComponent
import ir.part.sdk.ara.ui.user.screens.changePassword.ChangePasswordScreen
import ir.part.sdk.ara.ui.user.screens.forgetPassword.ForgetPasswordScreen
import ir.part.sdk.ara.ui.user.screens.forgetPasswordVerification.ForgetPasswordVerificationScreen
import ir.part.sdk.ara.ui.user.screens.login.LoginScreen
import ir.part.sdk.ara.ui.user.screens.register.RegisterScreen

fun NavGraphBuilder.addUserGraph(navController: NavHostController) {
    navigation(
        route = UserRouter.Graph.router,
        startDestination = UserRouter.UserRegisterScreen.router
    ) {

        var captchaViewModel: CaptchaViewModel? = null

        registerScreen {
            var showCaptcha by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(key1 = Unit) {
                showCaptcha = true
            }
            if (showCaptcha) {
                captchaViewModel =
                    SharedFeatureComponent.builder(LocalContext.current as ComponentProviderActivity)
                        .getCaptchaViewModel()
                showCaptcha = false
            }
            val registerViewModel =
                UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getRegisterViewModel()
            RegisterScreen(
                captchaViewModel = captchaViewModel,
                navigateToLogin = { navController.navigateToLoginScreen() },
                registerViewModel = registerViewModel
            )
        }
        loginScreen {
            var showCaptcha by remember {
                mutableStateOf(true)
            }
            LaunchedEffect(key1 = Unit) {
                showCaptcha = true
            }
            if (showCaptcha) {
                captchaViewModel =
                    SharedFeatureComponent.builder(LocalContext.current as ComponentProviderActivity)
                        .getCaptchaViewModel()
                showCaptcha = false
            }
            val loginViewModel =
                UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getLoginViewModel()

            LoginScreen(
                captchaViewModel = captchaViewModel,
                navigateToForgetPassword = { navController.navigateToForgetPasswordScreen() },
                navigateToDocument = { navController.navigateToSubmitDocumentScreen() },
                loginViewModel = loginViewModel
            )
        }

        forgetPasswordScreen {
            var showCaptcha by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = Unit) {
                showCaptcha = true
            }
            if (showCaptcha) {
                captchaViewModel =
                    SharedFeatureComponent.builder(LocalContext.current as ComponentProviderActivity)
                        .getCaptchaViewModel()
                showCaptcha = false

            }
            val forgetPasswordViewModel =
                UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getForgetPasswordViewModel()
            ForgetPasswordScreen(
                captchaViewModel = captchaViewModel,
                navigateToVerificationForgetPassword = { nationalCode ->
                    navController.navigateToVerificationPasswordScreen(
                        nationalCode
                    )
                },
                forgetPasswordViewModel = forgetPasswordViewModel
            )
        }
        forgetPasswordVerificationScreen {
            val forgetPasswordVerificationViewModel =
                UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getForgetPasswordVerificationViewModel()
            ForgetPasswordVerificationScreen(
                nationalCode = it.arguments?.get("nationalCode")?.toString(),
                navigateToLogin = {
                    navController.navigateToLoginScreen(
                        popUpTo = UserRouter.UserLoginScreen.router,
                        inclusive = true
                    )
                },
                navigateUp = { navController.navigateToForgetPasswordScreen() },
                forgetPasswordVerificationViewModel = forgetPasswordVerificationViewModel
            )
        }
        changePasswordScreen {
            val changePasswordViewModel =
                UserComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getChangePasswordViewModel()
            ChangePasswordScreen(changePasswordViewModel = changePasswordViewModel, onNavigateUp = {
                navController.navigateUp()
            })
        }
    }
}
