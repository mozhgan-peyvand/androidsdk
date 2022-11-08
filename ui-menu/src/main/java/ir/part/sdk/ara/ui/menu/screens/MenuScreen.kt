package ir.part.sdk.ara.ui.menu.screens

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getExitAppDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.merat.ui.menu.R

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel,
    onChangePasswordClick: () -> Unit,
    onTermsAndConditionClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onDisclaimerClick: () -> Unit,
    onCallCenterClick: () -> Unit,
    onGuideClick: () -> Unit,
    onSubmitCommentClick: () -> Unit,
    onRahyarClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)) {

        val showLogoutQuestionDialog = remember {
            mutableStateOf(false)
        }
        ShowExitDialogIfNeeded(showLogoutQuestionDialog.value, onConfirmedLogout = {
            menuViewModel.logout()
        }, onCancelDialog = {
            showLogoutQuestionDialog.value = false
        })
        var shouldLogout by remember {
            mutableStateOf(false)
        }
        shouldLogout = menuViewModel.successfulLogout.value == true
        if (shouldLogout) {
//            showLogoutQuestionDialog.value = false
//            onLogoutUser()
//            menuViewModel.successfulLogout.value = false // todo uncomment this and comment the rest of code block after implementing all navigation in app

            val activity = (LocalContext.current as? Activity)
            activity?.finish()
        }

        val loadingErrorState =
            rememberFlowWithLifecycle(flow = menuViewModel.loadingAndMessageState).collectAsState(
                initial = PublicState.Empty
            )
        ProcessLoadingAndErrorState(
            loadingErrorState.value,
            onErrorDialogDismissed = {
                menuViewModel.loadingAndMessageState.value.message = null
            },
            onLoadingDialogStarted = {
                showLogoutQuestionDialog.value = false
            })
        var phone by remember {
            mutableStateOf("")
        }
        phone = menuViewModel.phoneNumber.value
        var nationalCode by remember {
            mutableStateOf("")
        }
        nationalCode = menuViewModel.nationalCode.value

        MainMenuHeader(phone = phone, nationalCode = nationalCode)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        // list items
        MenuItem(
            R.drawable.ara_ic_lock,
            R.string.ara_label_change_password,
            onClick = onChangePasswordClick
        )

        MenuItem(
            icon = R.drawable.ara_ic_question,
            title = R.string.ara_label_guide,
            onClick = onGuideClick
        )

        MenuItem(
            icon = R.drawable.ara_ic_info,
            title = R.string.ara_label_about_us,
            onClick = onAboutUsClick
        )

        MenuItem(
            icon = R.drawable.ara_ic_paper,
            title = R.string.ara_label_terms_and_conditions,
            onClick = onTermsAndConditionClick
        )

        MenuItem(
            icon = R.drawable.ara_ic_award,
            title = R.string.ara_label_disclaimer,
            onClick = onDisclaimerClick
        )

        MenuItem(
            icon = R.drawable.ara_ic_location,
            title = R.string.ara_label_rahyar_address,
            onClick = { onRahyarClick() })

        MenuItem(
            icon = R.drawable.ara_ic_comment,
            title = R.string.ara_label_submit_comment,
            onClick = onSubmitCommentClick
        )

        MenuItem(
            icon = R.drawable.ara_ic_headphones_mic,
            title = R.string.ara_label_call_center,
            onClick = onCallCenterClick
        )

        MenuItem(icon = R.drawable.ara_ic_exit, title = R.string.ara_label_exit, onClick = {
            showLogoutQuestionDialog.value = true
        })
    }

}

@Composable
private fun ShowExitDialogIfNeeded(
    shouldShow: Boolean,
    onConfirmedLogout: () -> Unit,
    onCancelDialog: () -> Unit,
) {
    val exitDialog = getExitAppDialog(
        title = stringResource(id = R.string.ara_label_exit),
        description = stringResource(id = R.string.ara_label_would_you_like_to_logout_from_your_account),
        submitAction = {
            onConfirmedLogout()
        },
        cancelAction = {
            onCancelDialog()
        })


    if (shouldShow) {
        exitDialog.show()
    } else {
        exitDialog.dismiss()
    }
}

@Composable
private fun MainMenuHeader(phone: String, nationalCode: String) {
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
            Text(
                text = nationalCode,
                style = MaterialTheme.typography.h6OnPrimary(),
                color = ColorWhite
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2x)))
            Text(
                text = phone,
                style = MaterialTheme.typography.body2OnPrimary(),
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ara_ic_user_profile),
            contentDescription = "user profile"
        )

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

@Composable
private fun ProcessLoadingAndErrorState(
    input: PublicState?,
    onErrorDialogDismissed: () -> Unit,
    onLoadingDialogStarted: () -> Unit,
) {
    val dialog = getErrorDialog(
        title = stringResource(id = R.string.ara_msg_general_error_title),
        description = "",
        submitAction = {
            onErrorDialogDismissed()
        }
    )
    val loadingDialog = getLoadingDialog()

    if (input?.refreshing == true) {
        loadingDialog.show()
        onLoadingDialogStarted()
    } else {
        loadingDialog.dismiss()
        input?.message?.let { messageModel ->
            dialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}


