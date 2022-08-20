package ir.part.sdk.ara.ui.document.submitDocument

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.*
import ir.part.sdk.ara.domain.document.interacors.GetApplicantInformationRemote
import ir.part.sdk.ara.domain.document.interacors.GetPersonalInfoClubRemote
import ir.part.sdk.ara.domain.document.interacors.SubmitReqValidationRemote
import ir.part.sdk.ara.ui.document.submitDocument.mapper.toPersonalInfoClubView
import ir.part.sdk.ara.ui.document.submitDocument.mapper.toPersonalInfoSubmitDocumentView
import ir.part.sdk.ara.ui.document.submitDocument.mapper.toSubmitResponseValidationView
import ir.part.sdk.ara.ui.document.submitDocument.model.SubmitDocumentView
import ir.part.sdk.ara.ui.document.submitDocument.model.SubmitRequestView
import ir.part.sdk.ara.ui.document.submitDocument.model.SubmitResponseValidationView
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubmitDocumentViewModel @Inject constructor(
    private val getApplicantInformationRemote: GetApplicantInformationRemote,
    private val getUnionRemote: GetPersonalInfoClubRemote,
    private val submitReqValidationRemote: SubmitReqValidationRemote,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {

    private val uiMessageManager = UiMessageManager()

    private val loadingState = ObservableLoadingCounter()

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

    val state: StateFlow<SubmitDocumentView> = combine(
        getUnionRemote.flow,
        getApplicantInformationRemote.flow
    ) { getUnion, personalInfoSubmitDocument ->
        changeLoadingAndMessageStatus(
            viewModelScope,
            loadingState,
            exceptionHelper,
            uiMessageManager,
            getUnion, personalInfoSubmitDocument
        )
        SubmitDocumentView(
            personalInfoSubmitDocumentView = personalInfoSubmitDocument.data?.toPersonalInfoSubmitDocumentView(),
            personalInfoClubView = getUnion.data?.map { it.toPersonalInfoClubView() }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SubmitDocumentView.Empty
    )

    init {
        getPersonalInfo()
        onSelectedClub()
    }

    fun submitReqValidation(unionId: String, onResponse: (SubmitResponseValidationView?) -> Unit) {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                submitReqValidationRemote.invoke(
                    SubmitReqValidationRemote.Param(
                        SubmitRequestView(unionId = unionId.toInt())
                            .toSubmitReqValidationParam()
                    )
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager
                ) {
                    onResponse(it?.toSubmitResponseValidationView())
                }
            }
        }
    }

    private fun getPersonalInfo() {
        viewModelScope.launch {
            getApplicantInformationRemote(Unit)
        }
    }

    private fun onSelectedClub() {
        viewModelScope.launch {
            getUnionRemote(Unit)
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