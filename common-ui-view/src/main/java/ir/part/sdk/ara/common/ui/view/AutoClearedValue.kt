package ir.part.sdk.ara.common.ui.view

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Ignore Exception thrown in AutoClearedValue for access to after it becomes null
 * */
fun CoroutineScope.launchWithErrorHandler(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) = run {
    val errorHandler = CoroutineExceptionHandler { _, error ->
        when (error) {
            is IllegalStateException -> {
                Timber.tag("AutoClearedValueTAG")
                    .d("Tried to access value after fragment view is destroyed")
            }
            else -> throw error
        }
    }

    launch(
        context = context + errorHandler,
        block = block
    )
}