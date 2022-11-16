package ir.part.sdk.ara.domain.payment.interactors

//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.payment.entities.Payment
import ir.part.sdk.ara.domain.payment.repository.PaymentRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject


//@FeatureDataScope
class GetPaymentRemote @Inject constructor(
    private val repository: PaymentRepository
) : ResultInteractor<String, Payment?>() {

    override suspend fun doWork(params: String): InvokeStatus<Payment?> {
        return repository.getPaymentUrl(params)
    }

}
