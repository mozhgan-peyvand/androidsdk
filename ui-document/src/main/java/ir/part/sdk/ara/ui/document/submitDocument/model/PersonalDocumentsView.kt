package ir.part.sdk.ara.ui.document.submitDocument.model

import androidx.annotation.Keep


@Keep
data class PersonalDocumentsView(
    var fileIdNew: String? = null,
    var processInstanceId: String? = null,
    var fileId: Long? = null,
    var applicantUsername: String? = null,
    var unionId: Int? = null,
    var messageList: List<PersonalDocumentMessageView>? = null,
    var hasUnreadMessage: Boolean = false,
    var validationResult: DocumentResultValidationView? = null,
    var createdAt: String? = null,
    var requestDateView: String? = null,
    var amount: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var chartData: String? = null,
    var status: DocumentsStatusView? = null,
    val completionDate: String? = null,
    val completionDateView: String? = null,
    val showValidationProperties: Boolean = false
)
