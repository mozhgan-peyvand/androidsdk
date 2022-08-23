package ir.part.sdk.ara.ui.menu.util.validation

import ir.part.sdk.ara.common.ui.view.utils.validation.Validation
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult

enum class ValidationField {
    REQUIRED,
    PERSIAN_REQUIRED,
    EMAIL,
    PHONE,
    CAPTCHA,
}

fun validateWidget(
    key: ValidationField,
    value: String,
): Pair<ValidationField, List<ValidationResult>> {
    val errors = mutableListOf<ValidationResult>()
    val validations: MutableList<Validation> = mutableListOf()
    when (key) {

        ValidationField.REQUIRED -> {
            validations.addAll(
                listOf(
                    Validation.RequiredValidation
                )
            )
        }

        ValidationField.PERSIAN_REQUIRED -> {
            validations.addAll(
                listOf(
                    Validation.PersianValidation,
                    Validation.RequiredValidation
                )
            )
        }

        ValidationField.PHONE -> {
            validations.addAll(
                listOf(
                    Validation.MobilePhoneValidation,
                    Validation.RequiredValidation
                )
            )
        }

        ValidationField.EMAIL -> {
            validations.addAll(
                listOf(
                    Validation.EmailValidation
                )
            )
        }
        ValidationField.CAPTCHA -> {
            validations.addAll(
                listOf(
                    Validation.RequiredValidation
                )
            )
        }


    }
    validations.forEach { validation ->
        value.let { it ->
            validation.validator.validate(it)?.let {
                errors.add(it)
            }
        }
    }
    return Pair(key, errors)
}