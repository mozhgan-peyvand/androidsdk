package ir.part.sdk.ara.data.dashboard.repositories

import ir.part.sdk.ara.data.dashboard.entites.DoingEntity
import ir.part.sdk.ara.data.dashboard.entites.DoneEntity
import ir.part.sdk.ara.data.dashboard.entites.SubmitReqValidationParamModel
import ir.part.sdk.ara.util.api.BaseRemoteDataSource
import ir.part.sdk.ara.util.api.safeApiCall
import javax.inject.Inject

/**
 * Data source class that handles work with File API.
 */

class DashboardRemoteDataSource @Inject constructor(
    private val service: DashboardService
) : BaseRemoteDataSource() {

    suspend fun submitReqValidation(
        nationalCode: String,
        submitReqValidationParamModel: SubmitReqValidationParamModel
    ) =
        safeApiCall(
            call = { requestSubmitReqValidation(nationalCode, submitReqValidationParamModel) },
            errorMessage = "Error submit Request Validation"
        )

    private suspend fun requestSubmitReqValidation(
        nationalCode: String,
        submitReqValidationParamModel: SubmitReqValidationParamModel
    ) =
        checkApiResult(
            service.newDocumentProcess(
                url = urls.dashboard.newDocumentProcess,
                SubmitReqValidationParamModel(
                    processId = "b8db28da-53ba-4545-bcac-a82a45eb7ec9",
                    dashboardId = "3337a727-4d73-492e-9d60-f5d2c6deb103",
                    actorId = "407d4149-1ff2-47fb-a029-bc16b79f2a0b",
                    event = "new",
                    tags = listOf("username_$nationalCode"),
                    unionId = submitReqValidationParamModel.unionId
                )
            )
        )
    suspend fun getTask(processInstanceId: String) = safeApiCall(
        call = { requestGetTask(processInstanceId) },
        errorMessage = "Error getting taskInfo"
    )

    private suspend fun requestGetTask(processInstanceId: String) =
        checkApiResult(
            service.getTask(
                url = urls.dashboard.getTask,
                processInstanceId = processInstanceId
            )
        )


    suspend fun doingTask(processInstanceId: String, taskId: String) = safeApiCall(
        call = { requestDoingTask(processInstanceId, taskId) },
        errorMessage = "Error done taskInfo"
    )

    private suspend fun requestDoingTask(processInstanceId: String, taskId: String) =
        checkApiResult(
            service.requestDoingTask(
                url = urls.dashboard.doingTask,
                doingEntity = DoingEntity(
                    actorId = "407d4149-1ff2-47fb-a029-bc16b79f2a0b",
                    status = "doing",
                    dashboardId = "aa7f32e1-ff21-4b5b-92e1-c37da3499a43",
                    processInstanceId = processInstanceId,
                    taskId = taskId
                )
            )
        )


    suspend fun doneTask(processInstanceId: String, taskId: String) = safeApiCall(
        call = { requestDoneTask(processInstanceId, taskId) },
        errorMessage = "Error done taskInfo"
    )

    private suspend fun requestDoneTask(processInstanceId: String, taskId: String) =
        checkApiResult(
            service.requestDoneTask(
                url = urls.dashboard.doneTask,
                DoneEntity(
                    processInstanceId = processInstanceId,
                    taskId = taskId,
                    dashboardId = "aa7f32e1-ff21-4b5b-92e1-c37da3499a43",
                    actorId = "407d4149-1ff2-47fb-a029-bc16b79f2a0b",
                    state = true,
                    status = "done"
                )
            )
        )

}