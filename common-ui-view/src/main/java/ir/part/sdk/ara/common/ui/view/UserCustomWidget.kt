package ir.part.sdk.ara.common.ui.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker
import ir.part.sdk.ara.common.ui.view.theme.ErrorText
import ir.part.sdk.ara.common.ui.view.theme.buttonTextStyle
import ir.part.sdk.ara.common.ui.view.theme.subtitle1TextSecondary

@Composable
fun UserTextField(
    title: String? = null,
    hint: String,
    value: String,
    modifier: Modifier? = null,
    onValueChanged: (String) -> Unit,
    errorMessage: String,
    isLastField: Boolean = false,
    maxChar: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    painter: Painter? = null,
    trailingPasswordIcon: Boolean = false
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    if (title != null) {
        Text(
            text = title
        )
    }
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (maxChar != null) {
                if (newValue.length <= maxChar) {
                    onValueChanged(newValue)
                }
            } else {
                onValueChanged(newValue)
            }
        },
        modifier = (modifier ?: Modifier)
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.spacing_2x),
                end = dimensionResource(id = R.dimen.spacing_2x)
            ),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = hint,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        leadingIcon = {
            if (painter != null) {
                Icon(
                    painter = painter,
                    contentDescription = "",
                    tint = MaterialTheme.colors.primaryVariant()
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        keyboardOptions = KeyboardOptions(
            imeAction = if (isLastField) ImeAction.Done else ImeAction.Next,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        isError = errorMessage.isNotEmpty(),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (trailingPasswordIcon) {
            if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation()
        } else VisualTransformation.None,
        trailingIcon = {
            if (trailingPasswordIcon) {
                val image = if (passwordVisible)
                    Icons.Filled.VisibilityOff
                else Icons.Filled.Visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, "")
                }
            }
        }
    )

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_base)))

    ErrorText(
        visible =
        errorMessage.isNotEmpty(),
        errorMessage = errorMessage
    )
}


@Composable
fun UserButton(onClickButton: () -> Unit, textButton: String) {

    Button(
        onClick = { onClickButton.invoke() },
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
            text = textButton,
            style = MaterialTheme.typography.buttonTextStyle()
        )
    }
}