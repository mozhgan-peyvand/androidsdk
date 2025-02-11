package ir.part.sdk.ara.common.ui.view.utils.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

open class DialogManager {
    private lateinit var _dialogType: DialogType
    private var _titleDialog: String? = null
    private var _descriptionDialog: String? = null
    private var _submitText: Int? = null
    private var _cancelText: Int? = null
    private var _submitAction: (() -> Unit)? = null
    private var _cancelAction: (() -> Unit)? = null
    private var _icon: Int? = null
    private var _iconTintColor: Color? = null
    private var _boxBackgroundColor: Color? = null

    private lateinit var isOpenDialog: MutableState<Boolean>

    fun setDialogType(dialogType: DialogType): DialogManager {
        _dialogType = dialogType
        return this
    }

    fun setDialogTitleMessage(title: String): DialogManager {
        _titleDialog = title
        return this
    }

    fun setSubmitText(submitText: Int): DialogManager {
        _submitText = submitText
        return this
    }

    fun setCancelText(cancelText: Int): DialogManager {
        this._cancelText = cancelText
        return this
    }

    fun setDialogDetailMessage(detail: String): DialogManager {
        _descriptionDialog = detail
        return this
    }

    fun setConfigUi(icon: Int, iconTintColor: Color, boxBackgroundColor: Color): DialogManager {
        _icon = icon
        _iconTintColor = iconTintColor
        _boxBackgroundColor = boxBackgroundColor
        return this
    }

    fun setSubmitAction(submitAction: () -> Unit): DialogManager {
        _submitAction = submitAction
        return this
    }

    fun setCancelAction(cancelAction: () -> Unit): DialogManager {
        _cancelAction = cancelAction
        return this
    }


    companion object {
        fun builder(): DialogManager {
            return DialogManager()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun Build() {
        isOpenDialog = remember {
            mutableStateOf(false)
        }
        if (isOpenDialog.value) {
            Dialog(
                onDismissRequest = {},
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false,
                    usePlatformDefaultWidth = false
                )
            ) {
                DialogTypeHandler().DeterminationDialogType(
                    _dialogType,
                    submitAction = { dismiss(_submitAction) },
                    cancelAction = { dismiss(_cancelAction) },
                    title = _titleDialog,
                    description = _descriptionDialog,
                    cancelText = _cancelText,
                    submitText = _submitText,
                    icon = _icon ?: 0,
                    iconTintColor = _iconTintColor ?: Color.Transparent,
                    boxBackgroundColor = _boxBackgroundColor ?: Color.Transparent
                )
            }
        }
    }

    fun show() {
        isOpenDialog.value = true
    }

    fun dismiss(
        action: (() -> Unit)? = null,
    ) {
        action?.invoke()
        isOpenDialog.value = false
    }
}
