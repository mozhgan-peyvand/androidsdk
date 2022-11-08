package ir.part.sdk.ara.data.payment.entities

import com.squareup.moshi.Json
import ir.part.sdk.ara.domain.payment.entities.Payment
import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class PaymentResponseModel(
    @field:Json(name = "url")
    val url: String
) {

    fun toPayment() = Payment(url)
}