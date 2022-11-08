package ir.part.sdk.ara.data.payment.repositories

import ir.part.sdk.ara.data.payment.entities.PaymentBodyRequest
import ir.part.sdk.ara.data.payment.entities.PaymentResponseModel
import ir.part.sdk.ara.model.PublicResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface PaymentService {

    @POST
    suspend fun getPaymentUrl(
        @Url url: String,
        @Header("Content-Type") contentType: String,
        @Body paymentRequest: PaymentBodyRequest
    ): Response<PublicResponse<PaymentResponseModel>>
}