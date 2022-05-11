package ir.part.sdk.ara.data.barjavand.repositories

import ir.part.sdk.ara.data.barjavand.entities.*
import ir.part.sdk.ara.model.PublicResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Models the Files API
 */
interface BarjavandService {

//    getDocumentOverView
    @GET
    suspend fun getDocumentOverView(
        @Url url: String,
        @QueryMap options: Map<String, String>
    ): Response<PublicResponse<List<PersonalDocumentsEntity>>>

    // TODO: set url
    @POST
    suspend fun rejectRequestByUser(
        @Url url: String,
        @Body documentRejectRequestByUserParamModel: DocumentRejectRequestByUserParamModel
    ): Response<Unit>

    @PUT
    suspend fun setHasUnReadMessage(
        @Url url: String,
        @Body setFlagEntity: SetFlagEntity
    ): Response<Unit>

    @GET
    suspend fun getConstant(
        @Url url: String,
        @QueryMap options: Map<String, String>
    ): Response<PublicResponse<PersonalInfoConstantsModel>>

    @GET
    suspend fun getUnion(
        @Url url: String,
        @Query("unionIds") unionIds: String
    ): Response<PublicResponse<List<PersonalInfoClubModel>>>

}