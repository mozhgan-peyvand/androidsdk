package ir.part.sdk.ara.builder.ui.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.MainScope
import ir.part.sdk.ara.base.event.MeratEvent
import ir.part.sdk.ara.base.event.MeratEventPublisher
import ir.part.sdk.ara.builder.di.BuilderComponent
import ir.part.sdk.ara.builder.ui.bottomnavigation.BottomBarScreen
import ir.part.sdk.ara.builder.ui.bottomnavigation.navigateToLogin
import ir.part.sdk.ara.builder.util.localizedContext
import ir.part.sdk.ara.common.ui.view.collectOnActivity
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.theme.AraTheme
import ir.part.sdk.ara.home.utils.navigation.HomeRouter
import ir.part.sdk.ara.home.utils.navigation.addHomeGraph
import ir.part.sdk.ara.ui.document.utils.navigation.addDocumentGraph
import ir.part.sdk.ara.ui.menu.util.navigation.MenuRouter
import ir.part.sdk.ara.ui.menu.util.navigation.addMenuGraph
import ir.part.sdk.ara.ui.user.util.navigation.addUserGraph


@MainScope
class HomeActivity : ComponentProviderActivity() {

    companion object {
        var appId = ""
    }

    private var darkTheme = mutableStateOf(false)
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideComponent().inject(this)

        setContent {
            darkTheme = rememberSaveable {
                mutableStateOf(false)
            }
            navController = rememberNavController()

            // handle soft input mode
            when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                MenuRouter.SubmitCommentScreen.router, UiUserSharedIds.UserChangePassword.router -> {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
                else -> {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                }
            }

            AraTheme(darkTheme = darkTheme.value) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            color = MaterialTheme.colors.background
                        ),
                    bottomBar = {
                        BottomBarScreen(navController = navController)
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation()
                    }
                }

            }
        }

        MeratEventPublisher.events.collectOnActivity(this@HomeActivity) {
            if (it == MeratEvent.TokenExpired) {
                navController.navigateToLogin()
            }
        }
    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        localizedContext(context)
        return super.onCreateView(name, context, attrs)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(localizedContext(base!!))
    }

    private fun provideComponent() = BuilderComponent.builder(this)

    @Composable
    fun AppNavigation() {
        NavHost(navController, startDestination = HomeRouter.HomeGraph.router) {
            addHomeGraph(navController)
            addUserGraph(navController)
            addMenuGraph(navController)
            addDocumentGraph(navController)
        }
    }
}
