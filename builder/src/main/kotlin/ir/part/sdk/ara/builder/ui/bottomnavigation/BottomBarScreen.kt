package ir.part.sdk.ara.builder.ui.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.common.ui.view.disabled
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker

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
                        itemNavigation = {
                            navController.navigateToDocumentList()
                        },
                        itemEnable = false
                    )
                    AddItem(
                        screen = BottomNavigationItems.PersonalInfo,
                        currentDestination = currentDestination,
                        itemNavigation = {
                            navController.navigateToNamabar()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.SubmitRequest,
                        currentDestination = currentDestination,
                        itemNavigation = {
                            navController.navigateToRequestValidation()
                        },
                        itemEnable = false
                    )
                    AddItem(
                        screen = BottomNavigationItems.Menu,
                        currentDestination = currentDestination,
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
                        itemNavigation = {
                            navController.navigateToDocumentList()
                        },
                        itemEnable = false
                    )
                    AddItem(
                        screen = BottomNavigationItems.PersonalInfo,
                        currentDestination = currentDestination,
                        itemNavigation = {
                            navController.navigateToNamabar()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.SubmitRequest,
                        currentDestination = currentDestination,
                        itemNavigation = {
                            navController.navigateToRequestValidation()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.Menu,
                        currentDestination = currentDestination,
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
                        itemNavigation = {
                            navController.navigateToDocumentList()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.PersonalInfo,
                        currentDestination = currentDestination,
                        itemNavigation = {
                            navController.navigateToNamabar()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.SubmitRequest,
                        currentDestination = currentDestination,
                        itemNavigation = {
                            navController.navigateToRequestValidation()
                        },
                        itemEnable = true
                    )
                    AddItem(
                        screen = BottomNavigationItems.Menu,
                        currentDestination = currentDestination,
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
    itemNavigation: () -> Unit,
    itemEnable: Boolean
) {
    BottomNavigationItem(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        enabled = itemEnable,
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
            if (currentDestination?.route != screen.route) {
                itemNavigation()
            }
        }
    )
}

