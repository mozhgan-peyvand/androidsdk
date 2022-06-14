package ir.part.sdk.ara.ui.user.captcha

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.ErrorText
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.ui.user.util.validation.ValidationField
import ir.part.sdk.ara.ui.user.util.validation.validateWidget

@Composable
fun Captcha(
    captchaViewModel: CaptchaViewModel? = null,
) {

    captchaViewModel?.let {
        ProcessLoadingAndMessage(viewModel = captchaViewModel)
        LoadingObserver(viewModel = captchaViewModel)
    }

    Row {
        Column(modifier = Modifier.weight(1f)) {
            ShowCaptcha(captchaViewModel)
        }
        Button(
            onClick = {
                captchaViewModel?.refreshCaptcha()
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
        captchaViewModel?.captchaViewState?.value?.img?.asImageBitmap()?.let {
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
                style = MaterialTheme.typography.subtitle1
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
            backgroundColor = Color.White
        ),
        textStyle = TextStyle(color = Color.Black, textDirection = TextDirection.Content),
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        isError = if (captchaViewModel?.errorValue?.value?.first == ValidationField.CAPTCHA)
            captchaViewModel.errorValue.value.second.isNotEmpty() else false

    )
    ErrorText(
        visible = !captchaViewModel?.errorValue?.value?.second.isNullOrEmpty(),
        errorMessage = if (!captchaViewModel?.errorValue?.value?.second.isNullOrEmpty())
            captchaViewModel?.errorValue?.value?.second?.last()?.validator?.getErrorMessage(
                LocalContext.current
            )
        else ""
    )
}

@Composable
fun LoadingObserver(viewModel: CaptchaViewModel) {
    viewModel.loadingErrorState.value =
        rememberFlowWithLifecycle(flow = viewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        ).value
}

@Composable
fun ProcessLoadingAndMessage(viewModel: CaptchaViewModel) {
    val dialog = getInfoDialog(
        title = stringResource(id = R.string.msg_general_error_title),
        description = ""
    )
    val loadingDialog = getLoadingDialog()

    if (viewModel.loadingErrorState.value?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        viewModel.loadingErrorState.value?.message?.let { messageModel ->
            dialog.setDialogDetailMessage(messageModel.message).show()
        }
    }

}

@Preview(widthDp = 320, heightDp = 640)
@Composable
private fun CaptchaPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Captcha()
    }
}
