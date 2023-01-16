package ir.part.sdk.ara.domain.document.entities

data class DocumentState(
    val processId: String? = null,
    val processType: String? = null,
    val archive: String? = null,
    val processInstanceId: String? = null,
    val status: DocumentsStatus? = null
)
