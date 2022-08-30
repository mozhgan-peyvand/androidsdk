package ir.part.sdk.ara.ui.menu.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.domain.user.interacors.GetNationalCode
import ir.part.sdk.ara.domain.user.interacors.GetPhoneNumber
import ir.part.sdk.ara.domain.user.interacors.Logout
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val logout: Logout,
    private var exceptionHelper: ExceptionHelper,
    getNationalCode: GetNationalCode,
    getPhoneNumber: GetPhoneNumber,
) : ViewModel() {

    var uiMessageManager = UiMessageManager()
    var loadingState = ObservableLoadingCounter()

    val successfulLogout = mutableStateOf<Boolean?>(null)

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


    val phoneNumber = mutableStateOf("")
    val nationalCode = mutableStateOf("")

    init {
        nationalCode.value = getNationalCode()
        phoneNumber.value = getPhoneNumber()
    }


    fun logout() {
        if (loadingState.count.toInt() == 0) {
            clearAllMessage()

            logout(Unit).collectAndChangeLoadingAndMessageStatus(
                coroutineScope = viewModelScope,
                counter = loadingState,
                exceptionHelper = exceptionHelper,
                uiMessageManager = uiMessageManager
            ) { successful ->
                successfulLogout.value = successful
            }
        }
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                uiMessageManager.clearAllMessage()
            }
        }
    }
}