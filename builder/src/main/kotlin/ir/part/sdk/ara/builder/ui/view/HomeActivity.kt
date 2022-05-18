package ir.part.sdk.ara.builder.ui.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.MainScope
import ir.part.sdk.ara.builder.R
import ir.part.sdk.ara.builder.di.BuilderComponent
import ir.part.sdk.ara.builder.di.BuilderModule
import ir.part.sdk.ara.builder.util.localizedContext
import ir.part.sdk.ara.common.ui.view.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.dialog.getTryAgainDialog
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.ui.theme.AraTheme
import ir.part.sdk.ara.common.ui.view.viewModel.PublicState
import ir.part.sdk.ara.domain.document.entities.PersonalDocuments
import javax.inject.Inject


@MainScope
class HomeActivity : ComponentProviderActivity() {

    companion object {
        var appId = ""
    }

    @BuilderModule
    @Inject
    lateinit var homeActivityFactory: ViewModelProvider.Factory

    private val viewModel: HomeActivityViewModel by (this as ComponentActivity).viewModels { homeActivityFactory }

    private var darkTheme = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideComponent().inject(this)

        setContent {
            LocalContext.current.applicationContext
            initView()

            darkTheme = rememberSaveable {
                mutableStateOf(false)
            }

            AraTheme(darkTheme = darkTheme.value) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            color = MaterialTheme.colors.background
                        ),
                ) { innerPadding ->

                    Box(modifier = Modifier.padding(innerPadding)) {
                        InitState(viewModel)
                        Column {
                            Button(
                                onClick = { viewModel.getRequestPersonalDocumentRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getPersonalDocument")
                            }
                            Button(
                                onClick = { viewModel.getRequestRejectRequestByUserRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getRejectRequestByUserRemote")
                            }
                            Button(
                                onClick = { viewModel.getRequestPersonalInfoConstantsRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getPersonalInfoConstantsRemote")
                            }
                            Button(
                                onClick = { viewModel.getPersonalInfoClub() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getPersonalInfoClub")
                            }
                            Button(
                                onClick = { viewModel.getTaskRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getTaskRemote")
                            }
                            Button(
                                onClick = { viewModel.doneTaskRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "doneTaskRemote")
                            }
                            Button(
                                onClick = { viewModel.doingTaskRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "doingTaskRemote")
                            }
                            Button(
                                onClick = { viewModel.setDisableCustomerFlagRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "setDisableCustomerFlagRemote")
                            }

                            Button(
                                onClick = { viewModel.getRequestBaseStateRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getBaseStateRemote")
                            }
                            Button(
                                onClick = { viewModel.getRequestCurrentUser() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getCurrentUser1")
                            }
                            Button(
                                onClick = { viewModel.removeCurrentUser() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "removeCurrentUser")
                            }
                            Button(
                                onClick = { viewModel.getRequestLogOutRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getLogOutRemote1")
                            }
                            Button(
                                onClick = { viewModel.getRequestCaptchaRemote() },
                                modifier = Modifier.padding(top = 20.dp)
                            ) {
                                Text(text = "getCaptchaRemote1")
                            }
                        }

                    }

                }
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

    private fun initView() {
        viewModel.clearAllMessage()
    }

    @Composable
    private fun InitValue(
//        viewState: AraState,
        viewState:
        List<PersonalDocuments>,
        viewLoadingAndMessageState: PublicState,
        refresh: () -> Unit
    ) {
        Log.d("PersonalDocuments", viewState.toString())
        if (viewLoadingAndMessageState.refreshing) {
            getLoadingDialog().show()
        } else {
            viewLoadingAndMessageState.message?.let { messageModel ->
                LaunchedEffect(messageModel) {
                    refresh.invoke()
                    Toast.makeText(
                        baseContext,
                        messageModel.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
//        viewState.PersonalDocumentList?.let { formValue ->
//            Log.d(
//                "PersonalDocumentList",
//                formValue.joinToString(" ") { it.processInstanceId.toString() })
//
//        }
//        viewState.getPersonalInfoConstantsRemote?.let { formValue ->
//            Log.d("gender", formValue.gender.joinToString(" ") { it.name })
//        }
//
//        viewState.getPersonalInfoClubRemote?.let { formValue ->
//            Log.d("union", formValue.clubName.toString())
//        }
//        viewState.setDisableCustomerFlagRemote?.let { value ->
//            Log.d("hasReadeMessage", value.toString())
//        }
//        viewState.getBaseStateRemote?.let { value ->
//            Log.d("hasReadeMessage", value.toString())
//        }


    }

    @Composable
    fun InitState(viewModel: HomeActivityViewModel) {
//        val viewState by rememberFlowWithLifecycle(viewModel.state)
//            .collectAsState(initial = AraState.Empty)
        val viewState by remember {
            viewModel.personalDocumentState
        }
        val viewLoadingAndMessageState by rememberFlowWithLifecycle(viewModel.loadingAndMessageState)
            .collectAsState(initial = PublicState.Empty)

        val tryAgainDialog =
            getTryAgainDialog(describeMessage = stringResource(R.string.msg_dialog_try_again_message)) {
                initView()
            }
        InitValue(viewState, viewLoadingAndMessageState) {
            tryAgainDialog.show()
        }
    }

    private fun provideComponent() = BuilderComponent.builder(this)
}
