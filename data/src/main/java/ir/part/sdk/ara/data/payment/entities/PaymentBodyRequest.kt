package ir.part.sdk.ara.data.payment.entities

import com.squareup.moshi.JsonClass
import se.ansman.kotshi.JsonSerializable

@JsonClass(generateAdapter = true)
@JsonSerializable
data class PaymentBodyRequest(
    val taskInstanceId: String,
    val processInstanceId: String
)