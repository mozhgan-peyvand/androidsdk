package ir.part.sdk.ara.data.barjavand.entities


import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class RemoveDocumentParamRequest(
    val schema: Schema,
    val id: String,
    val newTags: Tag,
    // todo : remove this when data object is removed from request body
    val data: EmptyClass = EmptyClass()
)

@JsonSerializable
data class EmptyClass(
    val empty: Any? = null
)