package ir.part.sdk.ara.ui.user.screens.forgetPassword

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

        Button(
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
                    forgetPasswordViewModel?.setRecoverPassword(
                        forgetPasswordViewModel.nationalCode.value,
                        captchaViewModel?.captchaValue?.value,
                        captchaViewModel?.captchaViewState?.value?.token
                    )

                } else {
                    forgetPasswordViewModel?.setErrorNationalCode(
                        validateWidget(
                            ValidationField.NATIONAL_CODE,
                            forgetPasswordViewModel.nationalCode.value
                        )
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
                text = stringResource(id = R.string.label_recover_code),
                style = MaterialTheme.typography.buttonTextStyle()
            )
        }
    }
}


@Composable
private fun ShowUserName(forgetPasswordViewModel: ForgetPasswordViewModel) {

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
                style = MaterialTheme.typography.subtitle1TextSecondary()
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
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.merat_ic_single),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant()
            )
        },
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
private fun ObserveLoadingState(
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
