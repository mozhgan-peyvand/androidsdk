package ir.part.sdk.ara.data.barjavand.entities

import com.squareup.moshi.Json
import ir.part.sdk.ara.domain.document.entities.DocumentResultValidation

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class DocumentResultValidationEntity(
    @field:Json(name = "KAMELAN_MOTANASEB")
    val proportionate: Int? = null,
    @field:Json(name = "MOMTAZ")
    val perfectProportionate: Int? = null,
    @field:Json(name = "MOTANASEB")
    val disproportionate: Int? = null,
    @field:Json(name = "EXPIRE_DATE")
    val expireDate: String? = null,
    @field:Json(name = "DEBT_CEILING")
    val deptCeiling: Int? = null,
    @field:Json(name = "COMMITMENT_CEILING")
    val commitmentCeiling: Int? = null,
    @field:Json(name = "price")
    val commitmentPrice: Long? = null,
    @field:Json(name = "CHARTDATA")
    val chartData: String? = null
) {
    fun toFileResultValidation() = DocumentResultValidation(
        proportionate = proportionate,
        perfectProportionate = perfectProportionate,
        disproportionate = disproportionate,
        expireDate = expireDate,
        deptCeiling = deptCeiling,
        commitmentCeiling = commitmentCeiling,
        commitmentPrice = commitmentPrice,
        chartData = chartData,
    )
}





