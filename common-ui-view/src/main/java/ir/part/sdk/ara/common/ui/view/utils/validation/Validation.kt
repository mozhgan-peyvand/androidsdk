package ir.part.sdk.ara.common.ui.view.utils.validation


sealed class Validation(val value: String, val validator: Validator) {
    object RequiredValidation : Validation("required", RequiredValidator())
    data class ReNewPasswordValidation(val newPassword: String) : Validation("ReNewPassword",
        ReNewPasswordValidator(newPassword)
    )
    data class NewPasswordValidation(val Password: String) : Validation("ReNewPassword",
        NewPasswordValidator(Password)
    )
    object EmailValidation : Validation("email", EmailValidator())
    object PasswordWithRegularExpression : Validation("Password",
        IsValidatePasswordWithRegularExpression()
    )
    object NationalCodeValidation : Validation("nationalCode", NationalCodeValidator())
    object MobilePhoneValidation : Validation("MobilePhone", MobilePhone())
    object PasswordLengthValidation : Validation("MobilePhone", MaxSizeString())
}







