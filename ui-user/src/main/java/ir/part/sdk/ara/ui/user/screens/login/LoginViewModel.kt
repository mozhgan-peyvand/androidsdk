package ir.part.sdk.ara.ui.user.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.domain.user.interacors.GetLoginRemote
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private var getLoginRemote: GetLoginRemote,
    private var exceptionHelper: ExceptionHelper
) : ViewModel() {

    var loadingState = ObservableLoadingCounter()
    var uiMassageManager = UiMessageManager()

    //errorFields
    var errorValueNationalCode =
        mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))
    var errorValuePassword =
        mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))

    //fields
    var userName = mutableStateOf("")
    var password = mutableStateOf("")

    var loadingAndMessageState: StateFlow<PublicState> = combine(
        loadingState.observable,
        uiMassageManager.message
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

    fun getLogin(captchaValue: String, captchaToken: String, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getLoginRemote.invoke(
                    GetLoginRemote.Param(
                        LoginView(
                            nationalCode = userName.value,
                            password = password.value,
                            captchaValue = captchaValue,
                            captchaToken = captchaToken
                        ).toLoginParam()
                    )
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMassageManager
                ) {
                    if (it) {
                        onSuccess(it)
                    }
                }
            }
        }
    }

    fun setErrorNationalCode(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorValueNationalCode.value = errorList
    }

    fun setErrorPassword(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorValuePassword.value = errorList
    }

    fun setNationalCode(nationalCode: String) {
        userName.value = nationalCode
        setErrorNationalCode(
            validateWidget(
                ValidationField.NATIONAL_CODE,
                nationalCode
            )
        )
    }

    fun setPassword(passwordText: String) {
        password.value = passwordText
        setErrorPassword(
            validateWidget(
                ValidationField.LOGIN_PASSWORD,
                passwordText
            )
        )
    }

    fun isValidationFields(): Boolean {
        setErrorNationalCode(validateWidget(ValidationField.NATIONAL_CODE, userName.value))
        setErrorPassword(validateWidget(ValidationField.LOGIN_PASSWORD, password.value))

        return validateWidget(
            ValidationField.NATIONAL_CODE,
            userName.value
        ).second.isEmpty() &&
                validateWidget(
                    ValidationField.LOGIN_PASSWORD,
                    password.value
                ).second.isEmpty()
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            uiMassageManager.clearAllMessage()
        }
    }
}