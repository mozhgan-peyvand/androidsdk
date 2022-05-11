package ir.part.sdk.ara.data.dashboard.entites


import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class SubmitReqValidationParamModel(
    val processId: String? = null,
    val dashboardId: String? = null,
    val actorId: String? = null,
    val event: String? = null,
    val tags: List<String>? = null,
    val unionId: Int
)