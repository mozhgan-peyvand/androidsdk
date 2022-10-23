package ir.part.sdk.ara.common.ui.view.utils.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ir.part.sdk.ara.common.ui.view.*

class DialogTypeHandler {

    @Composable
    fun DeterminationDialogType(
        dialogType: String,
        submitAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        submitText: Int? = null,
        cancelText: Int? = null,
        title: String? = null,
        description: String? = null,
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
                    iconTintColor = MaterialTheme.colors.primaryDark(),
                    boxBackgroundColor = MaterialTheme.colors.errorBackground()
                )
            }
            DialogType.SuccessDialog.name -> {
                InformationDialog(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    submitText = StringResource.label_confirmation_dialog,
                    iconId = DrawableResource.common_view_ic_c_check,
                    iconTintColor = MaterialTheme.colors.success(),
                    boxBackgroundColor = MaterialTheme.colors.successBackground()
                )
            }
            DialogType.ErrorDialog.name -> {
                InformationDialog(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    submitText = StringResource.label_confirmation_dialog,
                    iconId = DrawableResource.common_view_ic_c_warning,
                    iconTintColor = MaterialTheme.colors.error,
                    boxBackgroundColor = MaterialTheme.colors.errorBackground(),
                    cancelLabel = cancelText,
                    cancelAction = cancelAction
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
                    iconTintColor = MaterialTheme.colors.error,
                    boxBackgroundColor = MaterialTheme.colors.errorBackground(),
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
                    iconTintColor = MaterialTheme.colors.error,
                    boxBackgroundColor = MaterialTheme.colors.errorBackground()
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
                    iconTintColor = MaterialTheme.colors.error,
                    boxBackgroundColor = MaterialTheme.colors.errorBackground()
                )
            }
            DialogType.UpdateSdkDialog.name -> {
                PromptDialog(
                    title = title ?: stringResource(id = StringResource.label_warning_title_dialog),
                    description = description ?: "",
                    submitText = submitText ?: 0,
                    cancelText = cancelText ?: 0,
                    submitAction = submitAction,
                    cancelAction = cancelAction,
                    iconId = R.drawable.merat_ic_c_info,
                    iconTintColor = MaterialTheme.colors.error(),
                    boxBackgroundColor = MaterialTheme.colors.errorBackground()
                )
            }
        }

    }

}