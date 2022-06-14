package ir.part.sdk.ara.builder.ui.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.launchWithErrorHandler
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.domain.document.entities.DocumentRejectRequestByUserParam
import ir.part.sdk.ara.domain.document.entities.PersonalDocuments
import ir.part.sdk.ara.domain.document.entities.ReadMessage
import ir.part.sdk.ara.domain.document.interacors.*
import ir.part.sdk.ara.domain.tasks.interacors.DoingTaskRemote
import ir.part.sdk.ara.domain.tasks.interacors.DoneTaskRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetBaseStateRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetTaskRemote
import ir.part.sdk.ara.domain.user.entities.ChangePasswordParam
import ir.part.sdk.ara.domain.user.interacors.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


class HomeActivityViewModel @Inject constructor(
    private val getChangePasswordRemote: GetChangePasswordRemote,
    private val getPersonalDocumentRemote: GetPersonalDocumentRemote,
    private val getRejectRequestByUserRemote: GetRejectRequestByUserRemote,
    private val getPersonalInfoConstantsRemote: GetPersonalInfoConstantsRemote,
    private val getPersonalInfoClubRemote: GetPersonalInfoClubRemote,
    private val submitReqValidationRemote: SubmitReqValidationRemote,
    private val doingTaskRemote: DoingTaskRemote,
    private val doneTaskRemote: DoneTaskRemote,
    private val getTaskRemote: GetTaskRemote,
    private val setDisableCustomerFlagRemote: SetDisableCustomerFlagRemote,
    private val getBaseStateRemote: GetBaseStateRemote,
    private val getCaptchaRemote: GetCaptchaRemote,
    private val getLogOutRemote: GetLogOutRemote,
    private val getCurrentUser: GetCurrentUser,
    private val removeUser: RemoveUser,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {

    val logoutState = mutableStateOf(false)
    var personalDocumentState = mutableStateOf<List<PersonalDocuments>>(listOf())
    private val loadingState = ObservableLoadingCounter()

    private val uiMessageManager = UiMessageManager()

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

//    val state: StateFlow<AraState> = combine(
//        getChangePasswordRemote.flow, getPersonalDocumentRemote.flow, getRejectRequestByUserRemote.flow
////        getPersonalInfoConstantsRemote.flow
//        , setDisableCustomerFlagRemote.flow, getBaseStateRemote.flow
//
//    ) { valueResult, valuegetPersonalDocumentRemote, valueRejectRequestByUserRemote
////        , valueGetPersonalInfoConstantsRemote
//        , valueSetDisableCustomerFlagRemote, valueGetBaseStateRemote
//        ->
//        changeLoadingAndMessageStatus(
//            viewModelScope,
//            loadingState,
//            exceptionHelper,
//            uiMessageManager,
//            valueResult, valuegetPersonalDocumentRemote, valueRejectRequestByUserRemote
////            ,valueGetPersonalInfoConstantsRemote
//            , valueSetDisableCustomerFlagRemote, valueGetBaseStateRemote
//        )
//        AraState(
//            PersonalDocumentList = valuegetPersonalDocumentRemote.data,
//            getRejectRequestByUserRemote = valueRejectRequestByUserRemote.data
////                    ,getPersonalInfoConstantsRemote=valueGetPersonalInfoConstantsRemote.data
//            ,
//            setDisableCustomerFlagRemote = valueSetDisableCustomerFlagRemote.data,
//            getBaseStateRemote = valueGetBaseStateRemote.data
//        )
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = AraState.Empty
//    )

    init {
        getPersonalDocumentRemote.flow.collectAndChangeLoadingAndMessageStatus(
            viewModelScope,
            loadingState,
            exceptionHelper,
            uiMessageManager
        ) {
            if (it != null) {
                personalDocumentState.value = it
            }
        }
        getCurrentUser.invoke(Unit, 5L).collectAndChangeLoadingAndMessageStatus(
            viewModelScope,
            loadingState,
            exceptionHelper,
            uiMessageManager
        )
        removeUser.invoke(Unit, 5L).collectAndChangeLoadingAndMessageStatus(
            viewModelScope,
            loadingState,
            exceptionHelper,
            uiMessageManager
        )
        getLogOutRemote.flow.collectAndChangeLoadingAndMessageStatus(
            viewModelScope,
            loadingState,
            exceptionHelper,
            uiMessageManager
        ) {
            logoutState.value = it
        }
    }

    fun getRequestChangePasswordRequest() {
        getChangePasswordRemote(
            GetChangePasswordRemote.Param(
                ChangePasswordParam(
                    "",
                    "part7931",
                    "part7931"
                )
            )
        )
    }

    fun getRequestPersonalDocumentRemote() {
        getPersonalDocumentRemote(
            GetPersonalDocumentRemote.Param(
                "0925562513"
            )
        )
    }

    fun getRequestRejectRequestByUserRemote() {
        // TODO: not built yet
        getRejectRequestByUserRemote(
            GetRejectRequestByUserRemote.Param(
                DocumentRejectRequestByUserParam(fileId = 1223154)
            )
        )
    }

    fun getRequestPersonalInfoConstantsRemote() {
        getPersonalInfoConstantsRemote(Unit)
    }

    fun getPersonalInfoClub() {
        getPersonalInfoClubRemote(
            GetPersonalInfoClubRemote.Param(
                listOf("100", "101", "102", "103", "104", "105")
            )
        )
    }

    fun getTaskRemote() {
        getTaskRemote(Unit)
    }

    fun doingTaskRemote() {
        doingTaskRemote(
            DoingTaskRemote.Param(
                processInstanceId = "5cc43db5-206d-432b-8b1e-2e28acdf1713",
                taskId = "e0ecfa04-8abb-4ccc-9a71-3875c94d52d1"
            )
        )
    }

    fun doneTaskRemote() {
        doneTaskRemote(
            DoneTaskRemote.Param(
                processInstanceId = "5cc43db5-206d-432b-8b1e-2e28acdf1713",
                taskId = "e0ecfa04-8abb-4ccc-9a71-3875c94d52d1"
            )
        )
    }

    fun setDisableCustomerFlagRemote() {
        setDisableCustomerFlagRemote(
            SetDisableCustomerFlagRemote.Param(
                fileIdNew = "123454321-1007",
                readMessage = ReadMessage(false)
            )
        )
    }

    fun getRequestBaseStateRemote() {
        getBaseStateRemote(Unit)
    }

    fun getRequestLogOutRemote() {
        getLogOutRemote(Unit)
    }

    fun getRequestCaptchaRemote() {
        getCaptchaRemote(Unit)
    }

    fun getRequestCurrentUser() {
        getCurrentUser(Unit)
    }

    fun removeCurrentUser() {
        removeUser(Unit)
    }

    fun clearAllMessage() {
        viewModelScope.launchWithErrorHandler {
            uiMessageManager.clearAllMessage()
        }
    }
}