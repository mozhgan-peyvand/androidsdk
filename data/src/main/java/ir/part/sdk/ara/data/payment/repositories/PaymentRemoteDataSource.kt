package ir.part.sdk.ara.data.payment.repositories

import ir.part.sdk.ara.data.payment.entities.PaymentBodyRequest
import ir.part.sdk.ara.util.api.BaseRemoteDataSource
import ir.part.sdk.ara.util.api.safeApiCall
import javax.inject.Inject

class PaymentRemoteDataSource @Inject constructor(private val service: PaymentService) :
    BaseRemoteDataSource() {

    suspend fun getPaymentUrl(paymentRequest: PaymentBodyRequest) = safeApiCall(
        call = { requestGetPaymentUrl(paymentRequest) },
        errorMessage = "Error getting payment"
    )

    private suspend fun requestGetPaymentUrl(paymentRequest: PaymentBodyRequest) = checkApiResult(
        service.getPaymentUrl(
            url = urls.payment.getPayment,
            contentType = "application/json",
            paymentRequest = paymentRequest
        )
    )
}