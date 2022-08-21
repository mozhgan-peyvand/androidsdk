package ir.part.sdk.ara.ui.menu.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import ir.part.sdk.ara.common.ui.view.disabled
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker2
import ir.part.sdk.ara.common.ui.view.theme.body2TextSecondary
import ir.part.sdk.ara.common.ui.view.theme.h6Bold
import ir.part.sdk.merat.ui.menu.R

@Composable
fun MenuTopAppBarContent(title: String, onNavigateUp: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x))
                .clickable {
                    onNavigateUp()
                },
            painter = painterResource(id = R.drawable.ic_back),
            tint = MaterialTheme.colors.disabled(),
            contentDescription = "back"
        )
        Text(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_2x)),
            text = title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )

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
        Column(Modifier.padding(top = dimensionResource(id = R.dimen.spacing_2x))) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.spacing_2x))
                    .clip(CircleShape)
                    .background(ColorBlueDarker2)
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_4x)))
        TextBody2Secondary(text)
    }
}
