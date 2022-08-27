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
        submitReqValidationParamModel: SubmitReqValidationParamModel
    ) =
        safeApiCall(
            call = { requestSubmitReqValidation(submitReqValidationParamModel) },
            errorMessage = "Error submit Request Validation"
        )

    private suspend fun requestSubmitReqValidation(
        submitReqValidationParamModel: SubmitReqValidationParamModel
    ) =
        checkApiResult(
            service.newDocumentProcess(
                url = urls.dashboard.newDocumentProcess,
                submitReqValidationParamModel = SubmitReqValidationParamModel(
                    // todo : change to dynamic after get task is implemented
                    processId = "b5872c85-caae-4ab9-833b-a3b37af420fb",
                    dashboardId = "60e11757-128c-4de8-9c68-28ce06bed1bf",
                    actorId = "279b8d69-c71a-4ff2-81e5-4143f6a839e7",
                    event = "new",
                    unionId = submitReqValidationParamModel.unionId
                )
            )
        )

    suspend fun getTask(processInstanceId: String, tags: String) = safeApiCall(
        call = { requestGetTask(processInstanceId, tags) },
        errorMessage = "Error getting taskInfo"
    )

    private suspend fun requestGetTask(processInstanceId: String, tags: String) =
        checkApiResult(
            service.getTask(
                url = urls.dashboard.getTask,
                processInstanceId = processInstanceId,
                tags = tags
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
                    // todo : change to dynamic after get task is implemented
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
                    // todo : change to dynamic after get task is implemented
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