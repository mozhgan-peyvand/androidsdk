package ir.part.sdk.ara.common.ui.view.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.disabled
import ir.part.sdk.ara.common.ui.view.statusBar
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource


@Composable
fun ButtonBlue(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = DimensionResource.spacing_3x)),
        onClick = { onClick() },
        enabled = isEnabled,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = dimensionResource(id = DimensionResource.spacing_half_base),
            color = if (isEnabled) MaterialTheme.colors.primaryVariant
            else MaterialTheme.colors.disabled()
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            disabledBackgroundColor = MaterialTheme.colors.disabled()
        )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = dimensionResource(id = DimensionResource.spacing_2x)),
            text = text,
            style = MaterialTheme.typography.subtitle2.copy(
                textAlign = TextAlign.Center,
                color =
                if (isEnabled) MaterialTheme.colors.statusBar()
                else MaterialTheme.colors.disabled()
            )
        )
    }
}

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    visible: Boolean,
    errorMessage: String? = null
) {
    if (visible)
        Text(
            text = errorMessage ?: "",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = modifier
                .padding(
                    start = dimensionResource(id = DimensionResource.spacing_3x),
                    end = dimensionResource(id = DimensionResource.spacing_2x)
                )
                .fillMaxWidth()
        )
}
