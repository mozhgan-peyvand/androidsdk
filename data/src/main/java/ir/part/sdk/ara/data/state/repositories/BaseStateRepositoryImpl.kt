package ir.part.sdk.ara.data.state.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.data.state.entites.BaseStateResponse
import ir.part.sdk.ara.data.state.mappers.toBaseState
import ir.part.sdk.ara.domain.tasks.entities.BaseStateInfo
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

@FeatureDataScope
class BaseStateRepositoryImpl @Inject constructor(
    private val remoteDataSource: BaseStateRemoteDataSource,
    private val requestExecutor: RequestExecutor,
    private val pref: SharedPreferences,
    @SK private val sk: String
) : BaseStateRepository {
    override suspend fun getBaseState(): InvokeStatus<BaseStateInfo?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<BaseStateResponse>, BaseStateInfo?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<BaseStateResponse>> {
                val response = remoteDataSource.baseState(
                    pref.getString("CurrentUserNationalCode", null)?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: ""
                )
                pref.edit().putString(
                    "processInstanceId",
                    AesEncryptor().encrypt(response.data?.item?.processInstanceId ?: "", sk)
                )
                    .apply()
                return response
            }

            override fun onConvertResult(data: PublicResponse<BaseStateResponse>): BaseStateInfo? =
                data.item?.toBaseState()
        }
        )
}