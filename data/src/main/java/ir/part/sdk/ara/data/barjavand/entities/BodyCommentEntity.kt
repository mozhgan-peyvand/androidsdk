package ir.part.sdk.ara.data.barjavand.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class BodyCommentEntity(
    val data: DataCommentEntity,
    val schema: SchemaEntity,
    val tags: TagsEntity
)

@JsonSerializable
data class SchemaEntity(val name: String, val version: String)

@JsonSerializable
data class TagsEntity(val userType: String, val mobile: String)

@JsonSerializable
data class DataCommentEntity(
    val description: String,
    val email: String?,
    val family: String,
    val mobile: String,
    val name: String,
)
