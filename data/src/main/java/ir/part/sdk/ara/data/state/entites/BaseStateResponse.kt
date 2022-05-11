package ir.part.sdk.ara.data.state.entites

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseStateResponse(
    @field:Json(name = "processType")
    val processType: String? = null,
    @field:Json(name = "pid")
    val pid: String? = null,
    @field:Json(name = "piid")
    val processInstanceId: String? = null,
    @field:Json(name = "relatedprocessInstanceIds")
    val relatedProcessInstanceIds: List<String?> = listOf()
)