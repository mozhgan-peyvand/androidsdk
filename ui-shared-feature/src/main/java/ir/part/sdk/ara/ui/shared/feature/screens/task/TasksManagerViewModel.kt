package ir.part.sdk.ara.ui.shared.feature.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.base.util.TaskStatus
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.domain.document.interacors.GetPersonalDocumentRemote
import ir.part.sdk.ara.domain.tasks.interacors.*
import ir.part.sdk.ara.domain.user.interacors.GetNationalCode
import ir.part.sdk.ara.domain.user.interacors.GetToken
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

open class TasksManagerViewModel @Inject constructor(
    private val doingRemote: DoingRemote,
    private val doneRemote: DoneRemote,
    private val getDoingTasksRemote: GetDoingTasksRemote,
    private val getBaseStateRemote: GetBaseStateRemote,
    private val getPersonalDocumentRemote: GetPersonalDocumentRemote,
    private val getToken: GetToken,
    private val getNationalCode: GetNationalCode,
    private val getTaskInstanceId: GetTaskInstanceId,
    private val getProcessInstanceId: GetProcessInstanceId,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {


    val currentTask: MutableSharedFlow<TasksName> = MutableSharedFlow(replay = 0)

    var getDoingTaskName = TasksName.NO_TASK

    private var getDoingTaskStatus: MutableSharedFlow<TaskStatus> = MutableSharedFlow(
        replay = 0
    )

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

    init {

        viewModelScope.launch {
            getDoingTaskStatus.collect {
                when (it) {
                    TaskStatus.DOING -> getBaseState()
                    TaskStatus.DONE -> {}
                    TaskStatus.UNDONE -> doing()
                    TaskStatus.NO_STATUS -> {
                    }
                }
            }
        }
    }

    fun getBaseState(loginState: Boolean = false) {
        viewModelScope.launch {
            clearAllMessage()
            getBaseStateRemote.invoke(
                Unit
            ).collectAndChangeLoadingAndMessageStatus(
                viewModelScope,
                loadingState,
                exceptionHelper,
                uiMessageManager
            ) {
                if (loginState) {
                    getDoingTasks()
                } else {
                    viewModelScope.launch {
                        when (it?.status ?: TaskStatus.NO_STATUS) {
                            TaskStatus.DOING -> {
                                currentTask.emit(getDoingTaskName)
                            }
                            TaskStatus.DONE -> {}
                            TaskStatus.UNDONE -> done()
                            TaskStatus.NO_STATUS -> {}
                        }
                    }
                }

            }
        }
    }

    private fun getDoingTasks() {
        viewModelScope.launch {
            clearAllMessage()
            getDoingTasksRemote.invoke(
                Unit
            ).collectAndChangeLoadingAndMessageStatus(
                viewModelScope,
                loadingState,
                exceptionHelper,
                uiMessageManager
            ) {
                if (it?.taskId.isNullOrEmpty()) {
                    getDoingTasks()
                } else {
                    viewModelScope.launch {
                        getDoingTaskStatus.emit(it?.status ?: TaskStatus.NO_STATUS)
                    }
                    getDoingTaskName = it?.taskName ?: TasksName.NO_TASK
                }
            }
        }
    }

    private fun doing() {
        viewModelScope.launch {
            clearAllMessage()
            doingRemote.invoke(
                Unit
            ).collectAndChangeLoadingAndMessageStatus(
                viewModelScope,
                loadingState,
                exceptionHelper,
                uiMessageManager
            ) {
                viewModelScope.launch {
                    currentTask.emit(getDoingTaskName)
                }
            }
        }
    }

    // use done in successes of each task
    fun done() {
        viewModelScope.launch {
            clearAllMessage()
            doneRemote.invoke(
                Unit
            ).collectAndChangeLoadingAndMessageStatus(
                viewModelScope,
                loadingState,
                exceptionHelper,
                uiMessageManager
            ) {
                getDoingTasks()
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

    fun checkIfUserHasDoc(onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            clearAllMessage()
            getPersonalDocumentRemote.invoke(
                Unit
            ).collectAndChangeLoadingAndMessageStatus(
                viewModelScope,
                loadingState,
                exceptionHelper,
                uiMessageManager
            ) { list ->
                if (list.isNullOrEmpty()) {
                    onSuccess(false)
                } else {
                    onSuccess(true)
                }
            }
        }
    }

    fun getTokenLocal(): String = getToken()

    fun getTaskInstanceIdLocal(): String = getTaskInstanceId()

    fun getNationalCodeLocal(): String = getNationalCode()

    fun getProcessInstanceIdLocal(): String = getProcessInstanceId()
}