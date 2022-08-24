package ir.part.sdk.ara.ui.user.screens.forgetPassword

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
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.ButtonBlue
import ir.part.sdk.ara.common.ui.view.theme.ErrorText
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.Captcha
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel


@Composable
fun ForgetPasswordScreen(
    captchaViewModel: CaptchaViewModel? = null,
    navigateToVerificationForgetPassword: ((String) -> Unit)? = null,
    forgetPasswordViewModel: ForgetPasswordViewModel? = null
) {


    if (forgetPasswordViewModel?.isRecoveredPassword?.value == true) {
        navigateToVerificationForgetPassword?.invoke(forgetPasswordViewModel.nationalCode.value)
        forgetPasswordViewModel.isRecoveredPassword.value = false
    }

    Column {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.merat_login_background),
            contentDescription = ""
        )
        forgetPasswordViewModel?.let {
            ObserveLoadingState(forgetPasswordViewModel = it)
            ProcessLoadingAndErrorState(forgetPasswordViewModel = it)
            ShowUserName(forgetPasswordViewModel = it)
        }

        Captcha(
            captchaViewModel = captchaViewModel,
        )

        ButtonBlue(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                captchaViewModel?.setError(
                    validateWidget(
                        ValidationField.CAPTCHA,
                        captchaViewModel.captchaValue.value
                    )
                )
                if (
                    validateWidget(
                        ValidationField.NATIONAL_CODE,
                        forgetPasswordViewModel?.nationalCode?.value ?: ""
                    ).second.isEmpty() &&
                    captchaViewModel?.errorValue?.value?.second?.isEmpty() != false
                ) {
                    forgetPasswordViewModel?.setRecoverPassword(forgetPasswordViewModel.nationalCode.value)

                } else {
                    forgetPasswordViewModel?.setErrorNationalCode(
                        validateWidget(
                            ValidationField.NATIONAL_CODE,
                            forgetPasswordViewModel.nationalCode.value
                        )
                    )
                }
            },
            text = stringResource(id = R.string.label_recover_code)
        )
    }
}


@Composable
fun ShowUserName(forgetPasswordViewModel: ForgetPasswordViewModel) {

    TextField(
        value = forgetPasswordViewModel.nationalCode.value,
        onValueChange = { inputValue ->
            forgetPasswordViewModel.nationalCode.value = inputValue
            forgetPasswordViewModel.setErrorNationalCode(
                validateWidget(
                    ValidationField.NATIONAL_CODE,
                    inputValue
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
        isError = !forgetPasswordViewModel.errorValueNationalCode.value.second.isNullOrEmpty()
    )
    ErrorText(
        visible = !forgetPasswordViewModel.errorValueNationalCode.value.second.isNullOrEmpty(),
        errorMessage = if (!forgetPasswordViewModel.errorValueNationalCode.value.second.isNullOrEmpty())
            forgetPasswordViewModel.errorValueNationalCode.value.second.last().validator.getErrorMessage(
                LocalContext.current
            ) else ""
    )
}

@Composable
fun ObserveLoadingState(
    forgetPasswordViewModel: ForgetPasswordViewModel
) {
    forgetPasswordViewModel.loadingErrorState.value =
        rememberFlowWithLifecycle(forgetPasswordViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        ).value
}

@Composable
private fun ProcessLoadingAndErrorState(forgetPasswordViewModel: ForgetPasswordViewModel) {
    val dialog = getInfoDialog(
        title = stringResource(id = R.string.label_warning_title_dialog),
        description = ""
    )
    val loadingDialog = getLoadingDialog()

    if (forgetPasswordViewModel.loadingErrorState.value?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        forgetPasswordViewModel.loadingErrorState.value?.message?.let { messageModel ->
            dialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun ChangePasswordPre() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ForgetPasswordScreen()
    }
}