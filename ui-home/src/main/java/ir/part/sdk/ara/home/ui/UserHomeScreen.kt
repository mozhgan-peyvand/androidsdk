package ir.part.sdk.ara.home.ui

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.primaryVariant
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.buttonTextPrimaryVariantStyle
import ir.part.sdk.ara.common.ui.view.theme.buttonTextStyle
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getUpdateSdkDialog
import ir.part.sdk.ara.home.version.VersionViewModel

@Composable
fun UserHomeScreen(
    navigateToLoginScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    versionViewModel: VersionViewModel
) {
    val context = LocalContext.current

    val loadingErrorState =
        rememberFlowWithLifecycle(flow = versionViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    ProcessLoadingAndErrorState(input = loadingErrorState.value)

    VersionDialogHandler(
        context = context,
        hasForceVersion = versionViewModel.hasForceVersion.value,
        shouldShowVersionDialog = versionViewModel.shouldShowVersionDialog.value,
        onIsForceDialogShow = {
            versionViewModel.shouldShowVersionDialog.value = false
        },
        onNotForceDialogShow = {
            versionViewModel.onNotForceUpdateDialogShow()
            versionViewModel.shouldShowVersionDialog.value = false
        }
    )

    UserHomeScreenElement(navigateToLoginScreen, navigateToRegisterScreen)
}

@Composable
fun VersionDialogHandler(
    context: Context,
    hasForceVersion: Boolean?,
    shouldShowVersionDialog: Boolean,
    onIsForceDialogShow: () -> Unit,
    onNotForceDialogShow: () -> Unit
) {


    val forceUpdateDialog = getUpdateSdkDialog(
        title = stringResource(id = R.string.ara_label_term_attention),
        description = stringResource(id = R.string.ara_msg_updateError_when_user_use_as_a_library_is_force),
        submitText = R.string.ara_btn_download_new_version,
        cancelText = R.string.ara_btn_exit_from_sdk,
    )

    val updateDialog = getUpdateSdkDialog(
        title = stringResource(id = R.string.ara_label_term_attention),
        description = stringResource(id = R.string.ara_msg_updateError_when_user_use_as_a_library_is_not_force),
        submitText = R.string.ara_btn_download_new_version,
        cancelText = R.string.ara_btn_exit_dialog
    )

    if (hasForceVersion == true && shouldShowVersionDialog) {
        forceUpdateDialog.setSubmitAction {
            onIsForceDialogShow()
            forceUpdateDialog.dismiss()
        }.setCancelAction {
            (context as? Activity)?.finish()
        }.show()
    } else if (hasForceVersion == false && shouldShowVersionDialog) {
        updateDialog.setSubmitAction {
            onNotForceDialogShow()
            updateDialog.dismiss()
        }.setCancelAction {
            onNotForceDialogShow()
            updateDialog.dismiss()
        }.show()
    }
}

@Composable
private fun UserHomeScreenElement(
    navigateToLoginScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()
        , content = {
            val (userHomeBackground, homePageLogo) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ara_background),
                contentDescription = "background",
                contentScale = ContentScale.Crop,
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
                    .padding(bottom = dimensionResource(id = R.dimen.spacing_8x))
                    .constrainAs(homePageLogo) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }) {
                Image(
                    painter = painterResource(id = R.drawable.ara_logo_credit),
                    contentDescription = "background",
                    Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_8x))
                )
                Image(
                    painter = painterResource(id = R.drawable.ara_ic_user_home_page_sentence),
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
                        backgroundColor = MaterialTheme.colors.primaryVariant(),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_normal)),
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.spacing_4x),
                            end = dimensionResource(id = R.dimen.spacing_4x)
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.ara_btn_register),
                        style = MaterialTheme.typography.buttonTextStyle()
                    )
                }

                OutlinedButton(
                    onClick = { navigateToLoginScreen.invoke() },
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.spacing_4x),
                            end = dimensionResource(id = R.dimen.spacing_4x),
                            top = dimensionResource(id = R.dimen.spacing_4x)

                        )
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = MaterialTheme.colors.primaryVariant()
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_normal)),
                    border = BorderStroke(width = 2.dp, MaterialTheme.colors.primaryVariant()),

                    ) {
                    Text(
                        text = stringResource(id = R.string.ara_btn_login),
                        style = MaterialTheme.typography.buttonTextPrimaryVariantStyle()
                    )
                }
            }

    })

}

@Composable
private fun ProcessLoadingAndErrorState(input: PublicState?) {
    val loadingDialog = getLoadingDialog()
    val errorDialog = getErrorDialog(
        title = stringResource(id = R.string.ara_msg_general_error_title),
        description = "",
        submitAction = { }
    )
    if (input?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        input?.message?.let { messageModel ->
            errorDialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}