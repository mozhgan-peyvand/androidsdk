package ir.part.sdk.ara.common.ui.view.api

import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.base.model.InvokeError
import ir.part.sdk.ara.base.model.InvokeStarted
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.model.InvokeSuccess
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.launchWithErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

class ObservableLoadingCounter {
    val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

fun <T> Flow<InvokeStatus<T>>.collectAndChangeLoadingAndMessageStatus(
    coroutineScope: CoroutineScope,
    counter: ObservableLoadingCounter,
    exceptionHelper: ExceptionHelper,
    uiMessageManager: UiMessageManager? = null,
    onRetry: (() -> Unit)? = null,
    returnData: ((value: T) -> Unit)? = null
) = coroutineScope.launchWithErrorHandler {
    collect { status ->
        when (status) {
            is InvokeStarted -> counter.addLoader()
            is InvokeSuccess<T> -> {
                returnData?.invoke(status.data)
                delay(100)
                counter.removeLoader()
            }
            is InvokeError -> {
                counter.removeLoader()
                changeLoadingAndMessageOnInvokeError(
                    counter,
                    exceptionHelper,
                    uiMessageManager,
                    status.exception,
                    onRetry
                )
            }
        }
    }
}

fun changeLoadingAndMessageStatus(
    coroutineScope: CoroutineScope,
    counter: ObservableLoadingCounter,
    exceptionHelper: ExceptionHelper,
    uiMessageManager: UiMessageManager? = null,
    vararg statuses: InvokeStatus<*>,
    onRetry: () -> Unit = {}
) = coroutineScope.launchWithErrorHandler(Dispatchers.IO) {
    var emitFirstMessage = false
    statuses.forEach { status ->
        when (status) {
            is InvokeStarted -> counter.addLoader()
            is InvokeSuccess -> counter.removeLoader()
            is InvokeError -> {
                counter.removeLoader()
                if (statuses.filterIsInstance<InvokeStarted<*>>().isEmpty()) {
                    if (!emitFirstMessage) {
                        changeLoadingAndMessageOnInvokeError(
                            counter,
                            exceptionHelper,
                            uiMessageManager,
                            status.exception,
                            onRetry
                        )
                    } else {
                        emitFirstMessage = true
                    }
                }
            }
        }
    }
}

private suspend fun changeLoadingAndMessageOnInvokeError(
    counter: ObservableLoadingCounter,
    exceptionHelper: ExceptionHelper,
    uiMessageManager: UiMessageManager? = null,
    exception: Exceptions,
    onRetry: (() -> Unit)? = null
) {
    if (counter.count.get() <= 0) {
        val uiMessage = exceptionHelper.getError(exception)
        uiMessageManager?.emitMessage(
            UiMessage(
                id = uiMessage.id,
                message = uiMessage.message,
                code = uiMessage.code,
                icon = uiMessage.icon,
                onRetry = onRetry
            )
        )
    }
}