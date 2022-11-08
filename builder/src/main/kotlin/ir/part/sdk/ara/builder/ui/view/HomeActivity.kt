package ir.part.sdk.ara.builder.ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.MainScope
import ir.part.sdk.ara.base.event.MeratEvent
import ir.part.sdk.ara.base.event.MeratEventPublisher
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.builder.R
import ir.part.sdk.ara.builder.di.BuilderComponent
import ir.part.sdk.ara.builder.ui.bottomnavigation.*
import ir.part.sdk.ara.builder.util.addNamabarNavGraph
import ir.part.sdk.ara.builder.util.localizedContext
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.collectOnActivity
import ir.part.sdk.ara.common.ui.view.ids.UiUserSharedIds
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.AraTheme
import ir.part.sdk.ara.common.ui.view.utils.dialog.DialogManager
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getExitAppDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.home.utils.navigation.HomeRouter
import ir.part.sdk.ara.home.utils.navigation.addHomeGraph
import ir.part.sdk.ara.home.utils.navigation.navigateToUserHomeScreen
import ir.part.sdk.ara.ui.document.utils.navigation.DocumentRouter
import ir.part.sdk.ara.ui.document.utils.navigation.addDocumentGraph
import ir.part.sdk.ara.ui.document.utils.navigation.navigateToFileListScreen
import ir.part.sdk.ara.ui.menu.util.navigation.MenuRouter
import ir.part.sdk.ara.ui.menu.util.navigation.addMenuGraph
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel
import ir.part.sdk.ara.ui.user.util.navigation.addUserGraph
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@MainScope
class HomeActivity : ComponentProviderActivity() {

    companion object {
        var appId = ""
    }

    private var mBackPressed: Long = 0
    private val timeInterVal = 2000

    @Inject
    lateinit var homeActivityFactory: ViewModelProvider.Factory

    private val tasksManagerViewModel: TasksManagerViewModel by (this as ComponentActivity).viewModels { homeActivityFactory }

    private val homeViewModel: HomeViewModel by (this as ComponentActivity).viewModels { homeActivityFactory }

    private var darkTheme = mutableStateOf(false)
    private lateinit var navController: NavHostController
    private var currentTask: TasksName? = null

    private lateinit var exitDialog: DialogManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideComponent().inject(this)

        setContent {
            val homeLoadingErrorState =
                rememberFlowWithLifecycle(flow = homeViewModel.loadingAndMessageState).collectAsState(
                    initial = PublicState.Empty
                )
            ProcessLoadingAndErrorState(input = homeLoadingErrorState.value)

            var userHasDoc: Boolean? by remember {
                mutableStateOf(null)
            }

            darkTheme = rememberSaveable {
                mutableStateOf(false)
            }
            navController = rememberNavController()

            var isFullScreen: Boolean by remember {
                mutableStateOf(true)
            }

            // handle soft input mode
            when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                MenuRouter.SubmitCommentScreen.router, UiUserSharedIds.UserChangePassword.router -> {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    if (isFullScreen) isFullScreen = false
                }
                BottomNavigationItems.PersonalInfo.route -> Unit
                else -> {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                    if (isFullScreen) isFullScreen = false
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
                        if (!isFullScreen) {
                            BottomBarScreen(navController = navController, currentTask)
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(onFullScreen = {
                            isFullScreen = it
                        })
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
            InitExitDialog()

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
    fun AppNavigation(onFullScreen: (Boolean) -> Unit) {
        NavHost(navController, startDestination = HomeRouter.HomeGraph.router) {
            addHomeGraph(navController)
            addUserGraph(navController, tasksManagerViewModel)
            addMenuGraph(navController)
            addDocumentGraph(navController, tasksManagerViewModel)
            addNamabarNavGraph(navController, tasksManagerViewModel, onFullScreen)
        }
    }

    @Composable
    private fun HandleUserHasDocState(userHasDoc: Boolean?, onValueChange: (Boolean?) -> Unit) {
        onValueChange(userHasDoc)
    }

    override fun onBackPressed() {
        if (navController.currentBackStackEntry?.destination?.route == UiUserSharedIds.UserHomeScreen.router) {
            exit()
        } else {
            when (navController.currentBackStackEntry?.destination?.route) {
                MenuRouter.MainMenuScreen.router,
                DocumentRouter.DocumentSubmitScreen.router,
                DocumentRouter.DocumentFileListScreen.router,
                BottomNavigationItems.PersonalInfo.route,
                -> {
                    exitDialog.show()
                }
                else -> super.onBackPressed()
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        return if (navController.graph.route != MenuRouter.MainMenuScreen.router &&
            navController.currentBackStackEntry?.destination?.route == UiUserSharedIds.UserChangePassword.router
        ) {
            exit()
            false
        } else {
            navController.navigateUp() || super.onNavigateUp()
        }
    }

    private fun exit() {
        if (mBackPressed + timeInterVal > System.currentTimeMillis()) {
            finish()
            return
        } else {
            Toast.makeText(
                applicationContext,
                getString(R.string.press_once_again_back_button_to_exit),
                Toast.LENGTH_SHORT
            ).show()
        }
        mBackPressed = System.currentTimeMillis()
    }

    @Composable
    private fun ProcessLoadingAndErrorState(input: PublicState?) {
        val loadingDialog = getLoadingDialog()
        val errorDialog = getErrorDialog(
            title = stringResource(id = ir.part.app.merat.ui.user.R.string.ara_label_warning_title_dialog),
            description = "",
            submitAction = {}
        )

        if (input?.refreshing == true) {
            loadingDialog.show()
        } else {
            loadingDialog.dismiss()
            input?.message?.let { messageModel ->
                errorDialog.setDialogDetailMessage(messageModel.message).show()
            }
        }
    }

    @Composable
    private fun InitExitDialog() {
        exitDialog = getExitAppDialog(
            title = stringResource(id = R.string.ara_btn_logout), description = stringResource(
                id = R.string.msg_sign_out
            ), submitAction = {
                homeViewModel.logout {
                    if (it) {
                        lifecycleScope.launch {
                            navController.navigateToUserHomeScreen()
                        }
                    }

                }
            }, cancelAction = {
                exitDialog.dismiss()
            }
        )
    }

    override fun onResume() {
        super.onResume()
        if ((this as? Activity)?.intent?.action == Intent.ACTION_VIEW && (this as? Activity)?.intent?.data?.getQueryParameters(
                "status"
            ) != null
        ) {
            val status = (this as? Activity)?.intent?.data?.getQueryParameters("status")

            if (status?.first() == "1") {
                navController.navigateToFileListScreen()
            } else {
                navController.navigateToFileListScreen()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
