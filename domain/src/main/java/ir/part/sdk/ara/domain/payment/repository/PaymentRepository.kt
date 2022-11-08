package ir.part.sdk.ara.domain.payment.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.payment.entities.Payment

interface PaymentRepository {

    suspend fun getPaymentUrl(documentProcessInstanceId: String): InvokeStatus<Payment?>

}