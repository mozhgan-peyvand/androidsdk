package ir.part.sdk.ara.ui.user.screens.login

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
import kotlinx.coroutines.flow.*
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
        MutableStateFlow(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))
    var errorValuePassword =
        MutableStateFlow(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))

    //fields
    private var userName = ""
    private var userPassword = ""

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
                            nationalCode = userName,
                            password = userPassword,
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
        userName = nationalCode
        setErrorNationalCode(
            validateWidget(
                ValidationField.NATIONAL_CODE,
                nationalCode
            )
        )
    }

    fun setPassword(passwordText: String) {
        userPassword = passwordText
        setErrorPassword(
            validateWidget(
                ValidationField.LOGIN_PASSWORD,
                passwordText
            )
        )
    }

    fun isValidationFields(): Boolean {
        setErrorNationalCode(validateWidget(ValidationField.NATIONAL_CODE, userName))
        setErrorPassword(validateWidget(ValidationField.LOGIN_PASSWORD, userPassword))

        return validateWidget(
            ValidationField.NATIONAL_CODE,
            userName
        ).second.isEmpty() &&
                validateWidget(
                    ValidationField.LOGIN_PASSWORD,
                    userPassword
                ).second.isEmpty()
    }

    fun clearAllMessage() {
        viewModelScope.launch {
            uiMassageManager.clearAllMessage()
        }
    }
}