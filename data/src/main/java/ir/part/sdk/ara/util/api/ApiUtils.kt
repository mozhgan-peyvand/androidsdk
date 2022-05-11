package ir.part.sdk.ara.util.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.base.model.*
import ir.part.sdk.ara.di.ApplicationJsonAdapterFactory
import org.json.JSONObject

/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [InvokeStatus.Error] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(
    call: suspend () -> InvokeStatus<T>,
    errorMessage: String
): InvokeStatus<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        InvokeError(Exceptions.IOException(message = errorMessage, cause = e))
    }
}

fun errorParser(errorBody: String?): PublicError {
    try {
        return if (errorBody != null) {

            val moshi = Moshi.Builder()
                .add(ApplicationJsonAdapterFactory)
                .build()
            val adapter = moshi.adapter<Error<Message>>(
                Types.newParameterizedType(
                    Error::class.java,
                    Message::class.java
                )
            )
            val errorBodyObject = JSONObject(errorBody)

            val errorString = errorBodyObject.getJSONObject("data").toString()

            val araError = adapter.fromJson(errorString)

            PublicError(
                item = Error(
                    message = Message(
                        fa = araError?.message?.fa,
                        en = araError?.message?.en ?: ""
                    )
                )
            )
        } else {
            PublicError()
        }
    } catch (e: Exception) {
        return PublicError()
    }
}
