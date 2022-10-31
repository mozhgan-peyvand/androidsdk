package ir.part.sdk.ara.ui.user.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.common.ui.view.*
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.utils.clearFocusOnKeyboardDismiss
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.Captcha
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel

@Composable
fun LoginScreen(
    onNavigateUp: () -> Unit,
    captchaViewModel: CaptchaViewModel,
    navigateToForgetPassword: (() -> Unit),
    loginViewModel: LoginViewModel,
    tasksManagerViewModel: TasksManagerViewModel,
) {

    var nationalCode: String? by remember {
        mutableStateOf(null)
    }
    var password: String? by remember {
        mutableStateOf(null)
    }

    val loginLoadingErrorState =
        rememberFlowWithLifecycle(flow = loginViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    nationalCode = loginViewModel.userName.value
    password = loginViewModel.password.value

    val taskLoadingErrorState =
        rememberFlowWithLifecycle(flow = tasksManagerViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    ProcessLoadingAndErrorState(
        input = loginLoadingErrorState.value
    ) {
        loginViewModel.loadingAndMessageState.value.message = null
    }

    ProcessLoadingAndErrorState(input = taskLoadingErrorState.value) {
        tasksManagerViewModel.loadingAndMessageState.value.message = null
    }

    Login(
        onNavigateUp = onNavigateUp,
        loginViewModel = loginViewModel,
        nationalCode = nationalCode,
        password = password,
        tasksManagerViewModel = tasksManagerViewModel,
        navigateToForgetPassword = navigateToForgetPassword,
        captchaViewModel = captchaViewModel
    )

}

@Composable
fun Login(
    loginViewModel: LoginViewModel,
    nationalCode: String?,
    tasksManagerViewModel: TasksManagerViewModel,
    navigateToForgetPassword: (() -> Unit)?,
    captchaViewModel: CaptchaViewModel,
    password: String?,
    onNavigateUp: () -> Unit,
) {
    val nationalCodeInteractionSource = remember { MutableInteractionSource() }
    val nationalCodeFocusState = nationalCodeInteractionSource.collectIsFocusedAsState()
    val passwordInteractionSource = remember { MutableInteractionSource() }
    val passwordFocusState = passwordInteractionSource.collectIsFocusedAsState()
    val captchaInteractionSource = remember { MutableInteractionSource() }
    val captchaFocusState = captchaInteractionSource.collectIsFocusedAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant())
        ) {

            Icon(
                modifier = Modifier
                    .clickable {
                        onNavigateUp()
                    }
                    .padding(dimensionResource(id = DimensionResource.spacing_3x)),
                painter = painterResource(id = R.drawable.ic_back),
                tint = MaterialTheme.colors.onPrimary(),
                contentDescription = "back"
            )
        }
        AnimatedVisibility(visible = nationalCodeFocusState.value.not() && passwordFocusState.value.not() && captchaFocusState.value.not()) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.ara_login_background),
                contentDescription = ""
            )
        }

        ShowNationalCode(
            nationalCode = nationalCode ?: "",
            onchangeNationalCode = { nationalCodeText ->
                loginViewModel.setNationalCode(
                    nationalCodeText
                )
            },
            errorNationalCode = loginViewModel.errorValueNationalCode,
            interactionSource = nationalCodeInteractionSource
        )
        ShowPassword(
            password = password ?: "",
            onChangePassword = { passwordText ->
                loginViewModel.setPassword(
                    passwordText
                )
            },
            errorPassword = loginViewModel.errorValuePassword,
            interactionSource = passwordInteractionSource
        )

        Captcha(
            captchaViewModel = captchaViewModel,
            interactionSource = captchaInteractionSource
        )

        UserButton(onClickButton = {
            captchaViewModel.setError(
                validateWidget(
                    ValidationField.CAPTCHA,
                    captchaViewModel.captchaValue.value
                )
            )
            if (
                loginViewModel.isValidationFields() && captchaViewModel.errorCaptchaValue.value.second.isEmpty()
            ) {
                loginViewModel.getLogin(
                    captchaValue = captchaViewModel.captchaValue.value,
                    captchaToken = captchaViewModel.captchaViewState.value?.token ?: "",
                    onSuccess = {
                        tasksManagerViewModel.getBaseState(it)
                    }
                )
            }
        }, textButton = stringResource(id = R.string.label_login))


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = DimensionResource.spacing_2x)),
            onClick = {
                navigateToForgetPassword?.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            elevation = null
        ) {
            Text(
                stringResource(id = R.string.label_forget_password),
                style = MaterialTheme.typography.subtitle2.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Black

                )
            )
        }
    }
}

@Composable
private fun ShowNationalCode(
    nationalCode: String,
    onchangeNationalCode: (String) -> Unit,
    errorNationalCode: MutableState<Pair<ValidationField, List<ValidationResult>>>,
    interactionSource: MutableInteractionSource,
) {
    UserTextField(
        modifier = Modifier.clearFocusOnKeyboardDismiss(),
        title = null,
        hint = stringResource(id = R.string.label_national_code),
        value = nationalCode,
        onValueChanged = { nationalCodeText ->
            onchangeNationalCode(nationalCodeText)
        },
        errorMessage = if (errorNationalCode.value.second.isNotEmpty())
            errorNationalCode.value.second.last().validator.getErrorMessage(LocalContext.current)
        else "",
        maxChar = 10,
        keyboardType = KeyboardType.Number,
        painter = painterResource(R.drawable.ara_ic_single),
        interactionSource = interactionSource
    )

}

@Composable
private fun ShowPassword(
    password: String,
    onChangePassword: (String) -> Unit,
    errorPassword: MutableState<Pair<ValidationField, List<ValidationResult>>>,
    interactionSource: MutableInteractionSource,
) {
    UserTextField(
        modifier = Modifier.clearFocusOnKeyboardDismiss(),
        hint = stringResource(id = R.string.label_password),
        value = password,
        onValueChanged = { passwordText -> onChangePassword.invoke(passwordText) },
        errorMessage = if (errorPassword.value.second.isNotEmpty())
            errorPassword.value.second.last().validator.getErrorMessage(LocalContext.current) else "",
        keyboardType = KeyboardType.Password,
        trailingPasswordIcon = true,
        painter = painterResource(R.drawable.ic_lock),
        interactionSource = interactionSource
    )

}

@Composable
private fun ProcessLoadingAndErrorState(input: PublicState?, onErrorDialogDismissed: () -> Unit) {
    val loadingDialog = getLoadingDialog()
    val errorDialog = getErrorDialog(
        title = stringResource(id = R.string.msg_general_error_title),
        description = "",
        submitAction = {
            onErrorDialogDismissed()
        }
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

