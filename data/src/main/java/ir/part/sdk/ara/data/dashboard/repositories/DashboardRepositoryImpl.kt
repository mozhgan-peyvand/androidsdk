package ir.part.sdk.ara.data.dashboard.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.data.dashboard.entites.DoingResponse
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
    private val dashboardLocalDataSource: DashboardLocalDataSource,
    private val remoteDataSource: DashboardRemoteDataSource,
    private val pref: SharedPreferences,
    private val requestExecutor: RequestExecutor,
    @SK private val sk: String
) : TaskRepository, DashboardRepository {

    override suspend fun requestGetDoingTasks(): InvokeStatus<TaskInfo?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<List<TaskResponse>>, TaskInfo?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<List<TaskResponse>>> {

                val processInstanceId =
                    listOf(("\"" + (pref.getString("processInstanceId", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: "") + "\"")).toString()

                return remoteDataSource.getDoingTasks(
                    processInstanceId = processInstanceId
                )
            }

            override fun onConvertResult(data: PublicResponse<List<TaskResponse>>): TaskInfo? {
                pref.edit().putString(
                    "taskId",
                    AesEncryptor().encrypt(data.item?.firstOrNull()?.taskId ?: "", sk)
                )
                    .apply()

                pref.edit().putString(
                    "taskInstanceId",
                    AesEncryptor().encrypt(data.item?.firstOrNull()?.id ?: "", sk)
                )
                    .apply()

                pref.edit().putString(
                    "taskName",
                    AesEncryptor().encrypt(data.item?.firstOrNull()?.name ?: "", sk)
                )
                    .apply()
                return data.item?.firstOrNull()?.toTaskInfo()
            }

        })

    override suspend fun requestDoingTask(
    ): InvokeStatus<String?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<DoingResponse>, String?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<DoingResponse>> =
                remoteDataSource.doingTask(
                    processInstanceId = pref.getString("processInstanceId", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: "",
                    taskId = pref.getString("taskId", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: "",
                    taskInstanceId = pref.getString("taskInstanceId", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: "",
                    taskName = pref.getString("taskName", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: ""
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
                processInstanceId = pref.getString("processInstanceId", null)?.let {
                    AesEncryptor().decrypt(it, sk)
                } ?: "",
                taskId = pref.getString("taskId", null)?.let {
                    AesEncryptor().decrypt(it, sk)
                } ?: "",
                taskInstanceId = pref.getString("taskInstanceId", null)?.let {
                    AesEncryptor().decrypt(it, sk)
                } ?: "",
                taskName = pref.getString("taskName", null)?.let {
                    AesEncryptor().decrypt(it, sk)
                } ?: ""
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
                    processInstanceId = pref.getString("processInstanceId", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: "",
                    taskInstanceId = pref.getString("taskInstanceId", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: "",
                    nationalCode = pref.getString("CurrentUserNationalCode", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: "",
                    processId = pref.getString("processId", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: ""
                )

            override fun onConvertResult(data: PublicResponse<SubmitResponseValidationEntity>): SubmitResponseValidation? =
                data.item?.toSubmitResponseValidation()
        })

    override fun getTaskInstanceId(): String = dashboardLocalDataSource.getTaskInstanceId()


}