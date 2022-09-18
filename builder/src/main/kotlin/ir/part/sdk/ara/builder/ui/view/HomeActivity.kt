package ir.part.sdk.ara.builder.ui.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.MainScope
import ir.part.sdk.ara.base.event.MeratEvent
import ir.part.sdk.ara.base.event.MeratEventPublisher
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.builder.di.BuilderComponent
import ir.part.sdk.ara.builder.ui.bottomnavigation.*
import ir.part.sdk.ara.builder.util.addNamabarNavGraph
import ir.part.sdk.ara.builder.util.localizedContext
import ir.part.sdk.ara.common.ui.view.collectOnActivity
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.theme.AraTheme
import ir.part.sdk.ara.home.utils.navigation.HomeRouter
import ir.part.sdk.ara.home.utils.navigation.addHomeGraph
import ir.part.sdk.ara.ui.document.utils.navigation.addDocumentGraph
import ir.part.sdk.ara.ui.document.utils.navigation.navigateToFileListScreen
import ir.part.sdk.ara.ui.menu.util.navigation.MenuRouter
import ir.part.sdk.ara.ui.menu.util.navigation.addMenuGraph
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel
import ir.part.sdk.ara.ui.user.util.navigation.addUserGraph
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@MainScope
class HomeActivity : ComponentProviderActivity() {

    companion object {
        var appId = ""
    }

    @Inject
    lateinit var homeActivityFactory: ViewModelProvider.Factory

    private val tasksManagerViewModel: TasksManagerViewModel by (this as ComponentActivity).viewModels { homeActivityFactory }

    private var darkTheme = mutableStateOf(false)
    private lateinit var navController: NavHostController
    private var currentTask: TasksName? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideComponent().inject(this)

        setContent {

            var userHasDoc: Boolean? by remember {
                mutableStateOf(null)
            }

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
                        BottomBarScreen(navController = navController, currentTask)
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation()
                    }
                }
            }

            HandleUserHasDocState(userHasDoc)
            { hasDoc ->
                if (hasDoc == true) {
                    navController.navigateToFileListScreen()
                    currentTask = TasksName.USER_HAS_DOC
                    userHasDoc = null
                } else if (hasDoc == false) {
                    navController.navigateToRequestValidation()
                    userHasDoc = null
                }
            }

            LaunchedEffect(key1 = true, block =
            {
                tasksManagerViewModel.currentTask.collectLatest {
                    currentTask = it
                    when (it) {
                        TasksName.CHANG_PASS -> {
                            navController.navigateToChangePass()
                        }
                        TasksName.COMPLETE_INFO -> {
                            navController.navigateToNamabar()
                        }
                        TasksName.START_NEW_DOCUMENT -> {
                            tasksManagerViewModel.checkIfUserHasDoc { hasDoc ->
                                userHasDoc = hasDoc
                            }
                        }
                        TasksName.NO_TASK -> {}
                        else -> {}
                    }
                }
            })

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
        // todo : check null assertion
        super.attachBaseContext(localizedContext(base!!))
    }

    private fun provideComponent() = BuilderComponent.builder(this)

    @Composable
    fun AppNavigation() {
        NavHost(navController, startDestination = HomeRouter.HomeGraph.router) {
            addHomeGraph(navController)
            addUserGraph(navController, tasksManagerViewModel)
            addMenuGraph(navController)
            addDocumentGraph(navController, tasksManagerViewModel)
            addNamabarNavGraph(navController, tasksManagerViewModel)
        }
    }

    @Composable
    private fun HandleUserHasDocState(userHasDoc: Boolean?, onValueChange: (Boolean?) -> Unit) {
        onValueChange(userHasDoc)
    }
}
