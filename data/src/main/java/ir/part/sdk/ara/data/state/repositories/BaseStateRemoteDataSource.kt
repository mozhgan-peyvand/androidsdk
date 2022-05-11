package ir.part.sdk.ara.data.state.repositories

import ir.part.sdk.ara.data.state.entites.BaseStateEntity
import ir.part.sdk.ara.util.api.BaseRemoteDataSource
import ir.part.sdk.ara.util.api.safeApiCall
import javax.inject.Inject

class BaseStateRemoteDataSource @Inject constructor(private val service: BaseStateService) :
    BaseRemoteDataSource() {

    suspend fun baseState(nationalCode: String) = safeApiCall(
        call = { requestBaseState(nationalCode) },
        errorMessage = "Error getting baseState"
    )

    private suspend fun requestBaseState(nationalCode: String) = checkApiResult(
        service.baseStateObject(
            url = urls.stateService.getBaseStateObject,

            BaseStateEntity(
                searchToken = "processType_base&username_$nationalCode",
                include = listOf("id", "keys", "body"),
                sort = true,
                sortMethod = "asc",
                sortKeyIndex = 0,
                pagination = true,
                pageSize = 2,
                pageNumber = 1
            )
        )
    )


}