package ir.part.sdk.ara.ui.menu.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.theme.body2TextPrimaryBold
import ir.part.sdk.ara.common.ui.view.theme.body2TextSecondaryBold
import ir.part.sdk.ara.ui.menu.screens.common.MenuTopAppBarContent
import ir.part.sdk.ara.ui.menu.screens.common.TextBody2Secondary
import ir.part.sdk.ara.ui.menu.screens.common.TextHeadline6PrimaryBold
import ir.part.sdk.merat.ui.menu.R

@Composable
fun CallCenterScreen(onNavigateUp: () -> Unit) {
    Column() {
        TopAppBar(backgroundColor = Color.White,
            elevation = dimensionResource(id = R.dimen.spacing_half_base)) {
            MenuTopAppBarContent(title = stringResource(id = R.string.label_call_center),
                onNavigateUp = {
                    onNavigateUp()
                })
        }
        CallCenterContent()
    }
}

@Composable
private fun CallCenterContent() {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .verticalScroll(state = scrollState)
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_4x))
    ) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_6x)))
        TextHeadline6PrimaryBold(text = stringResource(id = R.string.label_contacting_ways))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_5x)))
        TextBody2Secondary(text = stringResource(id = R.string.label_call_center_description))

        CallInfoBoard()
    }

}

@Composable
private fun CallInfoBoard() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.spacing_4x)),
        elevation = dimensionResource(id = R.dimen.spacing_half_base),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.divider())
                    .padding(
                        dimensionResource(id = R.dimen.spacing_4x),
                    ),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.label_call_info),
                style = MaterialTheme.typography.body2TextSecondaryBold(),
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.spacing_4x),
                        horizontal = dimensionResource(id = R.dimen.spacing_2x)
                    ),
                text = stringResource(id = R.string.label_everyday_of_week_24h),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2TextPrimaryBold(),
            )

            Row(modifier = Modifier
                .background(color = MaterialTheme.colors.divider())
                .clickable {
                    openCallCenterDial(context = context)
                }
                .padding(
                    vertical = dimensionResource(id = R.dimen.spacing_4x),
                    horizontal = dimensionResource(id = R.dimen.spacing_2x)
                )
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {

                Text(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_2x)),
                    text = stringResource(id = R.string.label_phone_number_with_colon),
                    style = MaterialTheme.typography.body2TextPrimaryBold(),
                )

                Text(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_2x)),
                    text = stringResource(id = R.string.label_call_center_phone),
                    style = MaterialTheme.typography.body2TextPrimaryBold(),
                )
            }
        }
    }
}

private fun openCallCenterDial(context: Context) {
    val number = context.getString(R.string.call_center_number)
    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
    context.startActivity(callIntent)
}