package ir.part.sdk.ara.common.ui.view.commonCompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import ir.part.sdk.ara.common.ui.view.R
import ir.part.sdk.ara.common.ui.view.theme.body2TextSecondary
import ir.part.sdk.ara.common.ui.view.theme.disabled
import ir.part.sdk.ara.common.ui.view.theme.h6Bold

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
    Text(text = text , style = MaterialTheme.typography.h6Bold())
}
