package ir.part.sdk.ara.ui.user.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.primaryVariant
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker
import ir.part.sdk.ara.common.ui.view.theme.ErrorText
import ir.part.sdk.ara.common.ui.view.theme.buttonTextStyle
import ir.part.sdk.ara.common.ui.view.theme.subtitle1TextSecondary
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.Captcha
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel

@Composable
fun LoginScreen(
    captchaViewModel: CaptchaViewModel? = null,
    navigateToForgetPassword: (() -> Unit)? = null,
    navigateToDocument: (() -> Unit)? = null,
    loginViewModel: LoginViewModel? = null
) {

    // todo it will fix in get task
    when (loginViewModel?.nextStep?.value) {
        TasksName.StartNewDocument.value -> navigateToDocument?.invoke()
        else -> {}
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id =R.drawable.merat_login_background),
            contentDescription = ""
        )

        loginViewModel?.let {
            ObserveLoadingState(loginViewModel = it)
            ProcessLoadingAndErrorState(loginViewModel = it)
            ShowUserName(loginViewModel = it)
            ShowPassword(loginViewModel = it)
        }

        Captcha(
            captchaViewModel = captchaViewModel
        )

        Button(
            onClick = {
                captchaViewModel?.setError(
                    validateWidget(
                        ValidationField.CAPTCHA,
                        captchaViewModel.captchaValue.value
                    )
                )
                if (
                    loginViewModel?.isValidationFields() == true &&
                    captchaViewModel?.errorValue?.value?.second?.isEmpty() != false
                ) {
                    loginViewModel.getLogin(
                        captchaValue = captchaViewModel?.captchaValue?.value ?: "",
                        captchaToken = captchaViewModel?.captchaViewState?.value?.token ?: ""
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorBlueDarker,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.spacing_4x),
                    end = dimensionResource(id = R.dimen.spacing_4x),
                    bottom = dimensionResource(id = R.dimen.spacing_base)
                )
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.label_login),
                style = MaterialTheme.typography.buttonTextStyle()
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
private fun ShowUserName(loginViewModel: LoginViewModel) {
    val maxChar = 10
    TextField(
        value = loginViewModel.userName.value,
        onValueChange = { inputValue ->
            if (inputValue.length <= maxChar)
                loginViewModel.userName.value = inputValue
            loginViewModel.setErrorNationalCode(
                validateWidget(
                    ValidationField.NATIONAL_CODE,
                    loginViewModel.userName.value
                )
            )
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_national_code),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.merat_ic_single),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(
                bottom = dimensionResource(id = DimensionResource.spacing_2x),
                start = dimensionResource(id = DimensionResource.spacing_2x),
                end = dimensionResource(id = DimensionResource.spacing_2x)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = loginViewModel.errorValueNationalCode.value.second.isNotEmpty()
    )
    ErrorText(
        visible = !loginViewModel.errorValueNationalCode.value.second.isNullOrEmpty(),
        errorMessage = if (!loginViewModel.errorValueNationalCode.value.second.isNullOrEmpty()) loginViewModel.errorValueNationalCode.value.second.last().validator.getErrorMessage(
            LocalContext.current
        ) else ""
    )
}

@Composable
private fun ShowPassword(loginViewModel: LoginViewModel) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = loginViewModel.password.value,
        onValueChange = { inputValue ->
            loginViewModel.password.value = inputValue
            loginViewModel.setErrorPassword(
                validateWidget(
                    ValidationField.LOGIN_PASSWORD,
                    inputValue
                )
            )

        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_password),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.VisibilityOff
            else Icons.Filled.Visibility

            // Please provide localized description for accessibility services
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, "")
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_lock),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(
                bottom = dimensionResource(id = DimensionResource.spacing_2x),
                start = dimensionResource(id = DimensionResource.spacing_2x),
                end = dimensionResource(id = DimensionResource.spacing_2x)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = loginViewModel.errorValuePassword.value.second.isNotEmpty()
    )
    ErrorText(
        visible = !loginViewModel.errorValuePassword.value.second.isNullOrEmpty(),
        errorMessage = if (!loginViewModel.errorValuePassword.value.second.isNullOrEmpty()) loginViewModel.errorValuePassword.value.second.last().validator.getErrorMessage(
            LocalContext.current
        ) else ""
    )
}

@Composable
private fun ObserveLoadingState(
    loginViewModel: LoginViewModel
) {
    loginViewModel.loadingErrorState.value =
        rememberFlowWithLifecycle(loginViewModel.loadingAndMessageState).collectAsState(initial = PublicState.Empty).value
}

@Composable
private fun ProcessLoadingAndErrorState(loginViewModel: LoginViewModel) {
    val dialog = getInfoDialog(
        title = stringResource(id = R.string.label_warning_title_dialog),
        description = ""
    )
    val loadingDialog = getLoadingDialog()

    if (loginViewModel.loadingErrorState.value?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        loginViewModel.loadingErrorState.value?.message?.let { messageModel ->
            dialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}


@Preview(widthDp = 320, heightDp = 640)
@Composable
private fun LoginPreviews() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LoginScreen()
    }
}



