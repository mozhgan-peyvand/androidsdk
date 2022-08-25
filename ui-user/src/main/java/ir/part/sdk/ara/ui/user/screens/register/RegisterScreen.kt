package ir.part.sdk.ara.ui.user.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.part.app.merat.ui.user.R
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

        Button(
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
                text = stringResource(id = R.string.label_register),
                style = MaterialTheme.typography.buttonTextStyle()
            )
        }
    }
}

@Composable
private fun ShowNationalCode(registerViewModel: RegisterViewModel) {
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
private fun ShowEmail(registerViewModel: RegisterViewModel) {
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
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.merat_ic_mail),
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
private fun ShowPhone(registerViewModel: RegisterViewModel) {
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
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.merat_ic_phone_button),
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
private fun ObserveLoadingState(
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