package ir.part.sdk.ara.common.ui.view.utils.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class DialogTypeHandler {

    @Composable
    fun DeterminationDialogType(
        dialogType: DialogType,
        submitAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        submitText: Int? = null,
        cancelText: Int? = null,
        title: String? = null,
        description: String? = null,
        icon: Int,
        iconTintColor: Color,
        boxBackgroundColor: Color
    ) {
        when (dialogType) {
            DialogType.LOADING -> {
                LoadingDialog()
            }
            DialogType.INFO, DialogType.SUCCESS, DialogType.WARNING -> {
                DialogManagerAlert(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    submitText = StringResource.label_confirmation_dialog,
                    iconId = icon,
                    iconTintColor = iconTintColor,
                    boxBackgroundColor = boxBackgroundColor
                )
            }
            DialogType.PROMPT_INFO, DialogType.PROMPT_SUCCESS, DialogType.PROMPT_WARNING, DialogType.UPDATE_WARNING -> {
                DialogManagerPrompt(
                    title = title ?: "",
                    description = description ?: "",
                    submitAction = submitAction,
                    cancelAction = cancelAction,
                    submitText = submitText ?: 0,
                    cancelText = cancelText ?: 0,
                    iconId = icon,
                    iconTintColor = iconTintColor,
                    boxBackgroundColor = boxBackgroundColor,
                )
            }
        }

    }

}