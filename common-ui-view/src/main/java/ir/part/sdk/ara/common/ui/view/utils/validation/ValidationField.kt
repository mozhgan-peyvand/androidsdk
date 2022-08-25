package ir.part.sdk.ara.common.ui.view.utils.validation

enum class ValidationField {
    NATIONAL_CODE,
    EMAIL,
    PHONE,
    CAPTCHA,
    PASSWORD,
    NEW_PASSWORD,
    RE_NEW_PASSWORD,
    ACTIVITY_CODE,
    LOGIN_PASSWORD
}

fun validateWidget(
    key: ValidationField,
    value: String,
    newValue: String? = null
): Pair<ValidationField, List<ValidationResult>> {
    val errors = mutableListOf<ValidationResult>()
    val validations: MutableList<Validation> = mutableListOf()
    when (key) {
        ValidationField.NATIONAL_CODE -> {
            validations.addAll(
                listOf(
                    Validation.NationalCodeValidation,
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
                    Validation.EmailValidation,
                    Validation.RequiredValidation
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

        ValidationField.ACTIVITY_CODE -> {
            validations.addAll(
                listOf(
                    Validation.RequiredValidation
                )
            )
        }

        ValidationField.PASSWORD -> {
            validations.addAll(
                listOf(
                    Validation.PasswordWithRegularExpression,
                    Validation.PasswordLengthValidation,
                    Validation.RequiredValidation
                )
            )
        }
        ValidationField.NEW_PASSWORD -> {
            validations.addAll(
                listOf(
                    Validation.NewPasswordValidation(newValue ?: ""),
                    Validation.PasswordLengthValidation,
                    Validation.PasswordWithRegularExpression,
                    Validation.RequiredValidation
                )
            )
        }
        ValidationField.RE_NEW_PASSWORD -> {
            validations.addAll(
                listOf(
                    Validation.ReNewPasswordValidation(newValue ?: ""),
                    Validation.PasswordLengthValidation,
                    Validation.PasswordWithRegularExpression,
                    Validation.RequiredValidation
                )
            )
        }
        ValidationField.LOGIN_PASSWORD -> {
            validations.addAll(
                listOf(Validation.RequiredValidation)
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