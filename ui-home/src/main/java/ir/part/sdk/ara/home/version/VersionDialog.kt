package ir.part.sdk.ara.home.version

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.utils.dialog.DialogManager
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getSdkUpdateDialog
import ir.part.sdk.ara.home.ui.R

@Composable
fun ShowVersionDialog(versionViewModel: VersionViewModel) {
    val context = LocalContext.current
    val loadingErrorState =
        rememberFlowWithLifecycle(flow = versionViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )
    ProcessLoadingAndErrorState(input = loadingErrorState.value)

    val isShowForceDialog = remember {
        mutableStateOf<Boolean?>(null)
    }
    isShowForceDialog.value = versionViewModel.listHasFilterOrNot.value

    if (isShowForceDialog.value == true) {
        getSdkUpdateDialog(
            title = stringResource(id = R.string.label_term_attention),
            message = stringResource(id = R.string.msg_updateError_when_user_use_as_a_library_is_force),
            submitText = R.string.btn_download_new_version,
            cancelText = R.string.btn_exit_from_sdk,
        ).setSubmitAction {
            DialogManager().dismiss()
        }.setCancelAction {
            (context as? Activity)?.finish()
        }.show()
    } else if (isShowForceDialog.value == false) {
        getSdkUpdateDialog(
            title = stringResource(id = R.string.label_term_attention),
            message = stringResource(id = R.string.msg_updateError_when_user_use_as_a_library_is_not_force),
            submitText = R.string.btn_download_new_version,
            cancelText = R.string.btn_exit_dialog
        ).setSubmitAction {
            DialogManager().dismiss()
        }.setCancelAction {
            DialogManager().dismiss()
        }.show()
    }
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