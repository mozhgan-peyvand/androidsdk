package ir.part.sdk.ara.common.ui.view

import android.content.Context
import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.common.ui.view.api.UiMessage
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ExceptionHelper @Inject constructor(val context: Context) {

    enum class ErrorCodes {
        NoInternetConnection
    }

    fun getError(exception: Exceptions): UiMessage {

        val exceptionString = exception.toString()
        var errorMessage: String?
        val icon: Int
        var code: String = UUID.randomUUID().mostSignificantBits.toString()
        var id: String = UUID.randomUUID().mostSignificantBits.toString()
        Timber.d(exceptionString)

        when (exception) {
            is Exceptions.IOException -> {

                errorMessage = if (BuildConfig.DEBUG)
                    "${context.resources.getString(R.string.ara_msg_server_error)}\n$exceptionString"
                else
                    context.resources.getString(R.string.ara_msg_server_error)

                icon = R.drawable.ara_ic_connection_error
                id = exception.id
            }
            is Exceptions.NetworkConnectionException -> {

                errorMessage = if (BuildConfig.DEBUG)
                    "${context.resources.getString(R.string.ara_msg_connection_error)}\n$exceptionString"
                else
                    context.resources.getString(R.string.ara_msg_connection_error)

                icon = R.drawable.ara_ic_connection_error
                code = ErrorCodes.NoInternetConnection.name
                id = exception.id
            }
            is Exceptions.LocalDataSourceException -> {

                errorMessage = if (BuildConfig.DEBUG)
                    "${context.resources.getString(R.string.ara_msg_general_error)}\n$exceptionString"
                else
                    context.resources.getString(R.string.ara_msg_general_error)

                icon = R.drawable.ara_ic_general_error
                id = exception.id
            }
            is Exceptions.RemoteDataSourceException -> {

                errorMessage = (if (exception.message == null && exception.code.isNullOrEmpty()) {
                    context.resources.getString(R.string.ara_msg_server_error_not_thing)
                } else {
                    exception.message?.fa
                })?.let {
                    if (BuildConfig.DEBUG)
                        "${it}\n$exceptionString"
                    else
                        it
                }
                icon = R.drawable.ara_ic_connection_error
                code = exception.code ?: UUID.randomUUID().mostSignificantBits.toString()
                id = exception.id
            }
            is Exceptions.RemoteDataSourceExceptionUser -> {
                errorMessage = if (BuildConfig.DEBUG)
                    exception.message?.fa
                else
                    "${exception.message?.fa}"

                icon = R.drawable.ara_ic_connection_error
                id = exception.id
            }
            else -> {
                errorMessage = if (BuildConfig.DEBUG)
                    "${context.resources.getString(R.string.ara_msg_general_error)}\n$exceptionString"
                else
                    context.resources.getString(R.string.ara_msg_general_error)

                icon = R.drawable.ara_ic_general_error
            }
        }
        errorMessage = errorMessage
            ?: if (BuildConfig.DEBUG) "${context.resources.getString(R.string.ara_msg_general_error)}\n$exceptionString" else context.resources.getString(
                R.string.ara_msg_general_error
            )

        return UiMessage(id = id, message = errorMessage, icon = icon, code = code)

    }

}

