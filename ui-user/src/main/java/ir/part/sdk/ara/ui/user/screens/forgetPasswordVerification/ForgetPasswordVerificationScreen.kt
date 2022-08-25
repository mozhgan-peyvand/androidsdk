package ir.part.sdk.ara.ui.user.screens.forgetPasswordVerification

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.common.ui.view.api.PublicState
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
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime

@Composable
fun ForgetPasswordVerificationScreen(
    navigateToLogin: (() -> Unit)? = null,
    navigateUp: (() -> Unit)? = null,
    forgetPasswordVerificationViewModel: ForgetPasswordVerificationViewModel? = null,
    nationalCode: String? = null
) {

    if (forgetPasswordVerificationViewModel?.isSendCode?.value == true) {
        navigateToLogin?.invoke()
    }


    Column {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.merat_login_background),
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
        forgetPasswordVerificationViewModel?.let {
            ObserveLoadingState(forgetPasswordVerificationViewModel = it)
            ProcessLoadingAndErrorState(forgetPasswordVerificationViewModel = it)
            ShowSendCode(forgetPasswordVerificationViewModel = it)
        }
        StartTimer(navigateUp = navigateUp)

        Button(
            onClick = {
                if (validateWidget(
                        ValidationField.ACTIVITY_CODE,
                        forgetPasswordVerificationViewModel?.codeValue?.value ?: ""
                    ).second.isNullOrEmpty()
                ) {
                    forgetPasswordVerificationViewModel?.sendCode(
                        nationalCode = nationalCode ?: "",
                        forgetPasswordVerificationViewModel.codeValue.value
                    )
                } else {
                    forgetPasswordVerificationViewModel?.setErrorPassword(
                        validateWidget(
                            ValidationField.ACTIVITY_CODE,
                            forgetPasswordVerificationViewModel.codeValue.value
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
                text = stringResource(id = R.string.label_send_code),
                style = MaterialTheme.typography.buttonTextStyle()
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
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
private fun ShowSendCode(forgetPasswordVerificationViewModel: ForgetPasswordVerificationViewModel) {
    TextField(
        value = forgetPasswordVerificationViewModel.codeValue.value,
        onValueChange = { inputValue ->
            forgetPasswordVerificationViewModel.codeValue.value = inputValue
            forgetPasswordVerificationViewModel.setErrorPassword(
                validateWidget(
                    ValidationField.ACTIVITY_CODE,
                    inputValue
                )
            )
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_activation_code),
                textAlign = TextAlign.Center,
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
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = !forgetPasswordVerificationViewModel.errorValuePassword.value.second.isNullOrEmpty()
    )

    ErrorText(
        visible = !forgetPasswordVerificationViewModel.errorValuePassword.value.second.isNullOrEmpty(),
        errorMessage = if (!forgetPasswordVerificationViewModel.errorValuePassword.value.second.isNullOrEmpty()) {
            forgetPasswordVerificationViewModel.errorValuePassword.value.second.last().validator.getErrorMessage(
                LocalContext.current
            )
        } else ""
    )
}

@Composable
private fun ObserveLoadingState(
    forgetPasswordVerificationViewModel: ForgetPasswordVerificationViewModel
) {
    forgetPasswordVerificationViewModel.loadingErrorState.value =
        rememberFlowWithLifecycle(forgetPasswordVerificationViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        ).value
}

@Composable
private fun ProcessLoadingAndErrorState(forgetPasswordVerificationViewModel: ForgetPasswordVerificationViewModel) {
    val dialog = getInfoDialog(
        title = stringResource(id = R.string.label_warning_title_dialog),
        description = ""
    )
    val loadingDialog = getLoadingDialog()

    if (forgetPasswordVerificationViewModel.loadingErrorState.value?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        forgetPasswordVerificationViewModel.loadingErrorState.value?.message?.let { messageModel ->
            dialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
private fun ForgetPasswordVerificationPre() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ForgetPasswordVerificationScreen()
    }
}