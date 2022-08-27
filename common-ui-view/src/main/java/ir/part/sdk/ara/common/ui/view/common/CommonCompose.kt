package ir.part.sdk.ara.common.ui.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ir.part.sdk.ara.common.ui.view.R
import ir.part.sdk.ara.common.ui.view.disabled
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource

@Composable
fun TopAppBarContent(title: String, onNavigateUp: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = DimensionResource.spacing_2x))
                .clickable {
                    onNavigateUp()
                },
            painter = painterResource(id = R.drawable.ic_back),
            tint = MaterialTheme.colors.disabled(),
            contentDescription = "back"
        )
        Text(
            modifier = Modifier.padding(dimensionResource(id = DimensionResource.spacing_2x)),
            text = title,
            style = MaterialTheme.typography.h5BoldPrimary(),
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
        Spacer(modifier = Modifier.width(dimensionResource(id = DimensionResource.spacing_2x)))
        TextBody2Secondary(text)
    }
}
