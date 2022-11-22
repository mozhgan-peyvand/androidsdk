package ir.part.sdk.ara.data.dashboard.repositories

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.data.dashboard.entites.DoingResponse
import ir.part.sdk.ara.data.dashboard.entites.DoneResponse
import ir.part.sdk.ara.data.dashboard.entites.SubmitResponseValidationEntity
import ir.part.sdk.ara.data.dashboard.entites.TaskResponse
import ir.part.sdk.ara.data.dashboard.mappers.toSubmitReqValidationParamModel
import ir.part.sdk.ara.data.state.repositories.StateLocalDataSource
import ir.part.sdk.ara.data.userManager.repositories.UserManagerLocalDataSource
import ir.part.sdk.ara.domain.document.entities.SubmitReqValidationParam
import ir.part.sdk.ara.domain.document.entities.SubmitResponseValidation
import ir.part.sdk.ara.domain.document.repository.DashboardRepository
import ir.part.sdk.ara.domain.tasks.entities.Done
import ir.part.sdk.ara.domain.tasks.entities.TaskInfo
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val dashboardLocalDataSource: DashboardLocalDataSource,
    private val stateLocalDataSource: StateLocalDataSource,
    private val userManagerLocalDataSource: UserManagerLocalDataSource,
    private val remoteDataSource: DashboardRemoteDataSource,
    private val requestExecutor: RequestExecutor,
) : TaskRepository, DashboardRepository {

    override suspend fun requestGetDoingTasks(): InvokeStatus<TaskInfo?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<List<TaskResponse>>, TaskInfo?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<List<TaskResponse>>> {

                val processInstanceId =
                    listOf(("\"" + (stateLocalDataSource.getProcessInstanceId()) + "\"")).toString()

                return remoteDataSource.getDoingTasks(
                    processInstanceId = processInstanceId
                )
            }

            override fun onConvertResult(data: PublicResponse<List<TaskResponse>>): TaskInfo? {

                dashboardLocalDataSource.saveTaskId(data.item?.firstOrNull()?.taskId)

                dashboardLocalDataSource.saveTaskInstanceId(data.item?.firstOrNull()?.id)

                dashboardLocalDataSource.saveTaskName(data.item?.firstOrNull()?.name)

                return data.item?.firstOrNull()?.toTaskInfo()
            }

        })

    override suspend fun requestDoingTask(
    ): InvokeStatus<String?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<DoingResponse>, String?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<DoingResponse>> =
                remoteDataSource.doingTask(
                    processInstanceId = stateLocalDataSource.getProcessInstanceId(),
                    taskId = dashboardLocalDataSource.getTaskId(),
                    taskInstanceId = dashboardLocalDataSource.getTaskInstanceId(),
                    taskName = dashboardLocalDataSource.getTaskName()
                )

            override fun onConvertResult(data: PublicResponse<DoingResponse>): String {
                return data.item.toString()
            }
        })

    override suspend fun requestDoneTask(

    ): InvokeStatus<Done?> = requestExecutor.execute(object :
        InvokeStatus.ApiEventListener<PublicResponse<DoneResponse>, Done?> {
        override suspend fun onRequestCall(): InvokeStatus<PublicResponse<DoneResponse>> =
            remoteDataSource.doneTask(
                processInstanceId = stateLocalDataSource.getProcessInstanceId(),
                taskId = dashboardLocalDataSource.getTaskId(),
                taskInstanceId = dashboardLocalDataSource.getTaskInstanceId(),
                taskName = dashboardLocalDataSource.getTaskName()
            )

        override fun onConvertResult(data: PublicResponse<DoneResponse>): Done? =
            data.item?.toDone()
    })

    override suspend fun submitReqValidation(submitReqValidationParam: SubmitReqValidationParam): InvokeStatus<SubmitResponseValidation?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<SubmitResponseValidationEntity>, SubmitResponseValidation?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<SubmitResponseValidationEntity>> =
                remoteDataSource.submitReqValidation(
                    submitReqValidationParam.toSubmitReqValidationParamModel(),
                    processInstanceId = stateLocalDataSource.getProcessInstanceId(),
                    taskInstanceId = dashboardLocalDataSource.getTaskInstanceId(),
                    nationalCode = userManagerLocalDataSource.getNationalCode(),
                    processId = stateLocalDataSource.getProcessId()
                )

            override fun onConvertResult(data: PublicResponse<SubmitResponseValidationEntity>): SubmitResponseValidation? =
                data.item?.toSubmitResponseValidation()
        })

    override fun getTaskInstanceId(): String = dashboardLocalDataSource.getTaskInstanceId()


}