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
import ir.part.sdk.ara.common.ui.view.disabled
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker

@Composable
fun BottomBarScreen(navController: NavHostController) {
    BottomBar(navController = navController)
}

@Composable
private fun BottomBar(navController: NavHostController) {
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
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                ) {
                    when (screen) {
                        is BottomNavigationItems.Document -> {
                            navController.navigateToDocumentList()
                        }
                        is BottomNavigationItems.PersonalInfo -> {
                            navController.navigateToNamabar()
                        }
                        is BottomNavigationItems.SubmitRequest -> {
                            navController.navigateToRequestValidation()
                        }
                        is BottomNavigationItems.Menu -> {
                            navController.navigateToMenu()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.AddItem(
    screen: BottomNavigationItems,
    currentDestination: NavDestination?,
    itemNavigation: () -> Unit
) {
    BottomNavigationItem(modifier = Modifier
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
            itemNavigation()
        }
    )
}

