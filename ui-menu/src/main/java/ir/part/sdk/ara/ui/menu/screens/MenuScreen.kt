package ir.part.sdk.ara.ui.menu.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.theme.ColorBlue
import ir.part.sdk.ara.common.ui.view.theme.ColorBlueDarker
import ir.part.sdk.ara.common.ui.view.theme.ColorWhite
import ir.part.sdk.ara.common.ui.view.theme.subtitle1TextSecondary
import ir.part.sdk.merat.ui.menu.R

@Composable
fun MenuScreen(
    onChangePasswordClick: () -> Unit,
    onTermsAndConditionClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onDisclaimerClick: () -> Unit,
    onCallCenterClick: () -> Unit,
    onGuideClick: () -> Unit,
    onSubmitCommentClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)) {

        MainMenuHeader()

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        // list items
        MenuItem(R.drawable.ic_lock,
            R.string.label_change_password,
            onClick = onChangePasswordClick)

        MenuItem(icon = R.drawable.ic_question,
            title = R.string.label_guide,
            onClick = onGuideClick)

        MenuItem(icon = R.drawable.ic_info,
            title = R.string.label_about_us,
            onClick = onAboutUsClick)

        MenuItem(icon = R.drawable.ic_paper,
            title = R.string.label_terms_and_conditions,
            onClick = onTermsAndConditionClick)

        MenuItem(icon = R.drawable.ic_award,
            title = R.string.label_disclaimer,
            onClick = onDisclaimerClick)

        MenuItem(icon = R.drawable.ic_location, title = R.string.label_rahyar_address, onClick = {})

        MenuItem(icon = R.drawable.ic_comment,
            title = R.string.label_submit_comment,
            onClick = onSubmitCommentClick)

        MenuItem(icon = R.drawable.ic_headphones_mic,
            title = R.string.label_call_center,
            onClick = onCallCenterClick)

        MenuItem(icon = R.drawable.ic_exit, title = R.string.label_exit, onClick = {})

    }

}

@Composable
private fun MainMenuHeader() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(
            Brush.verticalGradient(
                listOf(
                    ColorBlueDarker,
                    ColorBlue
                )
            )
        )
        .padding(
            top = dimensionResource(id = R.dimen.spacing_8x),
            bottom = dimensionResource(id = R.dimen.spacing_8x),
            start = dimensionResource(id = R.dimen.spacing_4x),
            end = dimensionResource(id = R.dimen.spacing_7x)
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(text = "",
                style = MaterialTheme.typography.h6,
                color = ColorWhite) // todo get national code and set it here
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2x)))
            Text(text = "", // todo get phone number code and set it here
                style = MaterialTheme.typography.body2,
                color = ColorWhite)
        }
        Image(painter = painterResource(id = R.drawable.ic_user_profile),
            contentDescription = "user profile")

    }
}

@Composable
private fun MenuItem(@DrawableRes icon: Int, @StringRes title: Int, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(dimensionResource(id = R.dimen.spacing_4x)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = stringResource(id = title),
            style = MaterialTheme.typography.subtitle1TextSecondary())

        Image(painter = painterResource(id = icon),
            contentDescription = "user profile")
    }

    Divider(
        color = MaterialTheme.colors.divider(),
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_4x))
    )
}


