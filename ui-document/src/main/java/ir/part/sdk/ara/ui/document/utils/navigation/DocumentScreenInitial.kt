package ir.part.sdk.ara.ui.document.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeScreenInitial


fun NavGraphBuilder.submitScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = DocumentRouter.DocumentSubmitScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

fun NavGraphBuilder.resultValidationScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = DocumentRouter.ResultValidationScreenViewPager.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

fun NavGraphBuilder.fileListScreen(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = DocumentRouter.DocumentFileListScreen.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}


fun NavGraphBuilder.fileDetailScreenViewPager(
    screen: @Composable (NavBackStackEntry) -> Unit
) {
    safeScreenInitial(
        sourceScreen = DocumentRouter.DetailsScreenViewPager.router,
        screenSetUp = {
            screen.invoke(it)
        }
    )
}

