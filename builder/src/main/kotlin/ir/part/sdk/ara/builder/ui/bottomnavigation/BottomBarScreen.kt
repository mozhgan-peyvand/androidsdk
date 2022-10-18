package ir.part.sdk.ara.builder.ui.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.builder.R
import ir.part.sdk.ara.common.ui.view.disabled
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog

@Composable
fun BottomBarScreen(navController: NavHostController, currentTask: TasksName?) {
    BottomBar(navController = navController, currentTask)
}

@Composable
private fun BottomBar(navController: NavHostController, chooseTask: TasksName?) {
    val screens = listOf(
        BottomNavigationItems.Document,
        BottomNavigationItems.PersonalInfo,
        BottomNavigationItems.SubmitRequest,
        BottomNavigationItems.Menu
    )
    val showBottomBar =
        navController.currentBackStackEntryAsState().value?.destination?.route in screens.map { it.route }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (showBottomBar) {
        BottomNavigation {
            when (chooseTask) {
                TasksName.COMPLETE_INFO -> {
                    AddItem(
                        screen = BottomNavigationItems.Document,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToDocumentList()
                        },
                        itemEnable = false,
                        onDismissAccessDeniedDialog = {
                            if (currentDestination?.route != BottomNavigationItems.PersonalInfo.route) {
                                navController.navigateToNamabar()
                            }
                        }
                    )
                    AddItem(
                        screen = BottomNavigationItems.PersonalInfo,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToNamabar()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.SubmitRequest,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToRequestValidation()
                        },
                        itemEnable = false,
                        onDismissAccessDeniedDialog = {
                            if (currentDestination?.route != BottomNavigationItems.PersonalInfo.route) {
                                navController.navigateToNamabar()
                            }
                        }
                    )
                    AddItem(
                        screen = BottomNavigationItems.Menu,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToMenu()
                        },
                        itemEnable = true
                    )
                }
                TasksName.START_NEW_DOCUMENT -> {
                    AddItem(
                        screen = BottomNavigationItems.Document,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToDocumentList()
                        },
                        itemEnable = false,
                        onDismissAccessDeniedDialog = {
                            if (currentDestination?.route != BottomNavigationItems.SubmitRequest.route) {
                                navController.navigateToRequestValidation()
                            }
                        }
                    )
                    AddItem(
                        screen = BottomNavigationItems.PersonalInfo,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToNamabar()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.SubmitRequest,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToRequestValidation()
                        },
                        itemEnable = true,
                    )
                    AddItem(
                        screen = BottomNavigationItems.Menu,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToMenu()
                        },
                        itemEnable = true
                    )
                }
                TasksName.USER_HAS_DOC -> {
                    AddItem(
                        screen = BottomNavigationItems.Document,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToDocumentList()
                        },
                        itemEnable = true,
                    )
                    AddItem(
                        screen = BottomNavigationItems.PersonalInfo,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToNamabar()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.SubmitRequest,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToRequestValidation()
                        },
                        itemEnable = true,
                    )
                    AddItem(
                        screen = BottomNavigationItems.Menu,
                        currentDestination = currentDestination,
                        currentTask = chooseTask,
                        itemNavigation = {
                            navController.navigateToMenu()
                        },
                        itemEnable = true
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.AddItem(
    screen: BottomNavigationItems,
    currentDestination: NavDestination?,
    currentTask: TasksName?,
    itemNavigation: () -> Unit,
    itemEnable: Boolean,
    onDismissAccessDeniedDialog: (() -> Unit)? = null,
) {
    var currentTaskState by remember {
        mutableStateOf<TasksName?>(null)
    }
    HandleShowAlertDialog(
        currentTask = currentTaskState,
        onSubmitDialog = {
            currentTaskState = null
            onDismissAccessDeniedDialog?.invoke()
        })

    BottomNavigationItem(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        label = {
            Text(text = stringResource(id = screen.title))
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = MaterialTheme.colors.disabled(),
        selectedContentColor = ColorBlueDarker,
        onClick = {
            if (currentDestination?.route == screen.route) return@BottomNavigationItem
            if (itemEnable) {
                itemNavigation()
            } else {
                currentTaskState = currentTask
            }
        }
    )
}

@Composable
private fun HandleShowAlertDialog(onSubmitDialog: () -> Unit, currentTask: TasksName?) {
    val dialog = getErrorDialog(
        title = stringResource(id = R.string.label_access_denied),
        description = "",
        submitAction = {
            onSubmitDialog()
        }
    )

    when (currentTask) {
        TasksName.COMPLETE_INFO -> {
            dialog.setDialogDetailMessage(stringResource(id = R.string.msg_access_denied_for_personal_info_task))
            dialog.show()
        }
        TasksName.START_NEW_DOCUMENT -> {
            dialog.setDialogDetailMessage(stringResource(id = R.string.msg_access_denied_for_start_new_doc_task))
            dialog.show()
        }
        else -> {
            dialog.dismiss()
        }
    }
}

