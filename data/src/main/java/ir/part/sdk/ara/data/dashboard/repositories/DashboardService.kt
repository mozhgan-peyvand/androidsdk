package ir.part.sdk.ara.data.dashboard.repositories

import ir.part.sdk.ara.data.dashboard.entites.*
import ir.part.sdk.ara.model.PublicResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Models the Files API
 */
interface DashboardService {

    @POST
    suspend fun newDocumentProcess(
        @Url url: String,
        @Body submitReqValidationParamModel: SubmitReqValidationParamModel
    ): Response<PublicResponse<SubmitResponseValidationEntity>>

    @GET
    suspend fun getTask(
        @Url url: String,
        // todo : check if should be static or not
        @Query("dashboardId") dashboardId: String = "aa7f32e1-ff21-4b5b-92e1-c37da3499a43",
        @Query("processInstanceId") processInstanceId: String
    ): Response<PublicResponse<List<TaskResponse>>>


    @PUT
    suspend fun requestDoingTask(
        @Url url: String,
        // todo : check if should be static or not
        @Query("dashboardId") dashboardId: String = "aa7f32e1-ff21-4b5b-92e1-c37da3499a43",
        @Body doingEntity: DoingEntity
    ): Response<PublicResponse<String>>


    @PUT
    suspend fun requestDoneTask(
        @Url url: String,
        @Body doneEntity: DoneEntity
    ): Response<PublicResponse<DoneResponse>>

}