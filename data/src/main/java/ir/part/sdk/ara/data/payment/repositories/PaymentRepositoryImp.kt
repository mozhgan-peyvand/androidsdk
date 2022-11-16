package ir.part.sdk.ara.data.payment.repositories

//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.data.dashboard.repositories.DashboardLocalDataSource
import ir.part.sdk.ara.data.payment.entities.PaymentBodyRequest
import ir.part.sdk.ara.data.payment.entities.PaymentResponseModel
import ir.part.sdk.ara.domain.payment.entities.Payment
import ir.part.sdk.ara.domain.payment.repository.PaymentRepository
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

//@FeatureDataScope
class PaymentRepositoryImp @Inject constructor(
    private val remoteDataSource: PaymentRemoteDataSource,
    private val dashboardLocalDataSource: DashboardLocalDataSource,
    private val requestExecutor: RequestExecutor
) : PaymentRepository {

    override suspend fun getPaymentUrl(documentProcessInstanceId: String): InvokeStatus<Payment?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<PaymentResponseModel>, Payment?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<PaymentResponseModel>> =
                remoteDataSource.getPaymentUrl(
                    PaymentBodyRequest(
                        dashboardLocalDataSource.getTaskInstanceId(),
                        processInstanceId = documentProcessInstanceId
                    )
                )

            override fun onConvertResult(data: PublicResponse<PaymentResponseModel>): Payment? {
                return data.item?.toPayment()
            }
        })
}