package ir.part.sdk.ara.ui.user.screens.forgetPasswordVerification

import androidx.compose.foundation.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.common.ui.view.*
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun ForgetPasswordVerificationScreen(
    navigateToLogin: (() -> Unit),
    navigateToForgetPassword: (() -> Unit),
    forgetPasswordVerificationViewModel: ForgetPasswordVerificationViewModel,
    nationalCode: String? = null,
    onNavigateUp: () -> Unit
) {

    var codeForgetPassword: String? by remember {
        mutableStateOf(null)
    }

    val loadingErrorState =
        rememberFlowWithLifecycle(forgetPasswordVerificationViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    codeForgetPassword = forgetPasswordVerificationViewModel.codeValue.value

    if (forgetPasswordVerificationViewModel.isSendCode.value) {
        navigateToLogin.invoke()
        forgetPasswordVerificationViewModel.isSendCode.value = false
    }

    ProcessLoadingAndErrorState(loadingErrorState.value) {
        forgetPasswordVerificationViewModel.loadingAndMessageState.value.message = null
    }

    ForgetPasswordVerification(
        onNavigateUp = onNavigateUp,
        codeForgetPassword = codeForgetPassword ?: "",
        forgetPasswordVerificationViewModel = forgetPasswordVerificationViewModel,
        navigateUp = navigateToForgetPassword,
        nationalCode = nationalCode
    )

}

@Composable
fun ForgetPasswordVerification(
    onNavigateUp: () -> Unit,
    codeForgetPassword: String,
    forgetPasswordVerificationViewModel: ForgetPasswordVerificationViewModel,
    navigateUp: (() -> Unit)?,
    nationalCode: String?
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
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
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.ara_login_background),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(dimensionResource(id = DimensionResource.spacing_4x)),
            text = stringResource(id = R.string.label_recover_code),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(dimensionResource(id = DimensionResource.spacing_4x)),
            text = stringResource(id = R.string.msg_recover_code_description)
        )
        ShowSendCode(
            codeForgetPassword = codeForgetPassword,
            onChangeCodeForgetPassword = { codeForgetPassword ->
                forgetPasswordVerificationViewModel.setCode(codeForgetPassword)
            },
            errorCodeForgetPassword = forgetPasswordVerificationViewModel.errorValuePassword
        )

        StartTimer(navigateUp = navigateUp)

        UserButton(onClickButton = {
            if (validateWidget(
                    ValidationField.ACTIVITY_CODE,
                    forgetPasswordVerificationViewModel.codeValue.value
                ).second.isEmpty()
            ) {
                forgetPasswordVerificationViewModel.sendCode(
                    nationalCode = nationalCode ?: "",
                    forgetPasswordVerificationViewModel.codeValue.value
                )
            } else {
                forgetPasswordVerificationViewModel.setErrorPassword(
                    validateWidget(
                        ValidationField.ACTIVITY_CODE,
                        forgetPasswordVerificationViewModel.codeValue.value
                    )
                )
            }
        }, textButton = stringResource(id = R.string.label_send_code))
    }
}

@Composable
private fun StartTimer(
    navigateUp: (() -> Unit)?
) {
    var time by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        for (second in 120 downTo 0) {
            val minute = TimeUnit.SECONDS.toMinutes(second.toLong())
            time = String.format(
                "%02d:%02d",
                minute,
                second - TimeUnit.MINUTES.toSeconds(minute)
            )
            delay(1000)
        }
    }
    Box {
        if (time != "00:00") {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = DimensionResource.spacing_4x)),
                text = time,
                style = MaterialTheme.typography.subtitle2.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary
                )
            )
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { navigateUp?.invoke() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                elevation = null
            ) {
                Text(
                    text = stringResource(id = R.string.msg_resend_code),
                    style = MaterialTheme.typography.subtitle2.copy(
                        textAlign = TextAlign.Center,
                        color = Color.Black

                    )
                )
            }
        }
    }
}

@Composable
private fun ShowSendCode(
    codeForgetPassword: String,
    onChangeCodeForgetPassword: (String) -> Unit,
    errorCodeForgetPassword: MutableState<Pair<ValidationField, List<ValidationResult>>>
) {
    UserTextField(
        title = null,
        hint = stringResource(id = R.string.label_activation_code),
        value = codeForgetPassword,
        onValueChanged = { phoneNumber ->
            onChangeCodeForgetPassword(phoneNumber)
        },
        errorMessage = if (errorCodeForgetPassword.value.second.isNotEmpty())
            errorCodeForgetPassword.value.second.last().validator.getErrorMessage(LocalContext.current)
        else "",
        maxChar = 8,
        keyboardType = KeyboardType.Number
    )
}

@Composable
private fun ProcessLoadingAndErrorState(
    input: PublicState?,
    onErrorDialogDismissed: () -> Unit
) {
    val loadingDialog = getLoadingDialog()
    val errorDialog = getErrorDialog(
        title = stringResource(id = R.string.label_warning_title_dialog),
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
