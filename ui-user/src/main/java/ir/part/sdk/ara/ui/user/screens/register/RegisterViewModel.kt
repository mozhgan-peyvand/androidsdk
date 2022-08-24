package ir.part.sdk.ara.ui.user.screens.register

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
import ir.part.sdk.ara.domain.user.interacors.GetRegisterRemote
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val getRegisterRemote: GetRegisterRemote,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {

    var registerDone = mutableStateOf(false)

    private val loadingState = ObservableLoadingCounter()

    var loadingErrorState = mutableStateOf<PublicState?>(null)

    private val uiMessageManager = UiMessageManager()

    //error fields
    var errorNationalCode =
        mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))
    var errorEmail = mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))
    var errorPhone = mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))

    //fields
    var userName = mutableStateOf("")
    var email = mutableStateOf("")
    var phone = mutableStateOf("")


    val loadingAndMessageState: StateFlow<PublicState> = combine(
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

    fun setRegister(captchaToken: String, captchaValue: String) {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getRegisterRemote.invoke(
                    GetRegisterRemote.Param(
                        RegisterView(
                            nationalCode = userName.value,
                            email = email.value,
                            phone = phone.value,
                            captchaToken = captchaToken,
                            captchaValue = captchaValue
                        ).toRegisterParam()
                    )
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager
                ) {
                    registerDone.value = it

                }
            }
        }
    }


    fun setErrorNationalCode(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorNationalCode.value = errorList
    }

    fun setErrorEmail(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorEmail.value = errorList
    }

    fun setErrorPhone(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorPhone.value = errorList
    }

    fun isValidationFields(): Boolean {

        setErrorNationalCode(validateWidget(ValidationField.NATIONAL_CODE, userName.value))
        setErrorEmail(validateWidget(ValidationField.EMAIL, email.value))
        setErrorPhone(validateWidget(ValidationField.PHONE, phone.value))

        return validateWidget(
            ValidationField.NATIONAL_CODE, userName.value
        ).second.isNullOrEmpty() &&
                validateWidget(ValidationField.EMAIL, email.value).second.isNullOrEmpty() &&
                validateWidget(ValidationField.PHONE, phone.value).second.isNullOrEmpty()
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                uiMessageManager.clearAllMessage()
            }
        }
    }

}
