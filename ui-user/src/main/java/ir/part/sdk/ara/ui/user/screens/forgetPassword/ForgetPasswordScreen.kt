package ir.part.sdk.ara.ui.user.screens.forgetPassword

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
import ir.part.sdk.ara.ui.user.util.common.VisibilityStatusBar


@Composable
fun ForgetPasswordScreen(
    onNavigateUp: () -> Unit,
    captchaViewModel: CaptchaViewModel,
    navigateToVerificationForgetPassword: ((String) -> Unit),
    forgetPasswordViewModel: ForgetPasswordViewModel,
) {

    var nationalCode: String? by remember {
        mutableStateOf(null)
    }

    val loadingErrorState =
        rememberFlowWithLifecycle(forgetPasswordViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    nationalCode = forgetPasswordViewModel.userName.value

    if (forgetPasswordViewModel.isRecoveredPassword.value) {
        navigateToVerificationForgetPassword.invoke(forgetPasswordViewModel.userName.value)
        forgetPasswordViewModel.isRecoveredPassword.value = false
    }

    ProcessLoadingAndErrorState(loadingErrorState.value, removeErrorsFromStates = {
        forgetPasswordViewModel.clearAllMessage()
    })

    ForgetPassword(
        onNavigateUp = onNavigateUp,
        nationalCode = nationalCode ?: "",
        forgetPasswordViewModel = forgetPasswordViewModel,
        captchaViewModel = captchaViewModel
    )
}

@Composable
fun ForgetPassword(
    onNavigateUp: () -> Unit,
    nationalCode: String,
    forgetPasswordViewModel: ForgetPasswordViewModel,
    captchaViewModel: CaptchaViewModel?,
) {
    val nationalCodeInteractionSource = remember { MutableInteractionSource() }
    val nationalCodeFocusState = nationalCodeInteractionSource.collectIsFocusedAsState()
    val captchaInteractionSource = remember { MutableInteractionSource() }
    val captchaFocusState = captchaInteractionSource.collectIsFocusedAsState()

    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {

        AnimatedVisibility(visible = nationalCodeFocusState.value.not() && captchaFocusState.value.not()) {
            VisibilityStatusBar(nationalCodeFocusState.value.not() && captchaFocusState.value.not())
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
            nationalCode = nationalCode,
            onchangeNationalCode = { nationalCodeText ->
                forgetPasswordViewModel.setNationalCode(
                    nationalCodeText
                )
            },
            errorNationalCode = forgetPasswordViewModel.errorValueNationalCode,
            nationalCodeInteractionSource
        )

        if (captchaViewModel != null) {
            Captcha(
                captchaViewModel = captchaViewModel,
                interactionSource = captchaInteractionSource
            )
        }

        UserButton(onClickButton = {
            captchaViewModel?.setError(
                validateWidget(
                    ValidationField.CAPTCHA,
                    captchaViewModel.captchaValue.value
                )
            )
            if (
                validateWidget(
                    ValidationField.NATIONAL_CODE,
                    forgetPasswordViewModel.userName.value
                ).second.isEmpty() && captchaViewModel?.errorCaptchaValue?.value?.second?.isEmpty() == true
            ) {
                forgetPasswordViewModel.setRecoverPassword(
                    forgetPasswordViewModel.userName.value,
                    captchaViewModel.captchaValue.value,
                    captchaViewModel.captchaViewState.value?.token
                )

            } else {
                forgetPasswordViewModel.setErrorNationalCode(
                    validateWidget(
                        ValidationField.NATIONAL_CODE,
                        forgetPasswordViewModel.userName.value
                    )
                )
            }
        }, textButton = stringResource(id = R.string.label_recover_code))
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


