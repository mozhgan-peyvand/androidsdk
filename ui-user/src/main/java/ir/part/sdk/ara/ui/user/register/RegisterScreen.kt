package ir.part.sdk.ara.ui.user.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.ButtonBlue
import ir.part.sdk.ara.common.ui.view.theme.ErrorText
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.ui.user.captcha.Captcha
import ir.part.sdk.ara.ui.user.captcha.CaptchaViewModel
import ir.part.sdk.ara.ui.user.util.validation.ValidationField
import ir.part.sdk.ara.ui.user.util.validation.validateWidget


@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel? = null,
    captchaViewModel: CaptchaViewModel? = null,
    navigateToLogin: (() -> Unit)? = null
) {
    if (registerViewModel?.registerDone?.value == true) {
        navigateToLogin?.invoke()
    }

    Column {

        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.merat_login_background),
            contentDescription = ""
        )
        registerViewModel?.let {
            ObserveLoadingState(registerViewModel = it)
            ProcessLoadingAndErrorState(registerViewModel = it)
            ShowNationalCode(registerViewModel = it)
            ShowEmail(registerViewModel = it)
            ShowPhone(registerViewModel = it)
        }

        //Captcha
        Captcha(
            captchaViewModel = captchaViewModel
        )

        ButtonBlue(
            modifier = Modifier,
            onClick = {
                captchaViewModel?.setError(
                    validateWidget(
                        ValidationField.CAPTCHA,
                        captchaViewModel.captchaValue.value
                    )
                )
                if (
                    registerViewModel?.isValidationFields() == true &&
                    captchaViewModel?.errorValue?.value?.second?.isEmpty() != false
                ) {
                    registerViewModel.setRegister(
                        captchaToken = captchaViewModel?.captchaViewState?.value?.token ?: "",
                        captchaValue = captchaViewModel?.captchaValue?.value ?: ""
                    )
                }

            },
            text = stringResource(id = R.string.label_register)
        )
    }
}

@Composable
fun ShowNationalCode(registerViewModel: RegisterViewModel) {
    val maxChar = 10
    TextField(
        value = registerViewModel.userName.value,
        onValueChange = { inputValue ->
            if (inputValue.length <= maxChar)
                registerViewModel.userName.value = inputValue
            registerViewModel.setErrorNationalCode(
                validateWidget(
                    ValidationField.NATIONAL_CODE,
                    registerViewModel.userName.value
                )
            )
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_national_code),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1
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
            backgroundColor = Color.White
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = registerViewModel.errorNationalCode.value.second.isNotEmpty()
    )
    ErrorText(
        visible = !registerViewModel.errorNationalCode.value.second.isNullOrEmpty(),
        errorMessage = if (!registerViewModel.errorNationalCode.value.second.isNullOrEmpty())
            registerViewModel.errorNationalCode.value.second.last().validator.getErrorMessage(
                LocalContext.current
            )
        else ""
    )
}

@Composable
fun ShowEmail(registerViewModel: RegisterViewModel) {
    TextField(
        value = registerViewModel.email.value,
        onValueChange = { inputValue ->
            registerViewModel.email.value = inputValue
            registerViewModel.setErrorEmail(validateWidget(ValidationField.EMAIL, inputValue))
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_email),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1
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
            backgroundColor = Color.White
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = registerViewModel.errorEmail.value.second.isNotEmpty()

    )
    ErrorText(
        visible =
        !registerViewModel.errorEmail.value.second.isNullOrEmpty(),
        errorMessage = if (!registerViewModel.errorEmail.value.second.isNullOrEmpty())
            registerViewModel.errorEmail.value.second.last().validator.getErrorMessage(LocalContext.current)
        else ""
    )
}

@Composable
fun ShowPhone(registerViewModel: RegisterViewModel) {
    val maxChar = 11
    TextField(
        value = registerViewModel.phone.value,
        onValueChange = { inputValue ->
            if (inputValue.length <= maxChar) registerViewModel.phone.value = inputValue
            registerViewModel.setErrorPhone(
                validateWidget(
                    ValidationField.PHONE,
                    registerViewModel.phone.value
                )
            )
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_phone),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1
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
            backgroundColor = Color.White
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = registerViewModel.errorPhone.value.second.isNotEmpty()

    )
    ErrorText(
        visible =
        !registerViewModel.errorPhone.value.second.isNullOrEmpty(),
        errorMessage = if (!registerViewModel.errorPhone.value.second.isNullOrEmpty())
            registerViewModel.errorPhone.value.second.last().validator.getErrorMessage(LocalContext.current)
        else ""
    )
}

@Composable
fun ObserveLoadingState(
    registerViewModel: RegisterViewModel
) {
    registerViewModel.loadingErrorState.value =
        rememberFlowWithLifecycle(registerViewModel.loadingAndMessageState).collectAsState(initial = PublicState.Empty).value
}

@Composable
private fun ProcessLoadingAndErrorState(
    registerViewModel: RegisterViewModel
) {
    val dialog = getInfoDialog(
        title = stringResource(id = R.string.label_warning_title_dialog),
        description = ""
    )
    val loadingDialog = getLoadingDialog()

    if (registerViewModel.loadingErrorState.value?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        registerViewModel.loadingErrorState.value?.message?.let { messageModel ->
            dialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
private fun RegisterPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        RegisterScreen()
    }
}
