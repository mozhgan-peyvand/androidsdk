package ir.part.sdk.ara.ui.shared.feature.screens.captcha

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import ir.part.app.merat.ui.shared.feature.R
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.primaryVariant
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.ErrorText
import ir.part.sdk.ara.common.ui.view.theme.subtitle1TextSecondary
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget

@Composable
fun Captcha(
    captchaViewModel: CaptchaViewModel,
) {

    val captchaLoadingErrorState =
        rememberFlowWithLifecycle(flow = captchaViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    ProcessLoadingAndErrorState(captchaLoadingErrorState.value) {
        captchaViewModel.loadingAndMessageState.value.message = null
    }

    Row {
        Column(modifier = Modifier.weight(2f)) {
            ShowCaptcha(captchaViewModel)
        }
        Button(
            onClick = {
                captchaViewModel.refreshCaptcha()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            elevation = null
        ) {
            Image(
                painter = painterResource(id = R.drawable.merat_ic_reload),
                contentDescription = ""
            )
        }
        captchaViewModel.captchaViewState.value?.img?.asImageBitmap()?.let {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = DimensionResource.spacing_2x))
                    .height(32.dp),
                bitmap = it,
                contentDescription = "",
            )
        }
    }
}


@Composable
fun ShowCaptcha(captchaViewModel: CaptchaViewModel?) {

    TextField(
        value = captchaViewModel?.captchaValue?.value ?: "",
        onValueChange = { inputValue ->
            captchaViewModel?.captchaValue?.value = inputValue
            captchaViewModel?.setError(validateWidget(ValidationField.CAPTCHA, inputValue))
        },
        placeholder = {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.label_security_code),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.merat_ic_access_key),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant()
            )
        },
        modifier = Modifier
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
        textStyle = TextStyle(color = Color.Black, textDirection = TextDirection.Content),
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        isError = if (captchaViewModel?.errorCaptchaValue?.value?.first == ValidationField.CAPTCHA)
            captchaViewModel.errorCaptchaValue.value.second.isNotEmpty() else false

    )
    ErrorText(
        visible = !captchaViewModel?.errorCaptchaValue?.value?.second.isNullOrEmpty(),
        errorMessage = if (!captchaViewModel?.errorCaptchaValue?.value?.second.isNullOrEmpty())
            captchaViewModel?.errorCaptchaValue?.value?.second?.last()?.validator?.getErrorMessage(
                LocalContext.current
            )
        else ""
    )
}

@Composable
private fun ProcessLoadingAndErrorState(input: PublicState?, onErrorDialogDismissed: () -> Unit) {
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