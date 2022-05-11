package ir.part.sdk.ara.util.api

import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.util.NetworkHandler
import javax.inject.Inject

class RequestExecutor @Inject constructor(private val networkHandler: NetworkHandler) {

    suspend fun <T, R> execute(
        apiEventListener: InvokeStatus.ApiEventListener<T, R>
    ): InvokeStatus<R> =
        if (networkHandler.hasNetworkConnection()) {
            val result = apiEventListener.onRequestCall()
            result.switchData(apiEventListener)
        } else {
            apiEventListener.onErrorOccurred(Exceptions.NetworkConnectionException())
        }
}

