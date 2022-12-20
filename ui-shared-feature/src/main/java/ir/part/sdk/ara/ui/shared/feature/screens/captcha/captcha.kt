package ir.part.sdk.ara.ui.shared.feature.screens.captcha

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import ir.part.app.merat.ui.shared.feature.R
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.common.ProcessLoadingAndErrorState
import ir.part.sdk.ara.common.ui.view.primaryVariant
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.ErrorText
import ir.part.sdk.ara.common.ui.view.theme.subtitle1TextSecondary
import ir.part.sdk.ara.common.ui.view.utils.clearFocusOnKeyboardDismiss
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget

@Composable
fun Captcha(
    captchaViewModel: CaptchaViewModel,
    interactionSource: MutableInteractionSource? = null,
) {

    val captchaLoadingErrorState =
        rememberFlowWithLifecycle(flow = captchaViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    ProcessLoadingAndErrorState(captchaLoadingErrorState.value)

    Row {
        Column(modifier = Modifier.weight(2f)) {
            ShowCaptcha(captchaViewModel, interactionSource)
        }
        Button(
            modifier = Modifier.padding(top = dimensionResource(id = DimensionResource.spacing_3x)),
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
                    .padding(
                        top = dimensionResource(id = DimensionResource.spacing_4x),
                        end = dimensionResource(id = DimensionResource.spacing_2x)
                    )
                    .height(40.dp),
                bitmap = it,
                contentDescription = "",
            )
        }
    }
}


@Composable
fun ShowCaptcha(
    captchaViewModel: CaptchaViewModel?,
    interactionSource: MutableInteractionSource? = null,
) {
    val focusManager = LocalFocusManager.current


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
            )
            .clearFocusOnKeyboardDismiss(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray
        ),
        textStyle = TextStyle(color = Color.Black, textDirection = TextDirection.Content),
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {
            focusManager.clearFocus()
        },
        isError = if (captchaViewModel?.errorCaptchaValue?.value?.first == ValidationField.CAPTCHA)
            captchaViewModel.errorCaptchaValue.value.second.isNotEmpty() else false,
        interactionSource = interactionSource ?: MutableInteractionSource(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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
