package ir.part.sdk.ara.data.state.entites

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentStateResponse(
    val processId: String? = null,
    val processType: String? = null,
    val archive: String? = null,
    val processInstanceId: String? = null,
    val status: String? = null,
    val tasks: Map<String, Status>? = null
)
