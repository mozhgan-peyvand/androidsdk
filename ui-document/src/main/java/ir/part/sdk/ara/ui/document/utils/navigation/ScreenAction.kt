package ir.part.sdk.ara.ui.document.utils.navigation

import androidx.navigation.NavHostController
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeNavigate

fun NavHostController.navigateToSubmitScreen() = safeNavigate(
    DocumentRouter.DocumentSubmitScreen.router
)

fun NavHostController.navigateToFileListScreen() = safeNavigate(
    DocumentRouter.DocumentFileListScreen.router
)

fun NavHostController.navigateToDetail() = safeNavigate(
    DocumentRouter.DetailsScreenViewPager.router
)

fun NavHostController.navigateToResultValidation() = safeNavigate(
    DocumentRouter.ResultValidationScreenViewPager.router
)

