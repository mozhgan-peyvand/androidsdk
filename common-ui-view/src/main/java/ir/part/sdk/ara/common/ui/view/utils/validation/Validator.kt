package ir.part.sdk.ara.common.ui.view.utils.validation

import android.content.Context

interface Validator {
    fun validate(input: Any): ValidationResult?
    fun getErrorMessage(context: Context): String
    fun getMessageStringId(): Int
}