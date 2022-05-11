package ir.part.sdk.ara.domain.document.entities


data class PersonalDocuments(
    val fileIdNew: String? = null,
    val processInstanceId: String? = null,
    val fileId: Long? = null,
    val applicantUsername: String? = null,
    val unionId: Int? = null,
    val messageList: List<PersonalDocumentMessage>? = null,
    val hasUnreadMessage: Boolean = false,
    val validationResult: DocumentResultValidation,
    val createdAt: String? = null,
    val amount: Long? = null,
    val statusId: DocumentsStatus? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val statusName: String? = null,
    val statusTitle: String? = null
)