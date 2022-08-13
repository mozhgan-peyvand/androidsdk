package ir.part.sdk.ara.ui.menu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import ir.part.sdk.ara.common.ui.view.commonCompose.MenuTopAppBarContent
import ir.part.sdk.ara.common.ui.view.commonCompose.TextBody2Secondary
import ir.part.sdk.ara.common.ui.view.commonCompose.TextHeadline6PrimaryBold
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker2
import ir.part.sdk.merat.ui.menu.BuildConfig
import ir.part.sdk.merat.ui.menu.R


@Composable
fun AboutUsScreen(onNavigateUp: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.surface,
                elevation = dimensionResource(id = R.dimen.spacing_half_base)) {
                MenuTopAppBarContent(title = stringResource(id = R.string.label_about_us),
                    onNavigateUp = {
                        onNavigateUp()
                    })
            }
        }, bottomBar = {
            VersionBottomBar()
        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            AboutUsContent()
        }
    }
}

@Composable
fun AboutUsContent() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .padding(horizontal = dimensionResource(id = R.dimen.spacing_4x))) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_6x)))
        TextHeadline6PrimaryBold(text = stringResource(id = R.string.label_introduce_merat_validation_system_body))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_5x)))
        TextBody2Secondary(text = stringResource(id = R.string.label_about_us_header))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_5x)))
        TextBody2Secondary(text = stringResource(id = R.string.label_about_us_body_introduce))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_6x)))
        TextHeadline6PrimaryBold(text = stringResource(id = R.string.label_center_merat_validation))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_5x)))
        TextBody2Secondary(text = stringResource(id = R.string.label_merat_validation_center_body))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_9x)))

        HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.label_about_us_two))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.label_about_us_three))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.label_about_us_four))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.label_about_us_five))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.label_about_us_six))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.label_about_us_seven))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.label_about_us_eight))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.label_about_us_nine))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

    }

}

@Composable
private fun HighlightedBulletWithTextBody2Secondary(text: String) {
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


@Composable
private fun VersionBottomBar() {
    Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.surface,
        elevation = dimensionResource(id = R.dimen.spacing_2x)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.spacing_2x)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.label_about_us_app_version,
                BuildConfig.VERSION_NAME),
                style = MaterialTheme.typography.subtitle1,
                color = ColorBlueDarker)
        }
    }
}
