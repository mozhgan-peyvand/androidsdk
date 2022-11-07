package ir.part.sdk.ara.builder.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.builder.ui.bottomnavigation.BottomNavigationItems
import ir.part.sdk.ara.builder.ui.bottomnavigation.navigateToRequestValidation
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.navigationHelper.safeScreenInitial
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel
import ir.part.sdk.merat.ui.menu.R

fun NavGraphBuilder.addNamabarNavGraph(
    navController: NavHostController,
    tasksManagerViewModel: TasksManagerViewModel,
    onFullScreen: (Boolean) -> Unit,
) {

    navigation(
        route = "namabarGraph",
        startDestination = BottomNavigationItems.PersonalInfo.route
    ) {

        namabarScreen {
            with(tasksManagerViewModel) {

                val loadingErrorState =
                    rememberFlowWithLifecycle(flow = tasksManagerViewModel.loadingAndMessageState).collectAsState(
                        initial = PublicState.Empty
                    )
                ProcessLoadingAndErrorState(
                    loadingErrorState.value,
                    onErrorDialogDismissed = {
                        tasksManagerViewModel.loadingAndMessageState.value.message = null
                    })

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
                        onFullScreen(it)
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

@Composable
private fun ProcessLoadingAndErrorState(
    input: PublicState?,
    onErrorDialogDismissed: () -> Unit,
) {
    val dialog = getErrorDialog(
        title = stringResource(id = R.string.ara_msg_general_error_title),
        description = "",
        submitAction = {
            onErrorDialogDismissed()
        }
    )
    val loadingDialog = getLoadingDialog()

    if (input?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        input?.message?.let { messageModel ->
            dialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}