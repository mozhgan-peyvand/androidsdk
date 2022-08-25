package ir.part.sdk.ara.data.dashboard.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.data.dashboard.entites.DoneResponse
import ir.part.sdk.ara.data.dashboard.entites.SubmitResponseValidationEntity
import ir.part.sdk.ara.data.dashboard.entites.TaskResponse
import ir.part.sdk.ara.data.dashboard.mappers.toSubmitReqValidationParamModel
import ir.part.sdk.ara.domain.document.entities.SubmitReqValidationParam
import ir.part.sdk.ara.domain.document.entities.SubmitResponseValidation
import ir.part.sdk.ara.domain.document.repository.DashboardRepository
import ir.part.sdk.ara.domain.tasks.entities.Done
import ir.part.sdk.ara.domain.tasks.entities.TaskInfo
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

@FeatureDataScope
class DashboardRepositoryImpl @Inject constructor(
    private val remoteDataSource: DashboardRemoteDataSource,
    private val pref: SharedPreferences,
    private val requestExecutor: RequestExecutor,
    @SK private val sk: String
) : TaskRepository, DashboardRepository {

    override suspend fun requestGetTask(): InvokeStatus<List<TaskInfo>?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<List<TaskResponse>>, List<TaskInfo>?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<List<TaskResponse>>> {
                val response: InvokeStatus<PublicResponse<List<TaskResponse>>> =
                    remoteDataSource.getTask(
                        pref.getString("processInstanceId", null)?.let {
                            AesEncryptor().decrypt(it, sk)
                        } ?: ""
                    )

                pref.edit().putString(
                    "taskId",
                    (if (response.data?.item.isNullOrEmpty()) "" else response.data?.item?.get(0)?.taskId)?.let {
                        AesEncryptor().encrypt(
                            it, sk
                        )
                    }
                ).apply()
                return response
            }

            override fun onConvertResult(data: PublicResponse<List<TaskResponse>>): List<TaskInfo>? =
                data.item?.map { it.toTaskInfo() }

        })

    override suspend fun requestDoingTask(
        processInstanceId: String,
        taskId: String
    ): InvokeStatus<String?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<String>, String?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<String>> =
                remoteDataSource.doingTask(
                    processInstanceId,
                    taskId
                )

            override fun onConvertResult(data: PublicResponse<String>): String {
                return data.item.toString()
            }
        })

    override suspend fun requestDoneTask(
        processInstanceId: String,
        taskId: String
    ): InvokeStatus<Done?> = requestExecutor.execute(object :
        InvokeStatus.ApiEventListener<PublicResponse<DoneResponse>, Done?> {
        override suspend fun onRequestCall(): InvokeStatus<PublicResponse<DoneResponse>> =
            remoteDataSource.doneTask(
                processInstanceId, taskId
            )

        override fun onConvertResult(data: PublicResponse<DoneResponse>): Done? =
            data.item?.toDone()
    })

    override suspend fun submitReqValidation(submitReqValidationParam: SubmitReqValidationParam): InvokeStatus<SubmitResponseValidation?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<SubmitResponseValidationEntity>, SubmitResponseValidation?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<SubmitResponseValidationEntity>> =
                remoteDataSource.submitReqValidation(
                    submitReqValidationParam.toSubmitReqValidationParamModel()
                )

            override fun onConvertResult(data: PublicResponse<SubmitResponseValidationEntity>): SubmitResponseValidation? =
                data.item?.toSubmitResponseValidation()
        })
}