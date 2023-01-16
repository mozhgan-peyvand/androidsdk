package ir.part.sdk.ara.data.barjavand.entities


import com.squareup.moshi.Json
import ir.part.sdk.ara.domain.document.entities.PersonalDocuments

import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class PersonalDocumentsEntity(
    @field:Json(name = "id")
    val fileIdNew: String? = null,
    @field:Json(name = "processInstanceId")
    val processInstanceId: String? = null,
    @field:Json(name = "serialNumber")
    val fileId: Long? = null,
    @field:Json(name = "applicantUsername")
    val applicantUsername: String? = null,
    @field:Json(name = "unionId")
    val unionId: Int? = null,
    @field:Json(name = "messageList")
    val message: List<PersonalDocumentMessageEntity>? = null,
    @field:Json(name = "hasUnreadMessage")
    val hasUnreadMessage: Boolean = false,
    @field:Json(name = "validationResult")
    val validationResult: DocumentResultValidationEntity? = null,
    @field:Json(name = "createdAt")
    val createdAt: String? = null,
    @field:Json(name = "amount")
    val amount: Long? = null,
    @field:Json(name = "status")
    val statusId: String? = null,
    var name: String? = null,
    var family: String? = null,
    var statusName: String? = null,
    var statusTitle: String? = null
) {
    fun toPersonalDocuments() = PersonalDocuments(
        fileIdNew = fileIdNew,
        processInstanceId = processInstanceId,
        fileId = fileId,
        applicantUsername = applicantUsername,
        unionId = unionId,
        messageList = message?.map { it.toPersonalDocumentMessage() },
        hasUnreadMessage = hasUnreadMessage,
        validationResult = validationResult?.toFileResultValidation(),
        createdAt = createdAt,
        amount = amount,
        statusId = statusId.let {
            DocumentsStatusEntity.enumValueOf(it ?: "")?.toFilesStatus()
        },
        firstName = name,
        lastName = family,
        statusName = statusName,
        statusTitle = statusTitle
    )
}


