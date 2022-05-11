package ir.part.sdk.ara.domain.document.entities

data class DocumentResultValidation(
    val proportionate: Int? = null,
    val perfectProportionate: Int? = null,
    val disproportionate: Int? = null,
    val expireDate: String? = null,
    val deptCeiling: Int? = null,
    val commitmentCeiling: Int? = null,
    val commitmentPrice: Long? = null,
    val chartData: String? = null
)

