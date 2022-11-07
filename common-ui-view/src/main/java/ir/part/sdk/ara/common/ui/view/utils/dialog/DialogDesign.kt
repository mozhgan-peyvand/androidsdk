package ir.part.sdk.ara.common.ui.view.utils.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.airbnb.lottie.compose.*
import ir.part.sdk.ara.common.ui.view.R
import ir.part.sdk.ara.common.ui.view.onPrimary
import ir.part.sdk.ara.common.ui.view.primaryVariant
import ir.part.sdk.ara.common.ui.view.theme.body2BoldOnPrimary
import ir.part.sdk.ara.common.ui.view.theme.h6BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.theme.subtitle1BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.theme.subtitle1TextPrimary

@Composable
fun LoadingDialog() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ara_loading_animation))
    val isPlaying by remember {
        mutableStateOf(true)
    }
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever,
        restartOnPlay = false,
        speed = 2.5f
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(DimensionResource.spacing_4x)
            ),
        shape = RoundedCornerShape(dimensionResource(DimensionResource.radius_normal)),
        elevation = dimensionResource(id = R.dimen.card_elevation_normal)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(DimensionResource.spacing_2x),
                    end = dimensionResource(DimensionResource.spacing_2x)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = StringResource.ara_label_waiting_dialog),
                style = MaterialTheme.typography.subtitle1BoldTextPrimary(),
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(3f)
            )
            LottieAnimation(
                composition, progress,
                modifier = Modifier
                    .width(dimensionResource(DimensionResource.spacing_15x))
                    .height(dimensionResource(DimensionResource.spacing_15x))
                    .weight(1f),
                alignment = Alignment.CenterEnd
            )
        }
    }
}

@Composable
fun DialogManagerPrompt(
    title: String,
    description: String,
    submitAction: (() -> Unit)? = null,
    cancelAction: (() -> Unit)? = null,
    submitText: Int,
    cancelText: Int,
    iconId: Int,
    iconTintColor: Color,
    boxBackgroundColor: Color,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = DimensionResource.spacing_4x)),
        shape = RoundedCornerShape(dimensionResource(DimensionResource.radius_normal)),
        elevation = dimensionResource(id = DimensionResource.radius_small)
    ) {
        Column(modifier = Modifier.padding(bottom = dimensionResource(DimensionResource.spacing_5x))) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val constrain = ConstraintSet {
                    val iconBox = createRefFor("icon_box")
                    val icon = createRefFor("icon")
                    constrain(iconBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    constrain(icon) {
                        top.linkTo(iconBox.top)
                        start.linkTo(iconBox.start)
                        end.linkTo(iconBox.end)
                        bottom.linkTo(iconBox.bottom)
                    }

                }
                ConstraintLayout(constrain) {
                    Box(
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = DimensionResource.spacing_5x),
                                top = dimensionResource(id = DimensionResource.spacing_3x)
                            )
                            .width(dimensionResource(DimensionResource.spacing_10x))
                            .height(dimensionResource(DimensionResource.spacing_10x))
                            .background(
                                boxBackgroundColor,
                                shape = RoundedCornerShape(dimensionResource(id = DimensionResource.radius_big))
                            )
                            .layoutId("icon_box")
                    )
                    Icon(
                        painterResource(id = iconId),
                        contentDescription = "file_icon",
                        tint = iconTintColor,
                        modifier = Modifier
                            .width(dimensionResource(DimensionResource.spacing_10x))
                            .height(dimensionResource(DimensionResource.spacing_10x))
                            .padding(
                                start = dimensionResource(id = DimensionResource.spacing_5x),
                                top = dimensionResource(id = DimensionResource.spacing_3x)
                            )
                            .layoutId("icon")
                    )
                }

                Text(
                    text = title,
                    modifier = Modifier.padding(start = dimensionResource(DimensionResource.spacing_3x)),
                    style = MaterialTheme.typography.h6BoldTextPrimary()
                )
            }
            Text(
                text = description,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(
                    top = dimensionResource(DimensionResource.spacing_5x),
                    start = dimensionResource(DimensionResource.spacing_5x),
                    end = dimensionResource(DimensionResource.spacing_5x),
                ), style = MaterialTheme.typography.subtitle1TextPrimary()
            )
            Row(modifier = Modifier.padding(top = dimensionResource(DimensionResource.spacing_5x))) {
                Button(
                    onClick = { submitAction?.invoke() },
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = dimensionResource(id = R.dimen.spacing_5x))
                        .padding(
                            top = dimensionResource(DimensionResource.spacing_5x),
                            start = dimensionResource(DimensionResource.spacing_5x)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant(),
                        contentColor = MaterialTheme.colors.onPrimary()
                    ), shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_normal))
                ) {
                    Text(
                        text = stringResource(id = submitText),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2BoldOnPrimary(),
                        modifier = Modifier.padding(dimensionResource(id = DimensionResource.spacing_half_base))
                    )
                }
                OutlinedButton(
                    onClick = { cancelAction?.invoke() },
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = dimensionResource(id = R.dimen.spacing_5x))
                        .padding(
                            end = dimensionResource(DimensionResource.spacing_5x),
                            top = dimensionResource(DimensionResource.spacing_5x),
                            start = dimensionResource(DimensionResource.spacing_2x)
                        ), border = BorderStroke(
                        width = dimensionResource(id = DimensionResource.spacing_half_base),
                        color = MaterialTheme.colors.primaryVariant()
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.onPrimary()
                    ), shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_normal))
                ) {
                    Text(
                        text = stringResource(id = cancelText),
                        color = MaterialTheme.colors.primaryVariant(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2BoldOnPrimary(),
                        modifier = Modifier.padding(dimensionResource(id = DimensionResource.spacing_half_base))
                    )
                }
            }
        }
    }
}

