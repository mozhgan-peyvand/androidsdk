package ir.part.sdk.ara.ui.user.changePassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.domain.user.interacors.GetChangePasswordRemote
import ir.part.sdk.ara.ui.user.util.validation.ValidationField
import ir.part.sdk.ara.ui.user.util.validation.validateWidget
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    var getChangePasswordRemote: GetChangePasswordRemote,
    var exceptionHelper: ExceptionHelper
) : ViewModel() {

    var isChangePassword = mutableStateOf(false)
    var uiMessageManager = UiMessageManager()
    var loadingState = ObservableLoadingCounter()
    var loadingErrorState = mutableStateOf<PublicState?>(null)
    var errorValuePassword =
        mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))

    //errorFields
    var errorValueNewPassword =
        mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))
    var errorValueReNewPassword =
        mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))

    //fields
    var currentPassword = mutableStateOf("")
    var newPassword = mutableStateOf("")
    var reNewPassword = mutableStateOf("")


    var loadingAndMessageState: StateFlow<PublicState> = combine(
        loadingState.observable,
        uiMessageManager.message
    ) { refreshing, message ->

        PublicState(
            refreshing = refreshing,
            message = message
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PublicState.Empty
    )


    fun getChangePasswordRemote() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getChangePasswordRemote.invoke(
                    GetChangePasswordRemote.Param(
                        changePasswordParam = ChangePasswordView(
                            currentPassword = currentPassword.value,
                            newPassword = newPassword.value,
                            renewPassword = reNewPassword.value
                        ).toChangePasswordParam()
                    )
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager
                ) {
                    if (it) {
                        isChangePassword.value = true
                    }
                }
            }
        }
    }

    fun setErrorPassword(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorValuePassword.value = errorList
    }

    fun setErrorNewPassword(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorValueNewPassword.value = errorList
    }

    fun setErrorReNewPassword(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorValueReNewPassword.value = errorList
    }

    fun isValidationField(): Boolean {

        setErrorPassword(validateWidget(ValidationField.PASSWORD, currentPassword.value))
        setErrorNewPassword(validateWidget(ValidationField.PASSWORD, newPassword.value))
        setErrorReNewPassword(validateWidget(ValidationField.PASSWORD, reNewPassword.value))

        return validateWidget(
            ValidationField.PASSWORD,
            currentPassword.value
        ).second.isNullOrEmpty() &&
                validateWidget(
                    ValidationField.PASSWORD,
                    newPassword.value
                ).second.isNullOrEmpty() &&
                validateWidget(ValidationField.PASSWORD, reNewPassword.value).second.isNullOrEmpty()
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                uiMessageManager.clearAllMessage()
            }
        }
    }
}