package ir.part.sdk.ara.common.ui.view.utils.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ir.part.sdk.ara.common.ui.view.R

typealias StringResource = R.string
typealias DrawableResource = R.drawable
typealias DimensionResource = R.dimen

@Composable
fun getInfoDialog(title: String, description: String): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.InfoDialog.name)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setSubmitAction { }
    builder.Build()

    return builder
}

@Composable
fun getDeleteDialog(title: String, description: String): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.DeleteDialog.name)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
    builder.Build()

    return builder
}

@Composable
fun getLoadingDialog(): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.LoadingDialog.name)
        .setDialogTitleMessage(stringResource(id = StringResource.label_waiting_dialog))
    builder.Build()

    return builder
}

@Composable
fun getTryAgainDialog(
    describeMessage: String,
    submitAction: () -> Unit,
): DialogManager {
    val builder = DialogManager.builder()
        .setDialogType(DialogType.ErrorDialogWithExit.name)
        .setDialogTitleMessage(stringResource(id = StringResource.label_warning_title_dialog))
        .setDialogDetailMessage(describeMessage)
        .setSubmitAction { submitAction.invoke() }
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
        .setDialogType(DialogType.SuccessDialog.name)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setSubmitAction { submitAction() }
    builder.Build()

    return builder
}

@Composable
fun getErrorDialog(
    title: String,
    description: String,
    submitAction: () -> Unit
): DialogManager {

    val builder = DialogManager.builder()
        .setDialogType(DialogType.ErrorDialog.name)
        .setDialogTitleMessage(title)
        .setDialogDetailMessage(description)
        .setSubmitAction { submitAction() }
    builder.Build()

    return builder
}