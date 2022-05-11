package ir.part.sdk.ara.builder.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ir.part.sdk.ara.builder.R

@Composable
fun NextActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true
) {
    OutlinedButton(
        modifier = modifier.padding(all = dimensionResource(id = R.dimen.spacing_base)),
        onClick = { onClick() },
        enabled = isEnabled,
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.spacing_half_base),
            color = if (isEnabled) MaterialTheme.colors.primaryVariant
            else MaterialTheme.colors.disabled()
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            disabledBackgroundColor = MaterialTheme.colors.disabled()
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (iv, tv) = createRefs()
            Text(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(id = R.dimen.spacing_base),
                        horizontal = dimensionResource(id = R.dimen.spacing_2x)
                    )
                    .constrainAs(tv) {
                        top.linkTo(iv.top)
                        bottom.linkTo(iv.bottom)
                        end.linkTo(iv.start)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    },
                style = MaterialTheme.typography.subtitle2.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary
                ),
                text = text,
            )
            Image(
                modifier = Modifier
                    .rotate(180f)
                    .constrainAs(iv) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(id = R.drawable.ic_arrow_right_small),
                contentDescription = "previous",
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colors.onPrimary
                )
            )
        }
    }
}


@Composable
fun PreviousActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true
) {
    OutlinedButton(
        modifier = modifier.padding(all = dimensionResource(id = R.dimen.spacing_base)),
        onClick = { onClick() },
        enabled = isEnabled,
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.spacing_half_base),
            color =
            if (isEnabled) MaterialTheme.colors.primaryVariant
            else MaterialTheme.colors.disabled()
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (iv, tv) = createRefs()
            Image(
                modifier = Modifier
                    .wrapContentHeight()
                    .constrainAs(iv) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                painter = painterResource(id = R.drawable.ic_arrow_right_small),
                contentDescription = "previous",
                colorFilter = ColorFilter.tint(
                    color =
                    if (isEnabled) MaterialTheme.colors.primaryVariant
                    else MaterialTheme.colors.disabled()
                )
            )
            Text(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(id = R.dimen.spacing_base),
                        horizontal = dimensionResource(id = R.dimen.spacing_2x)
                    )
                    .constrainAs(tv) {
                        top.linkTo(iv.top)
                        bottom.linkTo(iv.bottom)
                        start.linkTo(iv.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                style = MaterialTheme.typography.subtitle2.copy(
                    textAlign = TextAlign.Center,
                    color =
                    if (isEnabled) MaterialTheme.colors.primaryVariant
                    else MaterialTheme.colors.disabled()
                ),
                text = text,
            )
        }
    }
}

//@Composable
//fun BackIcon(
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    resId: Int? = null,
//    contentDescription: String? = null,
//    iconStyle: IconStyle? = null
//) {
//    IconButton(
//        onClick = onClick,
//        modifier = modifier,
//    ) {
//        Icon(
//            painter = painterResource(id = resId ?: R.drawable.common_view_ic_arrow_right),
//            contentDescription = contentDescription,
//            tint = iconStyle?.tint ?: MaterialTheme.colors.primaryVariant
//        )
//    }
//}