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
        submitReqValidationParamModel: SubmitReqValidationParamModel,
        nationalCode: String,
        processInstanceId: String,
        taskInstanceId: String,
        processId: String
    ) =
        safeApiCall(
            call = {
                requestSubmitReqValidation(
                    submitReqValidationParamModel = submitReqValidationParamModel,
                    nationalCode = nationalCode,
                    processInstanceId = processInstanceId,
                    taskInstanceId = taskInstanceId,
                    processId = processId
                )
            },
            errorMessage = "Error submit Request Validation"
        )

    private suspend fun requestSubmitReqValidation(
        submitReqValidationParamModel: SubmitReqValidationParamModel,
        nationalCode: String,
        processInstanceId: String,
        taskInstanceId: String,
        processId: String
    ) =
        checkApiResult(
            service.newDocumentProcess(
                url = urls.dashboard.newDocumentProcess,
                submitReqValidationParamModel = SubmitReqValidationParamModel(
                    processId = processId,
                    dashboardId = "60e11757-128c-4de8-9c68-28ce06bed1bf",
                    actorId = "279b8d69-c71a-4ff2-81e5-4143f6a839e7",
                    event = "new",
                    unionId = submitReqValidationParamModel.unionId
                ),
                username = nationalCode,
                processInstanceId = processInstanceId,
                taskInstanceId = taskInstanceId
            )
        )

    suspend fun doingTask(
        processInstanceId: String,
        taskId: String,
        taskInstanceId: String,
        taskName: String
    ) =
        safeApiCall(
            call = {
                requestDoingTask(
                    processInstanceId = processInstanceId,
                    taskId = taskId,
                    taskInstanceId = taskInstanceId,
                    taskName = taskName
                )
            },
            errorMessage = "Error doing task"
        )

    private suspend fun requestDoingTask(
        processInstanceId: String,
        taskId: String,
        taskInstanceId: String,
        taskName: String
    ) =
        checkApiResult(
            service.doing(
                url = urls.dashboard.doingTask,
                doingEntity = DoingEntity(
                    actorId = "279b8d69-c71a-4ff2-81e5-4143f6a839e7",
                    status = "doing",
                    dashboardId = "60e11757-128c-4de8-9c68-28ce06bed1bf",
                    processInstanceId = processInstanceId,
                    taskId = taskId,
                    taskName = taskName
                ),
                processInstanceId = processInstanceId,
                taskInstanceId = taskInstanceId
            )
        )

    suspend fun doneTask(
        processInstanceId: String,
        taskId: String,
        taskInstanceId: String,
        taskName: String
    ) =
        safeApiCall(
            call = {
                requestDoneTask(
                    processInstanceId = processInstanceId,
                    taskId = taskId,
                    taskInstanceId = taskInstanceId,
                    taskName = taskName
                )
            },
            errorMessage = "Error done task"
        )

    private suspend fun requestDoneTask(
        processInstanceId: String,
        taskId: String,
        taskInstanceId: String,
        taskName: String
    ) =
        checkApiResult(
            service.done(
                url = urls.dashboard.doneTask,
                doneEntity = DoneEntity(
                    processInstanceId = processInstanceId,
                    taskId = taskId,
                    dashboardId = "60e11757-128c-4de8-9c68-28ce06bed1bf",
                    actorId = "279b8d69-c71a-4ff2-81e5-4143f6a839e7",
                    state = true,
                    status = "done",
                    taskName = taskName
                ),
                taskInstanceId = taskInstanceId,
                processInstanceId = processInstanceId
            )
        )

    suspend fun getDoingTasks(processInstanceId: String) =
        safeApiCall(
            call = {
                requestGetDoingTasks(
                    processInstanceId = processInstanceId,
                )
            },
            errorMessage = "Error get doing tasks"
        )

    private suspend fun requestGetDoingTasks(
        processInstanceId: String
    ) =
        checkApiResult(
            service.getDoingTasks(
                url = urls.dashboard.getDoingTasks,
                processInstanceId = processInstanceId
            )
        )
}