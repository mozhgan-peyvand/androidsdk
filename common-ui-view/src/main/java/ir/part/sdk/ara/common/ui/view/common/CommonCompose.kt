package ir.part.sdk.ara.common.ui.view.common

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import ir.part.sdk.ara.common.ui.view.R
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.disabled
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getConnectionErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog

@Composable
fun TopAppBarContent(title: String, onNavigateUp: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {


        IconButton(onClick = {
            onNavigateUp()
        }) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = DimensionResource.spacing_2x)),
                painter = painterResource(id = R.drawable.ara_ic_back),
                tint = MaterialTheme.colors.disabled(),
                contentDescription = "back"
            )
        }
        Text(
            modifier = Modifier.padding(dimensionResource(id = DimensionResource.spacing_2x)),
            text = title,
            style = MaterialTheme.typography.h5BoldTextPrimary(),
        )
    }
}

@Composable
fun SubmitActionContent(onSubmitClicked: () -> Unit, buttonText: Int) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .padding(
                start = dimensionResource(id = R.dimen.spacing_4x),
                end = dimensionResource(id = R.dimen.spacing_4x),
                top = dimensionResource(id = R.dimen.spacing_2x),
                bottom = dimensionResource(id = R.dimen.spacing_6x),
            )
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.spacing_base)),
            onClick = onSubmitClicked,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_2x)),

            colors = ButtonDefaults.buttonColors(backgroundColor = ColorBlueDarker)
        ) {
            Text(
                text = stringResource(buttonText),
                style = MaterialTheme.typography.buttonTextStyle(),
                modifier = Modifier
                    .background(Color.Transparent)
            )
        }
    }
}

@Composable
fun TextBody2Secondary(text: String) {
    Text(text = text, style = MaterialTheme.typography.body2TextSecondary())
}


@Composable
fun TextHeadline6PrimaryBold(text: String) {
    Text(text = text, style = MaterialTheme.typography.h6Bold())
}

@Composable
fun HighlightedBulletWithTextBody2Secondary(text: String) {
    Row {
        Column(Modifier.padding(top = dimensionResource(id = DimensionResource.spacing_2x))) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = DimensionResource.spacing_2x))
                    .clip(CircleShape)
                    .background(ColorBlueDarker2)
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = DimensionResource.spacing_3x)))
        TextBody2Secondary(text)
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    contentPadding: PaddingValues = PaddingValues(
        bottom = dimensionResource(R.dimen.spacing_base),
        top = dimensionResource(R.dimen.spacing_base)
    ),
) {
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    (BasicTextField(
        value = value,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .indicatorLine(enabled, isError, interactionSource, colors),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                contentPadding = contentPadding,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                isError = isError,
                colors = colors,
                label = label
            )
        }
    ))
}

@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    contentPadding: PaddingValues = PaddingValues(bottom = dimensionResource(R.dimen.spacing_base))
) {
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    (BasicTextField(
        value = value,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .indicatorLine(enabled, isError, interactionSource, colors),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value.text,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                contentPadding = contentPadding,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                isError = isError,
                colors = colors,
                label = label
            )
        }
    ))
}

@Composable
fun ProcessLoadingAndErrorState(
    vararg loadingAndErrorStates: PublicState?,
    removeErrorsFromStates: (() -> Unit)? = null
) {
    val activity = (LocalContext.current as? Activity)
    val loadingDialog = getLoadingDialog()
    val errorDialog = getErrorDialog(
        title = stringResource(id = R.string.ara_msg_general_error_title),
        description = ""
    ) {}
    val connectionDialog = getConnectionErrorDialog().setCancelAction {
        activity?.finish()
    }

    // loading
    if (
        loadingAndErrorStates.any() {
            it?.refreshing == true
        }
    ) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
    }

    // error
    val errors = loadingAndErrorStates.filter {
        it?.message?.code?.isNotBlank() == true
    }

    if (errors.isNotEmpty()) {
        loadingDialog.dismiss()

        when (errors.firstOrNull()?.message?.code) {

            "NoInternetConnection" -> {
                connectionDialog.setSubmitAction {
                    if (errors.any { it?.message?.onRetry != null }) {
                        errors.forEach {
                            if (it?.message?.onRetry != null) {
                                it.message?.onRetry?.invoke()
                            }
                        }
                    } else {
                        removeErrorsFromStates?.invoke()
                    }

                }
                connectionDialog.show()
            }

            else -> {
                errors.firstOrNull()?.message?.let { messageModel ->
                    errorDialog.setSubmitAction {
                        if (errors.any { it?.message?.onRetry != null }) {
                            errors.forEach {
                                if (it?.message?.onRetry != null) {
                                    it.message?.onRetry?.invoke()
                                }
                            }
                        } else {
                            removeErrorsFromStates?.invoke()
                        }
                    }
                    errorDialog.setDialogDetailMessage(messageModel.message).show()
                }
            }
        }
    }
}

