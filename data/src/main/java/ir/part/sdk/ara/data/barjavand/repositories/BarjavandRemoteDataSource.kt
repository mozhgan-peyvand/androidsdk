package ir.part.sdk.ara.data.barjavand.repositories

import ir.part.sdk.ara.data.barjavand.entities.*
import ir.part.sdk.ara.util.api.BaseRemoteDataSource
import ir.part.sdk.ara.util.api.safeApiCall
import org.json.JSONObject
import javax.inject.Inject

/**
 * Data source class that handles work with File API.
 */

class BarjavandRemoteDataSource @Inject constructor(
    private val service: BarjavandService,
) : BaseRemoteDataSource() {

    suspend fun getApplicantInformation(nationalCode: String) = safeApiCall(
        call = { requestApplicantInformation(nationalCode) },
        errorMessage = "Error getting personal Files"
    )

    private suspend fun requestApplicantInformation(nationalCode: String) =
        checkApiResult(
            service.getApplicantInformation(
                url = urls.barjavand.getApplicationInformation,
                options = BarjavandGetParamsRequest(
                    schemaName = "applicantInformation",
                    schemaVersion = "1.0.1",
                    id = nationalCode
                ).toHashMap()
            )
        )

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
                    tags = JSONObject().put("applicantUsername", nationalCode).toString()
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

    suspend fun setHasUnReadMessage(
        documentId: String,
        hasUnreadMessage: Boolean,
        nationalCode: String
    ) =
        safeApiCall(
            call = { requestSetHasUnReadMessage(documentId, hasUnreadMessage, nationalCode) },
            errorMessage = "Error set customer flag"
        )

    private suspend fun requestSetHasUnReadMessage(
        documentId: String,
        hasUnreadMessage: Boolean,
        nationalCode: String
    ) =
        checkApiResult(
            service.setHasUnReadMessage(
                url = urls.barjavand.setHasUnReadMessage,
                setHasUnreadMessageParamEntity = SetHasUnreadMessageParamEntity(
                    schema = Schema(
                        name = "document",
                        version = "1.0.0"
                    ),
                    id = "$nationalCode-$documentId",
                    data = SetHasUnreadMessageParamEntityData(
                        hasUnreadMessage
                    )
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
                    id = "constants"
                ).toHashMap()
            )
        )

    suspend fun getUnion() =
        safeApiCall(
            call = { requestGetUnion() },
            errorMessage = "Error getting personalInfo club"
        )

    private suspend fun requestGetUnion() =
        checkApiResult(
            service.getUnion(
                url = urls.barjavand.getUnion,
                options = BarjavandGetParamsRequest(
                    schemaName = "unions",
                    schemaVersion = "1.0.0"
                ).toHashMap()
            )
        )
}