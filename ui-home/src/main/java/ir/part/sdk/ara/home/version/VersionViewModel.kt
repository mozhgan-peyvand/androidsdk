package ir.part.sdk.ara.home.version

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.domain.version.entities.VersionDetail
import ir.part.sdk.ara.domain.version.interactors.GetVersionRemote
import ir.part.sdk.ara.home.ui.BuildConfig
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class VersionViewModel @Inject constructor(
    private val getVersion: GetVersionRemote,
    private val exceptionHandler: ExceptionHelper
) : ViewModel() {

    var uiMessageManager = UiMessageManager()
    var loadingState = ObservableLoadingCounter()

    private val filterList = mutableStateOf<List<VersionDetail>?>(null)

    var listHasFilterOrNot = mutableStateOf<Boolean?>(null)

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
        getVersion()
    }

    private fun getVersion() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                clearAllMessage()
                getVersion.invoke(Unit).collectAndChangeLoadingAndMessageStatus(
                    viewModelScope,
                    loadingState,
                    exceptionHandler,
                    uiMessageManager
                ) { versionList ->
                    filterList.value = versionList?.filter { version ->
                        version.versionNumber?.let { versionNumber ->
                            (versionNumber >= BuildConfig.VERSION_CODE)
                        } ?: false
                    }?.filter {
                        it.isForce?.let { isForce ->
                            (isForce)
                        } ?: false
                    }
                    listHasFilterOrNot.value = !filterList.value.isNullOrEmpty()
                }
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