package ir.part.sdk.ara.ui.user.forgetPasswordVerification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.domain.user.interacors.GetForgetPasswordVerificationRemote
import ir.part.sdk.ara.ui.user.util.validation.ValidationField
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgetPasswordVerificationViewModel @Inject constructor(
    var getForgetPasswordVerificationRemote: GetForgetPasswordVerificationRemote,
    var exceptionHelper: ExceptionHelper,
) : ViewModel() {

    var isSendCode = mutableStateOf(false)
    var loadingState = ObservableLoadingCounter()
    var uiMessageManager = UiMessageManager()
    var loadingErrorState = mutableStateOf<PublicState?>(null)

    //errorField
    var errorValuePassword =
        mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))

    //field
    var codeValue = mutableStateOf("")

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


    fun sendCode(nationalCode: String, verificationCode: String) {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getForgetPasswordVerificationRemote.invoke(
                    GetForgetPasswordVerificationRemote.Param(
                        nationalCode = nationalCode,
                        verificationCode
                    )
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager
                ) {
                    if (it) {
                        isSendCode.value = true
                    }
                }
            }
        }
    }

    fun setErrorPassword(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorValuePassword.value = errorList
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                uiMessageManager.clearAllMessage()
            }
        }
    }
}