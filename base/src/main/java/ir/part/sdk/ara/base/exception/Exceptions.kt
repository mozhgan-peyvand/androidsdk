package ir.part.sdk.ara.base.exception


import ir.part.sdk.ara.base.model.Message
import java.util.*

sealed class Exceptions {

    data class TimeoutCancellationException(
        val id: String = UUID.randomUUID().mostSignificantBits.toString(),
        val message: String = "Timeout Cancellation Exception",
        val cause: Throwable
    ) :

        Exceptions()

    data class IOException(
        val id: String = UUID.randomUUID().mostSignificantBits.toString(),
        val message: String = "IO Exception",
        val cause: Throwable
    ) :
        Exceptions()


    data class NetworkConnectionException(
        val id: String = UUID.randomUUID().mostSignificantBits.toString(),
        val message: String = "Network Connection Error"
    ) :
        Exceptions()


    data class RemoteDataSourceException(
        val id: String = UUID.randomUUID().mostSignificantBits.toString(),
        val code: String? = null,
        val message: Message? = null
    ) :
        Exceptions()


    data class RemoteDataSourceExceptionUser(
        val id: String = UUID.randomUUID().mostSignificantBits.toString(),
        val message: Message?
    ) :
        Exceptions()


    data class LocalDataSourceException(
        val id: String = UUID.randomUUID().mostSignificantBits.toString(),
        val message: String = "Local Data Source Error",
        val cause: Throwable? = null
    ) :
        Exceptions()


    data class ValidationException<T : Any>(val errors: T) : Exceptions()
}
