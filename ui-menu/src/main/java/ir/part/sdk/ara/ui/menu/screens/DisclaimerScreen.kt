package ir.part.sdk.ara.ui.menu.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import ir.part.sdk.ara.common.ui.view.common.TextBody2Secondary
import ir.part.sdk.ara.common.ui.view.common.TextHeadline6PrimaryBold
import ir.part.sdk.ara.common.ui.view.common.TopAppBarContent
import ir.part.sdk.merat.ui.menu.R

@Composable
fun DisclaimerScreen(onNavigateUp: () -> Unit) {
    Column() {
        TopAppBar(backgroundColor = Color.White,
            elevation = dimensionResource(id = R.dimen.spacing_half_base)) {
            TopAppBarContent(title = stringResource(id = R.string.label_disclaimer),
                onNavigateUp = {
                    onNavigateUp()
                })
        }
        DisclaimerContent()
    }
}

@Composable
private fun DisclaimerContent() {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .verticalScroll(state = scrollState)
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_4x))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_6x)))
        TextHeadline6PrimaryBold(text = stringResource(id = R.string.label_disclaimer))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_5x)))

        TextBody2Secondary(text = stringResource(id = R.string.text_disclaimer))
    }
}