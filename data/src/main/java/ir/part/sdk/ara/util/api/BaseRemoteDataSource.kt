package ir.part.sdk.ara.util.api

import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.base.model.InvokeError
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.model.InvokeSuccess
import retrofit2.Response
import javax.inject.Inject

open class BaseRemoteDataSource {
    @Inject
    lateinit var urls: ApiUrlHelper
    protected fun <T> checkApiResult(
        response: Response<T>
    ): InvokeStatus<T> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null)
                return InvokeSuccess(body)
        }
        val error = errorParser(
            response.errorBody()?.string()
        )

        return InvokeError(
            Exceptions.RemoteDataSourceException(
                code = error.code?.toString(),
                message = error.item?.message
            )
        )
    }
}