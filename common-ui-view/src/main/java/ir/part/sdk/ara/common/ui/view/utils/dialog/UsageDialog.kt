package ir.part.sdk.ara.common.ui.view.utils.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ir.part.sdk.ara.common.ui.view.*


typealias StringResource = R.string
typealias DrawableResource = R.drawable
typealias DimensionResource = R.dimen

@Composable
fun getLoadingDialog(): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.LOADING)
        .setDialogTitleMessage(stringResource(id = StringResource.ara_label_waiting_dialog))
    builder.Build()

    return builder
}

@Composable
fun getDeleteDialog(title: String, description: String): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.PROMPT_WARNING)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setConfigUi(
            icon = R.drawable.ara_ic_bin,
            iconTintColor = MaterialTheme.colors.error,
            boxBackgroundColor = MaterialTheme.colors.errorBackground()
        )
        .setSubmitText(StringResource.ara_label_yes)
        .setCancelText(StringResource.ara_label_no)
    builder.Build()

    return builder
}

@Composable
fun getErrorDialog(title: String, description: String, submitAction: () -> Unit): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.WARNING)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setConfigUi(
            icon = R.drawable.ara_ic_c_warning,
            iconTintColor = MaterialTheme.colors.error,
            boxBackgroundColor = MaterialTheme.colors.errorBackground()
        )
        .setSubmitAction { submitAction() }
        .setSubmitText(R.string.ara_label_dialog_submit)
    builder.Build()

    return builder
}

@Composable
fun getErrorWithExitDialog(
    title: String,
    description: String,
): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.PROMPT_WARNING)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setConfigUi(
            icon = R.drawable.ara_ic_c_warning,
            iconTintColor = MaterialTheme.colors.error,
            boxBackgroundColor = MaterialTheme.colors.errorBackground()
        )
        .setSubmitText(R.string.ara_label_dialog_submit)
        .setCancelText(R.string.ara_btn_logout)
    builder.Build()

    return builder
}


@Composable
fun getUpdateSdkDialog(
    title: String,
    description: String,
    cancelText: Int,
    submitText: Int,
    submitCallback: (() -> Unit)? = null,
    cancelCallBack: (() -> Unit)? = null
): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.UPDATE_WARNING)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setConfigUi(
            icon = R.drawable.ara_ic_c_warning,
            iconTintColor = MaterialTheme.colors.error,
            boxBackgroundColor = MaterialTheme.colors.errorBackground()
        )
        .setSubmitAction { submitCallback?.invoke() }
        .setSubmitText(submitText)
        .setCancelText(cancelText)
        .setCancelAction { cancelCallBack?.invoke() }
    builder.Build()
    return builder
}

@Composable
fun getConnectionErrorDialog(): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.PROMPT_WARNING)
        .setDialogTitleMessage(stringResource(id = StringResource.ara_label_info))
        .setDialogDetailMessage(stringResource(id = StringResource.ara_msg_connection_error_description))
        .setConfigUi(
            icon = R.drawable.ara_ic_wifi_off,
            iconTintColor = MaterialTheme.colors.error,
            boxBackgroundColor = MaterialTheme.colors.errorBackground()
        )
        .setSubmitAction { }
        .setSubmitText(StringResource.ara_btn_retry)
        .setCancelText(StringResource.ara_btn_logout)
        .setCancelAction { }
    builder.Build()

    return builder
}

@Composable
fun getSuccessDialog(
    title: String,
    description: String,
    submitAction: () -> Unit
): DialogManager {

    val builder = DialogManager.builder()
        .setDialogType(DialogType.SUCCESS)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setConfigUi(
            icon = R.drawable.ara_ic_c_check,
            iconTintColor = MaterialTheme.colors.success(),
            boxBackgroundColor = MaterialTheme.colors.successBackground()
        )
        .setSubmitAction { submitAction() }
        .setSubmitText(StringResource.ara_label_dialog_submit)
    builder.Build()

    return builder
}

@Composable
fun getExitAppDialog(
    title: String,
    description: String,
    submitAction: () -> Unit,
    cancelAction: () -> Unit
): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.PROMPT_WARNING)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setConfigUi(
            icon = R.drawable.ara_ic_c_warning,
            iconTintColor = MaterialTheme.colors.error,
            boxBackgroundColor = MaterialTheme.colors.errorBackground()
        )
        .setSubmitAction { submitAction() }
        .setSubmitText(StringResource.ara_label_dialog_submit)
        .setCancelText(StringResource.ara_label_dialog_cancel)
        .setCancelAction { cancelAction() }
    builder.Build()

    return builder
}

@Composable
fun getFileValidationPaymentDialog(
    title: String,
    description: String,
    submitAction: () -> Unit,
    cancelAction: () -> Unit
): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.PROMPT_INFO)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setConfigUi(
            icon = R.drawable.ara_ic_c_info,
            iconTintColor = MaterialTheme.colors.primaryVariant,
            boxBackgroundColor = MaterialTheme.colors.highlightBackground()
        )
        .setSubmitAction { submitAction() }
        .setSubmitText(StringResource.ara_label_payment)
        .setCancelText(StringResource.ara_label_dialog_cancel)
        .setCancelAction { cancelAction() }
    builder.Build()

    return builder
}

