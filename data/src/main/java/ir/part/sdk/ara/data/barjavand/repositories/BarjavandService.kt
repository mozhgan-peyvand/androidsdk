package ir.part.sdk.ara.data.barjavand.repositories

import ir.part.sdk.ara.data.barjavand.entities.*
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.model.PublicResponseData
import retrofit2.Response
import retrofit2.http.*

/**
 * Models the Files API
 */
interface BarjavandService {

    @GET
    suspend fun getApplicantInformation(
        @Url url: String,
        @QueryMap options: Map<String, String>
    ): Response<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoModel>>>>

    @GET
    suspend fun getDocumentOverView(
        @Url url: String,
        @QueryMap options: Map<String, String>
    ): Response<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalDocumentsEntity>>>>

    @POST
    suspend fun rejectRequestByUser(
        @Url url: String,
        @Body documentRejectRequestByUserParamModel: DocumentRejectRequestByUserParamModel
    ): Response<Unit>

    @PATCH
    suspend fun setHasUnReadMessage(
        @Url url: String,
        @Body setHasUnreadMessageParamEntity: SetHasUnreadMessageParamEntity
    ): Response<Unit>

    @GET
    suspend fun getConstant(
        @Url url: String,
        @QueryMap options: Map<String, String>
    ): Response<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoConstantsModel>>>>

    @GET
    suspend fun getUnion(
        @Url url: String,
        @QueryMap options: Map<String, String>
    ): Response<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoClubModel>>>>

}