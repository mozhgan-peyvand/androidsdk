package ir.part.sdk.ara.ui.menu.screens.rahyar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.changeLoadingAndMessageStatus
import ir.part.sdk.ara.domain.document.interacors.GetPersonalInfoConstantsRemote
import ir.part.sdk.ara.domain.menu.interactors.GetRahyarRemote
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class RahyarViewModel @Inject constructor(
    private val getRahyarRemote: GetRahyarRemote,
    private val getConstantRemote: GetPersonalInfoConstantsRemote,
    private val exceptionHandler: ExceptionHelper
) : ViewModel() {

    init {
        getRahyar()
        getConstants()
    }

    var uiMessageManager = UiMessageManager()
    var loadingState = ObservableLoadingCounter()
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

    val getRahyarAndConstantState: StateFlow<RahyarAndConstantResult> = combine(
        getConstantRemote.flow,
        getRahyarRemote.flow
    ) { constant, rahyar ->
        changeLoadingAndMessageStatus(
            viewModelScope,
            loadingState,
            exceptionHandler,
            uiMessageManager,
            constant, rahyar
        )
        RahyarAndConstantResult(
            constant = constant.data?.toPersonalInfoConstantsView(),
            rahyar = rahyar.data?.map { it.toRahyarView() }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RahyarAndConstantResult.Empty
    )

    private fun getConstants() {
        viewModelScope.launch {
            getConstantRemote(Unit)
        }
    }

    fun getRahyar(province: String? = null) {
        viewModelScope.launch {
            getRahyarRemote(GetRahyarRemote.Param(province = province))
        }
    }
}