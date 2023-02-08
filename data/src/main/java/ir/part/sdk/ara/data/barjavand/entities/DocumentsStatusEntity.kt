package ir.part.sdk.ara.data.barjavand.entities

import ir.part.sdk.ara.domain.document.entities.DocumentsStatus


enum class DocumentsStatusEntity(val value: String) {

    WAITING_FOR_PERSONAL_INFORMATION_CHECK("waitingForPersonalInformationCheck"),
    PERSONAL_INFORMATION_CHECK("personalInformationCheck"),
    PERSONAL_INFORMATION_CHECK_ON_CORRECTION("personalInformationCheck@onCorrection"),
    PERSONAL_INFORMATION_CHECK_REJECTION("personalInformationCheck@rejection"),
    NEED_TO_CORRECTION("needToCorrection"),
    CREDIT_BEHAVIOR_INQUIRY("creditBehaviorInquiry"),
    BOUNCED_CHEQUE_INQUIRY("bouncedChequeInquiry"),
    CENTRAL_BANK_INQUIRY("centralBankInquiry"),
    FINANCIAL_PERFORMANCE_INQUIRY("financialPerformanceInquiry"),
    VIEW_INQUIRIES_AND_VALIDATION_REQUESTS("viewInquiriesAndValidationRequests"),
    VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION("viewInquiriesAndValidationRequests@rejection"),
    REQUEST_EVALUATION("requestEvaluation"),
    REQUEST_EVALUATION_REJECTION("requestEvaluation@rejection"),
    SECONDARY_REQUEST_EVALUATION_REJECTION("secondaryRequestEvaluation@rejection"),
    IMPROVER_COVERAGE_TRUE("improverCoverage@true"),
    IMPROVER_COVERAGE_FALSE("improverCoverage@false"),
    PAYMENT("payment"),
    DELETE_DOCUMENT("deleteDocument");

    companion object {

        fun enumValueOf(type: String): DocumentsStatusEntity? {
            enumValues<DocumentsStatusEntity>().forEach {
                if (it.value == type)
                    return it
            }
            return null
        }

    }

    fun toFilesStatus(): DocumentsStatus {
        return when (this) {
            WAITING_FOR_PERSONAL_INFORMATION_CHECK -> DocumentsStatus.WAITING_FOR_PERSONAL_INFORMATION_CHECK
            PERSONAL_INFORMATION_CHECK -> DocumentsStatus.PERSONAL_INFORMATION_CHECK
            PERSONAL_INFORMATION_CHECK_ON_CORRECTION -> DocumentsStatus.PERSONAL_INFORMATION_CHECK_ON_CORRECTION
            PERSONAL_INFORMATION_CHECK_REJECTION -> DocumentsStatus.PERSONAL_INFORMATION_CHECK_REJECTION
            NEED_TO_CORRECTION -> DocumentsStatus.NEED_TO_CORRECTION
            CREDIT_BEHAVIOR_INQUIRY -> DocumentsStatus.CREDIT_BEHAVIOR_INQUIRY
            BOUNCED_CHEQUE_INQUIRY -> DocumentsStatus.BOUNCED_CHEQUE_INQUIRY
            CENTRAL_BANK_INQUIRY -> DocumentsStatus.CENTRAL_BANK_INQUIRY
            FINANCIAL_PERFORMANCE_INQUIRY -> DocumentsStatus.FINANCIAL_PERFORMANCE_INQUIRY
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS -> DocumentsStatus.VIEW_INQUIRIES_AND_VALIDATION_REQUESTS
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION -> DocumentsStatus.VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION
            REQUEST_EVALUATION -> DocumentsStatus.REQUEST_EVALUATION
            REQUEST_EVALUATION_REJECTION -> DocumentsStatus.REQUEST_EVALUATION_REJECTION
            SECONDARY_REQUEST_EVALUATION_REJECTION -> DocumentsStatus.SECONDARY_REQUEST_EVALUATION_REJECTION
            IMPROVER_COVERAGE_TRUE -> DocumentsStatus.IMPROVER_COVERAGE_TRUE
            IMPROVER_COVERAGE_FALSE -> DocumentsStatus.IMPROVER_COVERAGE_FALSE
            PAYMENT -> DocumentsStatus.PAYMENT
            DELETE_DOCUMENT -> DocumentsStatus.DELETE_DOCUMENT
        }
    }
}