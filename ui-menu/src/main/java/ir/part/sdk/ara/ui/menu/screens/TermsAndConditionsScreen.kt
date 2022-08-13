package ir.part.sdk.ara.ui.menu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.commonCompose.MenuTopAppBarContent
import ir.part.sdk.ara.common.ui.view.commonCompose.TextBody2Secondary
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.merat.ui.menu.R

@Composable
fun TermsAndConditionScreen(onNavigateUp: () -> Unit) {
    Column() {
        TopAppBar(backgroundColor = Color.White,
            elevation = dimensionResource(id = R.dimen.spacing_half_base)) {
            MenuTopAppBarContent(title = stringResource(id = R.string.label_terms_and_conditions),
                onNavigateUp = {
                    onNavigateUp()
                })
        }
        TermsAndConditionContent()
    }
}

@Composable
private fun TermsAndConditionContent() {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .verticalScroll(state = scrollState)
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_4x),
                vertical = dimensionResource(
                    id = R.dimen.spacing_6x))
    ) {
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
                        .padding(vertical = dimensionResource(id = R.dimen.spacing_4x),
                            horizontal = dimensionResource(id = R.dimen.spacing_2x)),
                    textAlign = TextAlign.Start,
                    text = stringResource(id = R.string.label_dear_user),
                    style = MaterialTheme.typography.body2TextSecondaryBold(),
                )
                Text(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_2x)),
                    text = stringResource(id = R.string.label_terms_and_conditions_board),
                    style = MaterialTheme.typography.subtitle1TextPrimary(),
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_5x)))
        TextBody2Secondary(text = stringResource(id = R.string.label_terms_and_conditions_body))

        InfoTextHighlight(title = stringResource(id = R.string.label_attention),
            body = stringResource(id = R.string.label_term_attention_one))

        InfoTextHighlight(title = stringResource(id = R.string.label_attention),
            body = stringResource(id = R.string.label_term_attention_two))
    }
}


@Composable
private fun InfoTextHighlight(title: String, body: String? = null) {
    Column() {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_9x)))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(dimensionResource(id = R.dimen.spacing_4x)),
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "Info",
                tint = ColorBlueDarker2
            )
            Text(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_2x)),
                text = title,
                style = MaterialTheme.typography.body2Highlight()
            )

        }

        body?.let {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
            Text(text = body, style = MaterialTheme.typography.body2Highlight())
        }
    }
}