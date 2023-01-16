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
        @Header("username") username: String,
        @Header("process-instance-id") processInstanceId: String,
        @Header("task-instance-id") taskInstanceId: String,
        @Body submitReqValidationParamModel: SubmitReqValidationParamModel
    ): Response<PublicResponse<SubmitResponseValidationEntity>>

    @PUT
    suspend fun doing(
        @Url url: String,
        @Header("task-instance-id") taskInstanceId: String,
        @Header("process-instance-id") processInstanceId: String,
        @Body doingEntity: DoingEntity
    ): Response<PublicResponse<DoingResponse>>

    @PUT
    suspend fun done(
        @Url url: String,
        @Header("task-instance-id") taskInstanceId: String,
        @Header("process-instance-id") processInstanceId: String,
        @Body doneEntity: DoneEntity
    ): Response<PublicResponse<DoneResponse>>

    @GET
    suspend fun getDoingTasks(
        @Url url: String,
        @Query("processInstanceIds") processInstanceId: String
    ): Response<PublicResponse<List<TaskResponse>>>

    @GET
    suspend fun getDocumentTasks(
        @Url url: String,
        @Query("dashboardId") dashboardId: String,
        @Query("processInstanceId") processInstanceId: String,
        @Query("tags") tags: String
    ): Response<PublicResponse<List<TaskResponse>>>

}