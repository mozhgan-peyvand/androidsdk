package ir.part.sdk.ara.domain.payment.interactors

import dagger.Reusable
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.payment.entities.Payment
import ir.part.sdk.ara.domain.payment.repository.PaymentRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject


@Reusable
class GetPaymentRemote @Inject constructor(
    private val repository: PaymentRepository
) : ResultInteractor<String, Payment?>() {

    override suspend fun doWork(params: String): InvokeStatus<Payment?> {
        return repository.getPaymentUrl(params)
    }

}
