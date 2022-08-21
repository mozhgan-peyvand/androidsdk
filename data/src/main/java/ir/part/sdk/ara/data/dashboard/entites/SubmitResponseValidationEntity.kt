package ir.part.sdk.ara.data.dashboard.entites


import com.squareup.moshi.Json
import ir.part.sdk.ara.domain.document.entities.SubmitResponseValidation

import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class SubmitResponseValidationEntity(
    @field:Json(name = "documentSerialNumber")
    val documentSerialNumber: Int
) {
    fun toSubmitResponseValidation() = SubmitResponseValidation(
        documentSerialNumber = documentSerialNumber
    )
}