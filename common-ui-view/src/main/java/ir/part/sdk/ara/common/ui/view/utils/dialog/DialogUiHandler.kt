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
import ir.part.sdk.ara.common.ui.view.theme.*

class DialogUiHandler {

    @Composable
    fun DeterminationDialogType(
        dialogType: String,
        submitAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        title: String? = null,
        description: String? = null
    ) {
        when (dialogType) {
            DialogType.LoadingDialog.name -> {
                LoadingDialog()
            }
            DialogType.InfoDialog.name -> {
                InformationDialog(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    submitText = StringResource.label_confirmation_dialog,
                    iconId = DrawableResource.common_view_ic_c_info,
                    iconBackgroundColor = MaterialTheme.colors.primaryDark(),
                    iconPlaceHolderColor = MaterialTheme.colors.highlightBackground()
                )
            }
            DialogType.SuccessDialog.name -> {
                InformationDialog(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    submitText = StringResource.label_confirmation_dialog,
                    iconId = DrawableResource.common_view_ic_c_check,
                    iconBackgroundColor = MaterialTheme.colors.success(),
                    iconPlaceHolderColor = MaterialTheme.colors.successBackground()
                )
            }
            DialogType.ErrorDialog.name -> {
                InformationDialog(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    submitText = StringResource.label_confirmation_dialog,
                    iconId = DrawableResource.common_view_ic_c_warning,
                    iconBackgroundColor = MaterialTheme.colors.error,
                    iconPlaceHolderColor = MaterialTheme.colors.errorBackground()
                )
            }
            DialogType.DeleteDialog.name -> {
                PromptDialog(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    cancelAction = cancelAction,
                    submitText = StringResource.label_yes,
                    cancelText = StringResource.label_no,
                    iconId = DrawableResource.common_view_ic_bin,
                    iconBackgroundColor = MaterialTheme.colors.error,
                    iconPlaceHolderColor = MaterialTheme.colors.errorBackground(),
                )
            }
            DialogType.ErrorDialogWithExit.name -> {
                PromptDialog(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    cancelAction = cancelAction,
                    submitText = StringResource.label_confirmation_dialog,
                    cancelText = StringResource.btn_logout,
                    iconId = DrawableResource.common_view_ic_c_warning,
                    iconBackgroundColor = MaterialTheme.colors.error,
                    iconPlaceHolderColor = MaterialTheme.colors.errorBackground()
                )
            }
            DialogType.ConnectionDialog.name -> {
                PromptDialog(
                    title = title ?: stringResource(id = StringResource.label_warning_title_dialog),
                    description = description
                        ?: stringResource(id = StringResource.msg_connection_error_description),
                    submitAction = submitAction,
                    cancelAction = cancelAction,
                    submitText = StringResource.btn_retry,
                    cancelText = StringResource.btn_logout,
                    iconId = DrawableResource.common_view_ic_wifi_off,
                    iconBackgroundColor = MaterialTheme.colors.error,
                    iconPlaceHolderColor = MaterialTheme.colors.errorBackground()
                )
            }
        }

    }

