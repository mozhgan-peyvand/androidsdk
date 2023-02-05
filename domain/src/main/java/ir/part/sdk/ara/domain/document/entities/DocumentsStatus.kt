package ir.part.sdk.ara.domain.document.entities

enum class DocumentsStatus {
    WAITING_FOR_PERSONAL_INFORMATION_CHECK,
    PERSONAL_INFORMATION_CHECK,
    PERSONAL_INFORMATION_CHECK_ON_CORRECTION,
    PERSONAL_INFORMATION_CHECK_REJECTION,
    NEED_TO_CORRECTION,
    CREDIT_BEHAVIOR_INQUIRY,
    BOUNCED_CHEQUE_INQUIRY,
    CENTRAL_BANK_INQUIRY,
    FINANCIAL_PERFORMANCE_INQUIRY,
    VIEW_INQUIRIES_AND_VALIDATION_REQUESTS,
    VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION,
    REQUEST_EVALUATION,
    REQUEST_EVALUATION_REJECTION,
    SECONDARY_REQUEST_EVALUATION_REJECTION,
    IMPROVER_COVERAGE_TRUE,
    IMPROVER_COVERAGE_FALSE,
    PAYMENT,
    DELETE_DOCUMENT;
}