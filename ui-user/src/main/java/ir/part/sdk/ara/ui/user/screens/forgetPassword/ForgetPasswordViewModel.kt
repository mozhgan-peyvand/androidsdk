package ir.part.sdk.ara.ui.user.screens.forgetPassword

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
import ir.part.sdk.ara.domain.user.entities.ForgetPasswordParam
import ir.part.sdk.ara.domain.user.interacors.GetForgetPasswordRemote
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(
    var getForgetPasswordRemote: GetForgetPasswordRemote,
    var exceptionHelper: ExceptionHelper
) : ViewModel() {
    var isRecoveredPassword = mutableStateOf(false)
    var uiMessageManager = UiMessageManager()
    var loadingState = ObservableLoadingCounter()
    var loadingErrorState = mutableStateOf<PublicState?>(null)

    //errorField
    var errorValueNationalCode = mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))

    //field
    var nationalCode = mutableStateOf("")

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


    fun setRecoverPassword(nationalCode: String, captchaValue: String?, captchaToken: String?) {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getForgetPasswordRemote.invoke(
                    GetForgetPasswordRemote.Param(
                        ForgetPasswordParam(
                            nationalCode,
                            captchaValue,
                            captchaToken
                        )
                    )
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager
                ) {
                    if (it) {
                        isRecoveredPassword.value = true
                    }
                }
            }
        }
    }

    fun setErrorNationalCode(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorValueNationalCode.value = errorList
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                uiMessageManager.clearAllMessage()
            }
        }
    }

}