    @Composable
    fun LoadingDialog() {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
        val isPlaying by remember {
            mutableStateOf(true)
        }
        val progress by animateLottieCompositionAsState(
            composition,
            isPlaying = isPlaying,
            iterations = LottieConstants.IterateForever,
            restartOnPlay = false,
            speed = 2f
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(DimensionResource.spacing_2x),
                    end = dimensionResource(DimensionResource.spacing_2x)
                ),
            shape = RoundedCornerShape(dimensionResource(DimensionResource.radius_normal))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LottieAnimation(
                    composition, progress,
                    modifier = Modifier
                        .width(dimensionResource(DimensionResource.spacing_15x))
                        .height(dimensionResource(DimensionResource.spacing_15x))
                )
                Text(
                    text = stringResource(id = StringResource.label_waiting_dialog),
                    style = MaterialTheme.typography.subtitle1TextPrimaryBold(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun PromptDialog(
        title: String,
        description: String,
        submitAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        submitText: Int,
        cancelText: Int,
        iconId: Int,
        iconBackgroundColor: Color,
        iconPlaceHolderColor: Color
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(DimensionResource.radius_normal))
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
                                .padding(dimensionResource(DimensionResource.spacing_3x))
                                .width(dimensionResource(DimensionResource.spacing_10x))
                                .height(dimensionResource(DimensionResource.spacing_10x))
                                .background(
                                    iconPlaceHolderColor,
                                    shape = RoundedCornerShape(dimensionResource(id = DimensionResource.radius_big))
                                )
                                .layoutId("icon_box")
                        )
                        Icon(
                            painterResource(id = iconId),
                            contentDescription = "file_icon",
                            tint = iconBackgroundColor,
                            modifier = Modifier
                                .width(dimensionResource(DimensionResource.spacing_10x))
                                .height(dimensionResource(DimensionResource.spacing_10x))
                                .padding(dimensionResource(DimensionResource.spacing_3x))
                                .layoutId("icon")
                        )
                    }

                    Text(
                        text = title,
                        modifier = Modifier.padding(start = dimensionResource(DimensionResource.spacing_3x)),
                        style = MaterialTheme.typography.h6Bold()
                    )
                }
                Text(
                    text = description,
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
                            .padding(
                                end = dimensionResource(DimensionResource.spacing_2x),
                                start = dimensionResource(DimensionResource.spacing_5x)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ), shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = stringResource(id = submitText),
                            style = MaterialTheme.typography.subtitle2.copy(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onPrimary
                            ),
                            modifier = Modifier.padding(dimensionResource(id = DimensionResource.spacing_half_base))
                        )
                    }
                    OutlinedButton(
                        onClick = { cancelAction?.invoke() },
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                end = dimensionResource(DimensionResource.spacing_5x),
                                start = dimensionResource(DimensionResource.spacing_2x)
                            ), border = BorderStroke(
                            width = dimensionResource(id = DimensionResource.spacing_half_base),
                            color = ColorBlueDarker
                        ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.surface
                        ), shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = stringResource(id = cancelText),
                            style = MaterialTheme.typography.subtitle2.copy(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primaryVariant
                            ),
                            modifier = Modifier.padding(dimensionResource(id = DimensionResource.spacing_half_base))
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun InformationDialog(
        title: String,
        description: String,
        submitAction: (() -> Unit)? = null,
        submitText: Int,
        iconId: Int,
        iconBackgroundColor: Color,
        iconPlaceHolderColor: Color
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(DimensionResource.radius_normal))
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
                                .padding(dimensionResource(DimensionResource.spacing_3x))
                                .width(dimensionResource(DimensionResource.spacing_10x))
                                .height(dimensionResource(DimensionResource.spacing_10x))
                                .background(
                                    iconPlaceHolderColor,
                                    shape = RoundedCornerShape(dimensionResource(id = DimensionResource.spacing_2x))
                                )
                                .layoutId("icon_box")
                        )
                        Icon(
                            painter = painterResource(id = iconId),
                            contentDescription = "icon_file",
                            tint = iconBackgroundColor,
                            modifier = Modifier
                                .width(dimensionResource(DimensionResource.spacing_10x))
                                .height(dimensionResource(DimensionResource.spacing_10x))
                                .padding(dimensionResource(DimensionResource.spacing_3x))
                                .layoutId("icon")
                        )
                    }
                    Text(
                        text = title,
                        modifier = Modifier.padding(start = dimensionResource(DimensionResource.spacing_3x)),
                        style = MaterialTheme.typography.h6Bold()
                    )
                }
                Text(
                    text = description,
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
                            .padding(
                                end = dimensionResource(DimensionResource.spacing_2x),
                                start = dimensionResource(DimensionResource.spacing_5x)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ), shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = stringResource(id = submitText),
                            style = MaterialTheme.typography.subtitle2.copy(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onPrimary
                            )
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}