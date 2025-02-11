package ir.part.sdk.ara.ui.shared.feature.screens.captcha

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
import ir.part.sdk.ara.domain.user.interacors.GetCaptchaRemote
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CaptchaViewModel @Inject constructor(
    private var getCaptchaRemote: GetCaptchaRemote,
    private var exceptionHelper: ExceptionHelper
) : ViewModel() {

    var uiMessageManager = UiMessageManager()
    var loadingState = ObservableLoadingCounter()
    var errorCaptchaValue =
        mutableStateOf(Pair(ValidationField.CAPTCHA, listOf<ValidationResult>()))

    //errorField
    var loadingErrorState = mutableStateOf<PublicState?>(null)

    //field
    var captchaValue = mutableStateOf("")
    var captchaViewState = mutableStateOf<CaptchaViewState?>(null)


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

    init {
        refreshCaptcha()
    }

    fun refreshCaptcha() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getCaptchaRemote.invoke(Unit).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager,
                    onRetry = {
                        refreshCaptcha()
                    }
                ) {
                    captchaViewState.value = it?.toCaptchaView()
                }
            }
        }
    }

    fun setError(errorList: Pair<ValidationField, List<ValidationResult>>) {
        errorCaptchaValue.value = errorList
    }

    fun clearAllMessage() {
        viewModelScope.launch {
            uiMessageManager.clearAllMessage()
        }
    }
}