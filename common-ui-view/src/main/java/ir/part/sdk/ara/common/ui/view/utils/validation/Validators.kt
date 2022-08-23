package ir.part.sdk.ara.common.ui.view.utils.validation

import android.content.Context
import android.util.Patterns
import ir.part.sdk.ara.common.ui.view.R
import java.util.regex.Matcher
import java.util.regex.Pattern


class RequiredValidator : Validator {

    override fun validate(input: Any): ValidationResult? {
        (input as? String)?.let { text ->
            return if (text.isEmpty()) {
                ValidationResult(this)
            } else null
        } ?: return ValidationResult(this)
    }

    override fun getErrorMessage(context: Context) =
        String.format(context.getString(getMessageStringId()))

    override fun getMessageStringId() = R.string.msg_empty_not_allow
}

class EmailValidator : Validator {

    override fun validate(input: Any): ValidationResult? {
        return if ((input as? String)?.isNotEmpty() == true) {
            if (Patterns.EMAIL_ADDRESS.matcher(input as? String).matches()
            ) null else ValidationResult(this)
        } else null
    }

    override fun getErrorMessage(context: Context) =
        String.format(context.getString(getMessageStringId()))

    override fun getMessageStringId() = R.string.msg_invalid_email

}

class ReNewPasswordValidator(val newPassword: String) : Validator {

    override fun validate(input: Any): ValidationResult? {
        if (!(input as? String).isNullOrEmpty() && newPassword.isNotEmpty()) {
            return if ((input as? String) == newPassword) {
                null
            } else {
                ValidationResult(this)
            }
        }
        return null
    }

    override fun getErrorMessage(context: Context) =
        String.format(context.getString(getMessageStringId()))

    override fun getMessageStringId() = R.string.msg_invalid_new_password_by_renew_password

}

class NewPasswordValidator(val newPassword: String) : Validator {

    override fun validate(input: Any): ValidationResult? {
        if (!(input as? String).isNullOrEmpty() && newPassword.isNotEmpty()) {
            return if ((input as? String) != newPassword) {
                null
            } else {
                ValidationResult(this)
            }
        }
        return null
    }

    override fun getErrorMessage(context: Context) =
        String.format(context.getString(getMessageStringId()))

    override fun getMessageStringId() = R.string.msg_different_password
}

class IsValidatePasswordWithRegularExpression : Validator {
    override fun validate(input: Any): ValidationResult? {
        return if ((input as? String)?.isNotEmpty() == true) {
            val pattern: Pattern
            val passwordPatterns = "^(?=.*?[A-Za-z])(?=.*?[0-9]).{8,}\$"
            pattern = Pattern.compile(passwordPatterns)
            val matcher: Matcher = pattern.matcher(input as? String)
            if (matcher.matches())
                null else ValidationResult(this)
        } else null
    }

    override fun getErrorMessage(context: Context): String =
        String.format(context.getString(getMessageStringId()))


    override fun getMessageStringId(): Int = R.string.msg_invalid_not_persian

}

class NationalCodeValidator : Validator {

    override fun validate(input: Any): ValidationResult? {
        val identicalDigits = arrayOf(
            "0000000000",
            "1111111111",
            "2222222222",
            "3333333333",
            "4444444444",
            "5555555555",
            "6666666666",
            "7777777777",
            "8888888888",
            "9999999999"
        )

        val result = ValidationResult(this)
        when {
            (input as? String ?: "").trim { it <= ' ' }.isEmpty() -> //Melli Code is empty
                return result // Melli Code is empty
            (input as? String ?: "").length != 10 -> //Melli Code must be exactly 10 digits
                return result // Melli Code is less or more than 10 digits
            identicalDigits.contains(
                (input as? String ?: "")
            ) -> //MelliCode is not valid (Fake MelliCode)
                return result // Fake Melli Code
            else -> {
                var sum = 0

                for (i in 0..8) {
                    sum += Character.getNumericValue((input as? String ?: "")[i]) * (10 - i)
                }

                val lastDigit: Int
                val divideRemaining = sum % 11

                lastDigit = if (divideRemaining < 2) {
                    divideRemaining
                } else {
                    11 - divideRemaining
                }

                return if (Character.getNumericValue((input as? String ?: "")[9]) == lastDigit) null
                else result
            }
        }
    }

    override fun getErrorMessage(context: Context) =
        String.format(context.getString(getMessageStringId()))

    override fun getMessageStringId() = R.string.msg_invalid_national_code

}

class MobilePhone : Validator {

    override fun validate(input: Any): ValidationResult? {
        return if ((input as? String ?: "").isNotEmpty()
            && (input as? String ?: "").matches("09\\d{9}".toRegex())
        ) null else ValidationResult(this)
    }

    override fun getErrorMessage(context: Context) =
        String.format(context.getString(getMessageStringId()))

    override fun getMessageStringId() = R.string.msg_invalid_mobil
}

class MaxSizeString : Validator {
    override fun validate(input: Any): ValidationResult? {
        return if (!(input as? String).isNullOrEmpty()) {
            if ((input as? String)?.length ?: 0 < 8) {
                ValidationResult(this)
            } else null
        } else null
    }

    override fun getErrorMessage(context: Context) =
        String.format(context.getString(getMessageStringId()))

    override fun getMessageStringId() = R.string.msg_invalid_password_length

}

class PersianText : Validator {

    private val pattern =
        "^[\\u200C\\u0621\\u0622\\u0627\\u0623\\u0628\\u067e\\u062a\\u062b\\u062c\\u0686\\u062d\\u062e\\u062f\\u0630\\u0631\\u0632\\u0698\\u0633-\\u063a\\u0641\\u0642\\u06a9\\u06af\\u0644-\\u0646\\u0648\\u0624\\u0647\\u06cc\\u0626\\u0625\\u0671\\u0643\\u0629\\u064a\\u0649\\s]+\$"


    override fun validate(input: Any): ValidationResult? {
        return if ((input as? String ?: "").isNotEmpty()
            && (input as? String ?: "").matches(pattern.toRegex())
        ) null else ValidationResult(this)
    }

    override fun getErrorMessage(context: Context) =
        String.format(context.getString(getMessageStringId()))

    override fun getMessageStringId() = R.string.msg_invalid_persian
}