package ir.part.sdk.ara.data.barjavand.entities


import ir.part.sdk.ara.domain.document.entities.PersonalDocumentMessage
import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class PersonalDocumentMessageEntity(
    val dateTime: String? = null,
    val message: String? = null,
    val statusCode: String? = null
) {
    fun toPersonalDocumentMessage() = PersonalDocumentMessage(
        dataTime = dateTime,
        message = message,
        statusCode = statusCode.let {
            DocumentsStatusEntity.enumValueOf(it.toString())?.toFilesStatus()
        })
}




