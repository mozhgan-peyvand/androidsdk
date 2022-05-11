package ir.part.sdk.ara.data.dashboard.entites


import com.squareup.moshi.Json
import ir.part.sdk.ara.domain.document.entities.SubmitReqValidation

import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class SubmitReqValidationEntity(
    @field:Json(name = "documentSerialNumber")
    val documentSerialNumber: Int
) {
    fun toSubmitReqValidation() = SubmitReqValidation(
        shomareParvande = documentSerialNumber
    )
}