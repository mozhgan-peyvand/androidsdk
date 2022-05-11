package ir.part.sdk.ara.base.model


import ir.part.sdk.ara.base.exception.Exceptions

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class InvokeStatus<T>(open val data: T? = null) {

    interface ApiEventListener<T, R> {
        suspend fun onRequestCall(): InvokeStatus<T>

        suspend fun onSuccessfulResult(data: R): InvokeSuccess<R> = InvokeSuccess(data)

        suspend fun onErrorOccurred(e: Exceptions): InvokeError<R> = InvokeError(e)

        fun onConvertResult(data: T): R
    }

    override fun toString(): String {
        return when (this) {
            is InvokeStarted -> "InvokeStarted"
            is InvokeSuccess<*> -> "Success[data=$data]"
            is InvokeError<*> -> "Error[exception=$exception]"
        }
    }

    fun <S> switchData(converter: (T?) -> S): InvokeStatus<S> {
        return when (this) {
            is InvokeStarted -> InvokeStarted()
            is InvokeSuccess -> InvokeSuccess(converter(data))
            is InvokeError -> InvokeError(exception)
        }
    }

    suspend fun <R> switchData(
        apiEventListener: ApiEventListener<T, R>
    ): InvokeStatus<R> {
        return when (this) {
            is InvokeStarted -> InvokeStarted()
            is InvokeSuccess -> apiEventListener.onSuccessfulResult(
                apiEventListener.onConvertResult(
                    data
                )
            )
            is InvokeError -> apiEventListener.onErrorOccurred(exception)
        }
    }
}


data class InvokeStarted<T>(override var data: T? = null) : InvokeStatus<T>()

data class InvokeSuccess<T>(override var data: T) : InvokeStatus<T>()


data class InvokeError<T>(val exception: Exceptions) : InvokeStatus<T>()