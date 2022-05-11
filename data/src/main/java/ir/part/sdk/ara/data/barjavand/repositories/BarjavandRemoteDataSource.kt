package ir.part.sdk.ara.data.barjavand.repositories

import ir.part.sdk.ara.data.barjavand.entities.BarjavandGetParamsRequest
import ir.part.sdk.ara.data.barjavand.entities.DocumentRejectRequestByUserParamModel
import ir.part.sdk.ara.data.barjavand.entities.ReadMessageEntity
import ir.part.sdk.ara.data.barjavand.entities.SetFlagEntity
import ir.part.sdk.ara.util.api.BaseRemoteDataSource
import ir.part.sdk.ara.util.api.safeApiCall
import javax.inject.Inject

/**
 * Data source class that handles work with File API.
 */

class BarjavandRemoteDataSource @Inject constructor(
    private val service: BarjavandService,
) : BaseRemoteDataSource() {

    suspend fun getDocumentOverView(nationalCode: String) = safeApiCall(
        call = { requestGetDocumentOverView(nationalCode) },
        errorMessage = "Error getting personal Files"
    )


    private suspend fun requestGetDocumentOverView(nationalCode: String) =

        checkApiResult(
            service.getDocumentOverView(
                url = urls.barjavand.getDocumentOverView,
                options = BarjavandGetParamsRequest(
                    schemaName = "document",
                    schemaVersion = "1.0.0",
                    spec = "applicant-overview",
                    tags = arrayOf("userType_applicant", "username_$nationalCode").joinToString(
                        separator = "&"
                    )
                ).toHashMap()
            )
        )

    suspend fun rejectRequestByUser(documentRejectRequestByUserParamModel: DocumentRejectRequestByUserParamModel) =
        safeApiCall(
            call = { requestRejectRequestByUser(documentRejectRequestByUserParamModel) },
            errorMessage = "Error reject request by user"
        )

    private suspend fun requestRejectRequestByUser(documentRejectRequestByUserParamModel: DocumentRejectRequestByUserParamModel) =
        checkApiResult(
            service.rejectRequestByUser(
                url = "",
                documentRejectRequestByUserParamModel
            )
        )

    suspend fun setHasUnReadMessage(fileIdNew: String, readMessageEntity: ReadMessageEntity) =
        safeApiCall(
            call = { requestSetHasUnReadMessage(fileIdNew, readMessageEntity) },
            errorMessage = "Error set customer flag"
        )

    private suspend fun requestSetHasUnReadMessage(
        fileIdNew: String,
        readMessageEntity: ReadMessageEntity
    ) =
        checkApiResult(
            service.setHasUnReadMessage(
                url = urls.barjavand.setHasUnReadMessage,
                SetFlagEntity(
                    schemaName = "document",
                    schemaVersion = "1.0.0",
                    dataId = fileIdNew,
                    data = readMessageEntity
                )
            )
        )

    suspend fun getConstant() = safeApiCall(
        call = { requestGetConstant() },
        errorMessage = "Error getting personalInfo"
    )

    private suspend fun requestGetConstant() =
        checkApiResult(
            service.getConstant(
                url = urls.barjavand.getConstant,
                options = BarjavandGetParamsRequest(
                    schemaName = "constants",
                    schemaVersion = "1.0.0",
                    dataId = "constants"
                ).toHashMap()
            )
        )

    suspend fun getUnion(clubIds: List<String>) =
        safeApiCall(
            call = { requestGetUnion(clubIds) },
            errorMessage = "Error getting personalInfo club"
        )

    private suspend fun requestGetUnion(clubIds: List<String>) = checkApiResult(
        service.getUnion(
            url = urls.barjavand.getUnion,
            unionIds = clubIds.joinToString(",")
        )
    )

}