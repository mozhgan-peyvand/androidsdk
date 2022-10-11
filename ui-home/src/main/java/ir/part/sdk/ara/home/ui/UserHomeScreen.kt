package ir.part.sdk.ara.home.ui

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getSdkUpdateDialog
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

    VersionDialogHandler(context, versionViewModel.hasForceVersion.value)

    UserHomeScreenElement(navigateToLoginScreen, navigateToRegisterScreen)
}

@Composable
fun VersionDialogHandler(context: Context, hasForceVersion: Boolean?) {

    var shouldShowVersionDialog by remember {
        mutableStateOf(true)
    }

    val forceUpdateDialog = getSdkUpdateDialog(
        title = stringResource(id = R.string.label_term_attention),
        message = stringResource(id = R.string.msg_updateError_when_user_use_as_a_library_is_force),
        submitText = R.string.btn_download_new_version,
        cancelText = R.string.btn_exit_from_sdk,
    )

    val updateDialog = getSdkUpdateDialog(
        title = stringResource(id = R.string.label_term_attention),
        message = stringResource(id = R.string.msg_updateError_when_user_use_as_a_library_is_not_force),
        submitText = R.string.btn_download_new_version,
        cancelText = R.string.btn_exit_dialog
    )

    if (hasForceVersion == true && shouldShowVersionDialog) {
        forceUpdateDialog.setSubmitAction {
            shouldShowVersionDialog = false
            forceUpdateDialog.dismiss()
        }.setCancelAction {
            (context as? Activity)?.finish()
        }.show()
    } else if (hasForceVersion == false) {
        updateDialog.setSubmitAction {
            shouldShowVersionDialog = false
            updateDialog.dismiss()
        }.setCancelAction {
            shouldShowVersionDialog = false
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
                painter = painterResource(id = R.drawable.merat_background),
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
                        text = stringResource(id = R.string.btn_register),
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
                        text = stringResource(id = R.string.btn_login),
                        style = MaterialTheme.typography.buttonTextPrimaryVariantStyle()
                    )
                }
            }

    })

}

@Composable
private fun ProcessLoadingAndErrorState(input: PublicState?) {
    val loadingDialog = getLoadingDialog()
    val errorDialog = getInfoDialog(
        title = stringResource(id = R.string.msg_general_error_title),
        description = ""
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