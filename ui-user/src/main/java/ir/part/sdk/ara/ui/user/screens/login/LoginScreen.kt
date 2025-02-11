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
import ir.part.sdk.ara.common.ui.view.common.ProcessLoadingAndErrorState
import ir.part.sdk.ara.common.ui.view.utils.clearFocusOnKeyboardDismiss
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.Captcha
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel
import ir.part.sdk.ara.ui.user.util.common.VisibilityStatusBar

@Composable
fun LoginScreen(
    onNavigateUp: () -> Unit,
    captchaViewModel: CaptchaViewModel,
    navigateToForgetPassword: (() -> Unit),
    loginViewModel: LoginViewModel,
    tasksManagerViewModel: TasksManagerViewModel,
) {

    val loginLoadingErrorState =
        rememberFlowWithLifecycle(flow = loginViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    val taskLoadingErrorState =
        rememberFlowWithLifecycle(flow = tasksManagerViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    val errorValueNationalCode =
        rememberFlowWithLifecycle(flow = loginViewModel.errorValueNationalCode).collectAsState(
            initial = Pair(ValidationField.CAPTCHA, listOf())
        )

    val errorValuePassword =
        rememberFlowWithLifecycle(flow = loginViewModel.errorValuePassword).collectAsState(
            initial = Pair(ValidationField.CAPTCHA, listOf())
        )

    ProcessLoadingAndErrorState(
        loginLoadingErrorState.value,
        taskLoadingErrorState.value,
        removeErrorsFromStates = {
            loginViewModel.clearAllMessage()
            tasksManagerViewModel.clearAllMessage()
        }
    )

    Login(
        onNavigateUp = onNavigateUp,
        navigateToForgetPassword = navigateToForgetPassword,
        errorValueNationalCode = errorValueNationalCode.value,
        onNationalCodeChange = {
            loginViewModel.setNationalCode(it)
        },
        errorValuePassword = errorValuePassword.value,
        onPasswordChange = {
            loginViewModel.setPassword(
                it
            )
        },
        captchaViewModel = captchaViewModel,
        onLoginButtonClick = {
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
        }
    )
}

@Composable
fun Login(
    navigateToForgetPassword: (() -> Unit)?,
    onNavigateUp: () -> Unit,
    errorValueNationalCode: Pair<ValidationField, List<ValidationResult>>,
    onNationalCodeChange: (String) -> Unit,
    errorValuePassword: Pair<ValidationField, List<ValidationResult>>,
    onPasswordChange: (String) -> Unit,
    captchaViewModel: CaptchaViewModel,
    onLoginButtonClick: () -> Unit
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

        AnimatedVisibility(visible = nationalCodeFocusState.value.not() && passwordFocusState.value.not() && captchaFocusState.value.not()) {
            VisibilityStatusBar(nationalCodeFocusState.value.not() && passwordFocusState.value.not() && captchaFocusState.value.not())
            Column {
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
                            .padding(
                                top = dimensionResource(id = DimensionResource.spacing_8x),
                                start = dimensionResource(id = DimensionResource.spacing_3x),
                                end = dimensionResource(id = DimensionResource.spacing_3x),
                                bottom = dimensionResource(id = DimensionResource.spacing_3x)
                            ),
                        painter = painterResource(id = R.drawable.ara_ic_back),
                        tint = MaterialTheme.colors.onPrimary(),
                        contentDescription = "back"
                    )
                }
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.ara_login_background),
                    contentDescription = ""
                )
            }
        }

        ShowNationalCode(
            onchangeNationalCode = { nationalCodeText ->
                onNationalCodeChange(nationalCodeText)

            },
            errorNationalCode = errorValueNationalCode,
            interactionSource = nationalCodeInteractionSource
        )

        ShowPassword(
            onChangePassword = { passwordText ->
                onPasswordChange(passwordText)
            },
            errorPassword = errorValuePassword,
            interactionSource = passwordInteractionSource
        )

        Captcha(
            captchaViewModel = captchaViewModel,
            interactionSource = captchaInteractionSource
        )

        UserButton(
            onClickButton = {
                onLoginButtonClick()
            },
            textButton = stringResource(id = R.string.label_login)
        )


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
    onchangeNationalCode: (String) -> Unit,
    errorNationalCode: Pair<ValidationField, List<ValidationResult>>,
    interactionSource: MutableInteractionSource,
) {
    var nationalCode: String by remember {
        mutableStateOf("")
    }

    UserTextField(
        modifier = Modifier.clearFocusOnKeyboardDismiss(),
        title = null,
        hint = stringResource(id = R.string.label_national_code),
        value = nationalCode,
        onValueChanged = { nationalCodeText ->
            nationalCode = nationalCodeText
            onchangeNationalCode(nationalCodeText)
        },
        errorMessage = if (errorNationalCode.second.isNotEmpty())
            errorNationalCode.second.last().validator.getErrorMessage(LocalContext.current)
        else "",
        maxChar = 10,
        keyboardType = KeyboardType.Number,
        painter = painterResource(R.drawable.ara_ic_single),
        interactionSource = interactionSource
    )

}

@Composable
private fun ShowPassword(
    onChangePassword: (String) -> Unit,
    errorPassword: Pair<ValidationField, List<ValidationResult>>,
    interactionSource: MutableInteractionSource,
) {

    var password: String by remember {
        mutableStateOf("")
    }

    UserTextField(
        modifier = Modifier.clearFocusOnKeyboardDismiss(),
        hint = stringResource(id = R.string.label_password),
        value = password,
        onValueChanged = { passwordText ->
            password = passwordText
            onChangePassword.invoke(passwordText)
        },
        errorMessage = if (errorPassword.second.isNotEmpty())
            errorPassword.second.last().validator.getErrorMessage(LocalContext.current) else "",
        keyboardType = KeyboardType.Password,
        trailingPasswordIcon = true,
        painter = painterResource(R.drawable.ara_ic_lock),
        interactionSource = interactionSource
    )

}

