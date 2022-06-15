package ir.part.sdk.ara.ui.user.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.domain.tasks.interacors.GetBaseStateRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetTaskRemote
import ir.part.sdk.ara.domain.user.interacors.GetLoginRemote
import ir.part.sdk.ara.ui.user.util.validation.ValidationField
import ir.part.sdk.ara.ui.user.util.validation.validateWidget
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private var getLoginRemote: GetLoginRemote,
    private var getBaseStateRemote: GetBaseStateRemote,
    private var getTaskRemote: GetTaskRemote,
    private var exceptionHelper: ExceptionHelper
) : ViewModel() {

    var nextStep = mutableStateOf("")
    var loadingState = ObservableLoadingCounter()
    var uiMassageManager = UiMessageManager()
    val loadingErrorState = mutableStateOf<PublicState?>(null)

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

    fun getLogin(captchaValue: String, captchaToken: String) {
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
                        getBaseState()
                    }
                }
            }
        }
    }

    private fun getBaseState() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getBaseStateRemote.invoke(Unit).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMassageManager
                ) {
                    getTask()
                }
            }
        }
    }

    private fun getTask() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getTaskRemote.invoke(Unit).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMassageManager
                ) {
                    nextStep.value = it?.get(0)?.name.toString()
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

    fun isValidationFields(): Boolean {
        setErrorNationalCode(validateWidget(ValidationField.NATIONAL_CODE, userName.value))
        setErrorPassword(validateWidget(ValidationField.LOGIN_PASSWORD, password.value))

        return validateWidget(
            ValidationField.NATIONAL_CODE,
            userName.value
        ).second.isNullOrEmpty() &&
                validateWidget(ValidationField.LOGIN_PASSWORD, password.value).second.isNullOrEmpty()
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                uiMassageManager.clearAllMessage()
            }
        }
    }
}