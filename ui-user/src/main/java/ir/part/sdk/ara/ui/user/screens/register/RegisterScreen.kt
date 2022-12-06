package ir.part.sdk.ara.ui.user.screens.register

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
import ir.part.sdk.ara.common.ui.view.utils.clearFocusOnKeyboardDismiss
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.Captcha
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel
import ir.part.sdk.ara.ui.user.util.common.VisibilityStatusBar


@Composable
fun RegisterScreen(
    onNavigateUp: () -> Unit,
    registerViewModel: RegisterViewModel,
    captchaViewModel: CaptchaViewModel,
    navigateToLogin: () -> Unit,
) {

    var isRegister: Boolean by remember {
        mutableStateOf(false)
    }
    var nationalCode: String? by remember {
        mutableStateOf(null)
    }
    var email: String? by remember {
        mutableStateOf(null)
    }
    var phone: String? by remember {
        mutableStateOf(null)
    }

    val loadingErrorState =
        rememberFlowWithLifecycle(registerViewModel.loadingAndMessageState).collectAsState(initial = PublicState.Empty)

    isRegister = registerViewModel.registerDone.value
    nationalCode = registerViewModel.userName.value
    email = registerViewModel.email.value
    phone = registerViewModel.phone.value

    if (isRegister) {
        navigateToLogin.invoke()
        registerViewModel.registerDone.value = false
    }

    ProcessLoadingAndErrorState(loadingErrorState.value) {
        registerViewModel.loadingAndMessageState.value.message = null
    }

    Register(
        onNavigateUp = onNavigateUp,
        nationalCode = nationalCode ?: "",
        email = email ?: "",
        phone = phone ?: "",
        registerViewModel = registerViewModel,
        captchaViewModel = captchaViewModel
    )

}

@Composable
fun Register(
    nationalCode: String,
    email: String,
    phone: String,
    captchaViewModel: CaptchaViewModel,
    registerViewModel: RegisterViewModel,
    onNavigateUp: () -> Unit,
) {

    val nationalCodeInteractionSource = remember { MutableInteractionSource() }
    val nationalCodeFocusState = nationalCodeInteractionSource.collectIsFocusedAsState()
    val emailInteractionSource = remember { MutableInteractionSource() }
    val emailFocusState = emailInteractionSource.collectIsFocusedAsState()
    val phoneInteractionSource = remember { MutableInteractionSource() }
    val phoneFocusState = phoneInteractionSource.collectIsFocusedAsState()
    val captchaInteractionSource = remember { MutableInteractionSource() }
    val captchaFocusState = captchaInteractionSource.collectIsFocusedAsState()

    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {

        AnimatedVisibility(visible = nationalCodeFocusState.value.not() && emailFocusState.value.not() && phoneFocusState.value.not() && captchaFocusState.value.not()) {
            VisibilityStatusBar(nationalCodeFocusState.value.not() && emailFocusState.value.not() && phoneFocusState.value.not() && captchaFocusState.value.not())
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
                registerViewModel.setNationalCode(
                    nationalCodeText
                )
            },
            errorNationalCode = registerViewModel.errorNationalCode,
            interactionSource = nationalCodeInteractionSource
        )

        ShowEmail(
            email = email,
            onChangeEmail = { emailText -> registerViewModel.setEmail(emailText) },
            errorEmailCode = registerViewModel.errorEmail,
            interactionSource = emailInteractionSource
        )
        ShowPhone(
            phone = phone,
            onChangePhone = { phoneNumber -> registerViewModel.setPhone(phoneNumber) },
            errorPhone = registerViewModel.errorPhone,
            interactionSource = phoneInteractionSource
        )


        //Captcha
        Captcha(
            captchaViewModel = captchaViewModel,
            interactionSource = captchaInteractionSource
        )


        UserButton(textButton = stringResource(id = R.string.label_register), onClickButton = {
            captchaViewModel.setError(
                validateWidget(
                    ValidationField.CAPTCHA,
                    captchaViewModel.captchaValue.value
                )
            )
            if (
                registerViewModel.isValidationFields()
            ) {
                registerViewModel.setRegister(
                    captchaToken = captchaViewModel.captchaViewState.value?.token ?: "",
                    captchaValue = captchaViewModel.captchaValue.value
                )

            }
        })

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
private fun ShowEmail(
    email: String,
    onChangeEmail: (String) -> Unit,
    errorEmailCode: MutableState<Pair<ValidationField, List<ValidationResult>>>,
    interactionSource: MutableInteractionSource,
) {

    UserTextField(
        modifier = Modifier.clearFocusOnKeyboardDismiss(),
        title = null,
        hint = stringResource(id = R.string.label_email),
        value = email,
        onValueChanged = { emailText ->
            onChangeEmail(emailText)
        },
        errorMessage = if (errorEmailCode.value.second.isNotEmpty())
            errorEmailCode.value.second.last().validator.getErrorMessage(LocalContext.current)
        else "",
        keyboardType = KeyboardType.Email,
        painter = painterResource(R.drawable.ara_ic_mail),
        interactionSource = interactionSource
    )
}

@Composable
private fun ShowPhone(
    phone: String,
    onChangePhone: (String) -> Unit,
    errorPhone: MutableState<Pair<ValidationField, List<ValidationResult>>>,
    interactionSource: MutableInteractionSource,
) {
    UserTextField(
        modifier = Modifier.clearFocusOnKeyboardDismiss(),
        title = null,
        hint = stringResource(id = R.string.label_phone),
        value = phone,
        onValueChanged = { phoneNumber ->
            onChangePhone(phoneNumber)
        },
        errorMessage = if (errorPhone.value.second.isNotEmpty())
            errorPhone.value.second.last().validator.getErrorMessage(LocalContext.current)
        else "",
        maxChar = 11,
        keyboardType = KeyboardType.Phone,
        painter = painterResource(R.drawable.ara_ic_phone_button),
        interactionSource = interactionSource
    )
}


@Composable
private fun ProcessLoadingAndErrorState(input: PublicState?, onErrorDialogDismissed: () -> Unit) {
    val loadingDialog = getLoadingDialog()
    val errorDialog = getErrorDialog(
        title = stringResource(id = R.string.ara_label_warning_title_dialog),
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
