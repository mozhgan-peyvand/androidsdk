package ir.part.sdk.ara.common.ui.view.navigationHelper

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import timber.log.Timber

fun NavGraphBuilder.safeScreenInitial(
    sourceScreen: String,
    arguments: List<NamedNavArgument> = emptyList(),
    screenSetUp: @Composable (NavBackStackEntry) -> Unit,
    deepLink: List<NavDeepLink> = emptyList(),
    getArgumentName: List<String>? = emptyList()
) {
    composable(
        route = getArgument(sourceScreen, getArgumentName),
        arguments = arguments,
        deepLinks = deepLink
    ) { navBackStackEntry ->
        screenSetUp.invoke(navBackStackEntry)
    }
}

fun NavHostController.safeNavigate(
    destinationScreen: String,
    popUpTo: String? = null,
    inclusiveScreen: Boolean = false,
    args: List<Any>? = emptyList()
) {

    if (args?.isNotEmpty() == true) {
        navigate(route = passArgument(destinationScreen, args)) {
            if (popUpTo != null) {
                popUpTo(popUpTo) { inclusive = inclusiveScreen }
            }
        }
    } else {
        navigate(route = destinationScreen) {
            if (popUpTo != null) {
                popUpTo(popUpTo) { inclusive = inclusiveScreen }
            }
        }
    }
}

fun passArgument(router: String, args: List<Any>): String {
    return buildString {
        append(router)
        args.forEach { arg ->
            append("/$arg")
        }
    }
}

fun getArgument(router: String, value: List<String>?): String {
    return buildString {
        append(router)
        value?.forEach { arg ->
            append("/{$arg}")
        }
    }
}

