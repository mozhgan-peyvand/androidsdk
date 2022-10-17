package ir.part.sdk.ara.data.state.repositories

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.data.state.entites.BaseStateResponse
import ir.part.sdk.ara.data.state.mappers.toBaseState
import ir.part.sdk.ara.data.userManager.repositories.UserManagerLocalDataSource
import ir.part.sdk.ara.domain.tasks.entities.BaseStateInfo
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

@FeatureDataScope
class BaseStateRepositoryImpl @Inject constructor(
    private val stateLocalDataSource: StateLocalDataSource,
    private val userManagerLocalDataSource: UserManagerLocalDataSource,
    private val remoteDataSource: BaseStateRemoteDataSource,
    private val requestExecutor: RequestExecutor,
) : BaseStateRepository {
    override suspend fun getBaseState(): InvokeStatus<BaseStateInfo?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<BaseStateResponse>, BaseStateInfo?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<BaseStateResponse>> {
                val response = remoteDataSource.baseState(
                    userManagerLocalDataSource.getNationalCode()
                )

                stateLocalDataSource.saveProcessId(response.data?.item?.pid)

                stateLocalDataSource.saveProcessInstanceId(response.data?.item?.processInstanceId)
                return response
            }

            override fun onConvertResult(data: PublicResponse<BaseStateResponse>): BaseStateInfo? =
                data.item?.toBaseState()
        }
        )

    override fun getProcessInstanceId(): String = stateLocalDataSource.getProcessInstanceId()
}