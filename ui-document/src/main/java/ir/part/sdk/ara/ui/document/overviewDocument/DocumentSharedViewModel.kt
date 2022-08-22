package ir.part.sdk.ara.ui.document.overviewDocument

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.DateUtil
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.*
import ir.part.sdk.ara.domain.document.entities.RemoveDocumentParam
import ir.part.sdk.ara.domain.document.interacors.*
import ir.part.sdk.ara.ui.document.overviewDocument.model.OverviewDocumentView
import ir.part.sdk.ara.ui.document.submitDocument.mapper.toPersonalDocumentsView
import ir.part.sdk.ara.ui.document.submitDocument.mapper.toPersonalInfoConstantsView
import ir.part.sdk.ara.ui.document.submitDocument.mapper.toPersonalInfoSubmitDocumentView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalDocumentsView
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DocumentSharedViewModel @Inject constructor(
    private val getApplicantInformationRemote: GetApplicantInformationRemote,
    private val getPersonalDocumentRemote: GetPersonalDocumentRemote,
    private val setRemoveDocumentRemote: SetRemoveDocumentRemote,
    private val getPersonalInfoConstantsRemote: GetPersonalInfoConstantsRemote,
    private val setHasUnreadMessageRemote: SetHasUnreadMessageRemote,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {

    var documents: MutableStateFlow<List<PersonalDocumentsView>> = MutableStateFlow(listOf())

    var itemPersonalDocument = mutableStateOf<PersonalDocumentsView?>(null)
    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()
    private val dateUtil = DateUtil()


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

    val overviewDocumentState: StateFlow<OverviewDocumentView> = combine(
        getPersonalInfoConstantsRemote.flow,
        getApplicantInformationRemote.flow
    ) { personalInfoConstants, personalInfoSubmitDocument ->
        changeLoadingAndMessageStatus(
            viewModelScope,
            loadingState,
            exceptionHelper,
            uiMessageManager,
            personalInfoConstants, personalInfoSubmitDocument
        )
        OverviewDocumentView(
            personalInfoConstantsItem = personalInfoConstants.data?.toPersonalInfoConstantsView(),
            personalInfoSubmitDocumentView = personalInfoSubmitDocument.data?.toPersonalInfoSubmitDocumentView(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OverviewDocumentView.Empty
    )


    init {
        getPersonalInfo()
        getPersonalDocument()
        getPersonalInfoConstants()
    }

    private fun getPersonalInfo() {
        viewModelScope.launch {
            getApplicantInformationRemote(Unit)
        }
    }

    private fun getPersonalDocument() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getPersonalDocumentRemote.invoke(
                    Unit
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager
                ) { list ->
                    documents.value = list?.map {
                        it.toPersonalDocumentsView(dateUtil)
                    } ?: listOf()
                }
            }
        }

    }

    private fun getPersonalInfoConstants() {
        viewModelScope.launch {
            getPersonalInfoConstantsRemote(Unit)
        }
    }


    fun removeDocument(documentId: Long, onRemoveDocumentResponse: () -> Unit) {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                setRemoveDocumentRemote.invoke(
                    SetRemoveDocumentRemote.Param(RemoveDocumentParam(documentId))
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager
                ) {
                    onRemoveDocumentResponse()
                }
            }
        }
    }

    fun setHasUnreadMessage(
        hasUnreadMessage: Boolean,
        documentId: String,
        onResponse: (Boolean?) -> Unit
    ) {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                setHasUnreadMessageRemote.invoke(
                    SetHasUnreadMessageRemote.Param(
                        documentId,
                        hasUnreadMessage
                    )
                ).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHelper,
                    uiMessageManager
                ) {
                    onResponse(it)
                }
            }
        }
    }

    fun refreshFileListScreenRequest() {
        getPersonalDocument()
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                uiMessageManager.clearAllMessage()
            }
        }
    }
}