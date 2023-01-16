package ir.part.sdk.ara.data.state.repositories

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.data.state.entites.BaseStateResponse
import ir.part.sdk.ara.data.state.entites.DocumentStateResponse
import ir.part.sdk.ara.data.state.mappers.toBaseState
import ir.part.sdk.ara.data.state.mappers.toDocumentState
import ir.part.sdk.ara.data.userManager.repositories.UserManagerLocalDataSource
import ir.part.sdk.ara.domain.document.entities.DocumentState
import ir.part.sdk.ara.domain.tasks.entities.BaseStateInfo
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository
import ir.part.sdk.ara.model.StateResponse
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

class BaseStateRepositoryImpl @Inject constructor(
    private val stateLocalDataSource: StateLocalDataSource,
    private val userManagerLocalDataSource: UserManagerLocalDataSource,
    private val remoteDataSource: BaseStateRemoteDataSource,
    private val requestExecutor: RequestExecutor,
) : BaseStateRepository {
    override suspend fun getBaseState(): InvokeStatus<BaseStateInfo?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<StateResponse<BaseStateResponse>, BaseStateInfo?> {
            override suspend fun onRequestCall(): InvokeStatus<StateResponse<BaseStateResponse>> {
                val response = remoteDataSource.baseState(
                    userManagerLocalDataSource.getNationalCode()
                )

                stateLocalDataSource.saveProcessId(response.data?.item?.firstOrNull()?.pid)

                stateLocalDataSource.saveProcessInstanceId(response.data?.item?.firstOrNull()?.processInstanceId)
                return response
            }

            override fun onConvertResult(data: StateResponse<BaseStateResponse>): BaseStateInfo? =
                data.item?.firstOrNull()?.toBaseState()
        }
        )

    override fun getProcessInstanceId(): String = stateLocalDataSource.getProcessInstanceId()

    override suspend fun getDocumentsStates(): InvokeStatus<List<DocumentState>?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<StateResponse<DocumentStateResponse>, List<DocumentState>?> {
            override suspend fun onRequestCall(): InvokeStatus<StateResponse<DocumentStateResponse>> {

                return remoteDataSource.documentsStates(
                    userManagerLocalDataSource.getNationalCode()
                )
            }

            override fun onConvertResult(data: StateResponse<DocumentStateResponse>): List<DocumentState>? =
                data.item?.map {
                    it.toDocumentState()
                }
        }
        )
}