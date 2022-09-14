package ir.part.sdk.ara.builder.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.builder.ui.bottomnavigation.BottomNavigationItems
import ir.part.sdk.ara.builder.ui.bottomnavigation.navigateToRequestValidation
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeScreenInitial
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel

fun NavGraphBuilder.addNamabarNavGraph(
    navController: NavHostController,
    tasksManagerViewModel: TasksManagerViewModel
) {

    navigation(
        route = "namabarGraph",
        startDestination = BottomNavigationItems.PersonalInfo.route
    ) {

        namabarScreen {
            with(tasksManagerViewModel) {
                namabarBuilder(
                    token = getTokenLocal(),
                    userName = getNationalCodeLocal(),
                    taskInstanceId = getTaskInstanceIdLocal(),
                    processInstanceId = getProcessInstanceIdLocal(),
                    onPostRequest = {
                        if (tasksManagerViewModel.getDoingTaskName == TasksName.COMPLETE_INFO) {
                            done()
                        } else {
                            navController.navigateToRequestValidation()
                        }
                    },
                    onFullScreenChangeState = {
                    }
                ).show()
            }
        }
    }
}

fun NavGraphBuilder.namabarScreen(screen: @Composable (NavBackStackEntry) -> Unit) =
    safeScreenInitial(
        sourceScreen = BottomNavigationItems.PersonalInfo.route,
        screenSetUp = {
            screen.invoke(it)
        }
    )