@Composable
fun DialogManagerAlert(
    title: String,
    description: String,
    submitAction: (() -> Unit)? = null,
    submitText: Int,
    iconId: Int,
    iconTintColor: Color,
    boxBackgroundColor: Color,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = DimensionResource.spacing_4x)),
        shape = RoundedCornerShape(dimensionResource(DimensionResource.radius_normal)),
        elevation = dimensionResource(id = DimensionResource.radius_small)
    ) {
        Column(modifier = Modifier.padding(bottom = dimensionResource(DimensionResource.spacing_5x))) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                val constrain = ConstraintSet {
                    val iconBox = createRefFor("icon_box")
                    val icon = createRefFor("icon")
                    constrain(iconBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    constrain(icon) {
                        top.linkTo(iconBox.top)
                        start.linkTo(iconBox.start)
                        end.linkTo(iconBox.end)
                        bottom.linkTo(iconBox.bottom)
                    }

                }
                ConstraintLayout(constrain) {
                    Box(
                        modifier = Modifier

                            .padding(
                                start = dimensionResource(id = DimensionResource.spacing_5x),
                                top = dimensionResource(id = DimensionResource.spacing_3x)
                            )
                            .width(dimensionResource(DimensionResource.spacing_10x))
                            .height(dimensionResource(DimensionResource.spacing_10x))
                            .background(
                                boxBackgroundColor,
                                shape = RoundedCornerShape(dimensionResource(id = DimensionResource.radius_big))
                            )
                            .layoutId("icon_box")
                    )
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = "icon_file",
                        tint = iconTintColor,
                        modifier = Modifier
                            .width(dimensionResource(DimensionResource.spacing_10x))
                            .height(dimensionResource(DimensionResource.spacing_10x))
                            //maybe need use box padding for hear
                            .padding(
                                start = dimensionResource(id = DimensionResource.spacing_5x),
                                top = dimensionResource(id = DimensionResource.spacing_3x)
                            )
                            .layoutId("icon")
                    )
                }
                Text(
                    text = title,
                    modifier = Modifier.padding(start = dimensionResource(DimensionResource.spacing_3x)),
                    style = MaterialTheme.typography.h6BoldTextPrimary()
                )
            }
            Text(
                text = description,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(
                    top = dimensionResource(DimensionResource.spacing_5x),
                    start = dimensionResource(DimensionResource.spacing_5x),
                    end = dimensionResource(DimensionResource.spacing_5x)
                ), style = MaterialTheme.typography.subtitle1TextPrimary()
            )
            Row(modifier = Modifier.padding(top = dimensionResource(DimensionResource.spacing_5x))) {

                Button(
                    onClick = { submitAction?.invoke() },
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = dimensionResource(id = R.dimen.spacing_5x))
                        .padding(
                            end = dimensionResource(DimensionResource.spacing_5x),
                            top = dimensionResource(DimensionResource.spacing_5x),
                            start = dimensionResource(DimensionResource.spacing_5x)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant(),
                        contentColor = MaterialTheme.colors.onPrimary()
                    ), shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_normal))
                ) {
                    Text(
                        text = stringResource(id = submitText),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2BoldOnPrimary()
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}