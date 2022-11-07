package ir.part.sdk.ara.ui.document.utils.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import ir.part.sdk.ara.common.ui.view.textPrimary
import ir.part.sdk.ara.common.ui.view.textSecondary
import ir.part.sdk.ara.common.ui.view.theme.body2BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.theme.body2TextPrimary
import ir.part.sdk.ara.common.ui.view.theme.subtitle1TextSecondary
import ir.part.sdk.ara.common.ui.view.theme.subtitle2TextSecondary
import ir.part.sdk.ara.ui.document.R


@Composable
fun DocumentGenericLayout(
    title: Int,
    value: String,
    icon: Int,
    modifier: Modifier,
    color: Color? = null
) {

    val constrain = ConstraintSet {
        val documentStatusIcon = createRefFor("documentStatusIcon")
        val documentStatusTitle = createRefFor("documentStatusTitle")
        val documentStatusDes = createRefFor("documentStatusDes")

        constrain(documentStatusIcon) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }

        constrain(documentStatusTitle) {
            start.linkTo(documentStatusIcon.end)
            top.linkTo(documentStatusIcon.top)
            bottom.linkTo(documentStatusIcon.bottom)
        }

        constrain(documentStatusDes) {
            top.linkTo(documentStatusIcon.bottom)
            start.linkTo(documentStatusIcon.end)
        }

    }

    ConstraintLayout(
        constraintSet = constrain,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                end = dimensionResource(id = R.dimen.spacing_4x),
                start = dimensionResource(id = R.dimen.spacing_4x)
            )
    ) {
        Image(
            painter = painterResource(id = icon), contentDescription = "image",
            modifier = modifier
                .layoutId("documentStatusIcon")
                .width(dimensionResource(id = R.dimen.spacing_4x))
                .height(dimensionResource(id = R.dimen.spacing_4x)),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.textSecondary())
        )
        Text(
            text = stringResource(id = title),
            modifier = modifier
                .layoutId("documentStatusTitle")
                .padding(start = dimensionResource(id = R.dimen.spacing_4x)),
            style = MaterialTheme.typography.subtitle2TextSecondary()
        )
        Text(
            text = value,
            modifier = modifier
                .layoutId("documentStatusDes")
                .padding(
                    start = dimensionResource(id = R.dimen.spacing_4x),
                    top = dimensionResource(id = R.dimen.spacing_2x)
                ),
            style = MaterialTheme.typography.body2BoldTextPrimary(),
            color = color ?: MaterialTheme.colors.textPrimary()
        )
    }
}

@Composable
fun AraFileNotFound() {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.spacing_4x))
            .layoutId("fileNotFound")
    ) {
        Image(
            painter = painterResource(id = R.drawable.ara_ic_empty_folder_search),
            contentDescription = "ic_empty_folder_search"
        )

        Text(
            text = stringResource(id = R.string.ara_msg_file_not_found),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.spacing_4x)),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1TextSecondary()
        )
    }
}

@Composable
fun AraFileDoesNotExist() {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.spacing_4x))
            .layoutId("fileNotExist")
    ) {

        Image(
            painter = painterResource(id = R.drawable.ara_ic_empty_folder),
            contentDescription = "ic_empty_folder_search"
        )

        Text(
            text = stringResource(id = R.string.ara_msg_file_not_exist),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.spacing_4x)),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1TextSecondary()
        )

        Text(
            text = stringResource(id = R.string.ara_msg_resend_validation_request),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.spacing_base)),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle2TextSecondary()
        )
    }
}

@Composable
fun AraRetryLayout(
    painterDrawable: Int = R.drawable.ara_ic_general_error,
    title: Int = R.string.ara_msg_general_error
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.spacing_3x),
                end = dimensionResource(id = R.dimen.spacing_3x),
                top = dimensionResource(id = R.dimen.spacing_2x),
                bottom = dimensionResource(id = R.dimen.spacing_3x)
            )
    ) {

        Image(
            painter = painterResource(id = painterDrawable),
            contentDescription = "warning_document",
            modifier = Modifier.weight(1f)
        )

        Text(
            text = stringResource(id = title), modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.body2TextPrimary()
        )
    }
}