package ir.part.sdk.ara.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ir.part.sdk.ara.common.ui.view.theme.buttonTextStyle
import ir.part.sdk.ara.common.ui.view.theme.primaryDark

@Composable
fun UserHomeScreen(navigateToLoginScreen: () -> Unit, navigateToRegisterScreen: () -> Unit) {
    UserHomeScreenElement(navigateToLoginScreen, navigateToRegisterScreen)
}

@Composable
private fun UserHomeScreenElement(
    navigateToLoginScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit
) {
    ConstraintLayout {
        val (userHomeBackground, homePageLogo) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.merat_background),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .constrainAs(userHomeBackground) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.matchParent
                }
        )

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.spacing_7x))
                .constrainAs(homePageLogo) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }) {
            Image(
                painter = painterResource(id = R.drawable.merat_logo_credit),
                contentDescription = "background",
                Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_8x))
            )
            Image(
                painter = painterResource(id = R.drawable.merat_ic_user_home_page_sentence),
                contentDescription = "user_home_page_sentence",
                Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.spacing_8x),
                    start = dimensionResource(id = R.dimen.spacing_16x),
                    end = dimensionResource(id = R.dimen.spacing_16x)
                )
            )
            Button(
                onClick = { navigateToRegisterScreen.invoke() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryDark(),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.spacing_4x),
                        end = dimensionResource(id = R.dimen.spacing_4x),
                        bottom = dimensionResource(id = R.dimen.spacing_base)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.btn_register),
                    style = MaterialTheme.typography.buttonTextStyle()
                )
            }

            OutlinedButton(
                onClick = { navigateToLoginScreen.invoke() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primaryDark()
                ),
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.spacing_4x),
                        end = dimensionResource(id = R.dimen.spacing_4x)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                contentPadding = PaddingValues(10.dp),
                border = BorderStroke(2.dp, MaterialTheme.colors.primaryDark()),

                ) {
                Text(
                    text = stringResource(id = R.string.btn_login),
                    style = MaterialTheme.typography.buttonTextStyle()
                )
            }
        }
    }
}