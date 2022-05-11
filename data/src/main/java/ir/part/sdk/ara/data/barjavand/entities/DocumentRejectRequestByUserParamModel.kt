package ir.part.sdk.ara.data.barjavand.entities


import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class DocumentRejectRequestByUserParamModel(
    @field:Json(name = "parvande_number")
    val fileId: Long? = null
)