package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.domain.document.entities.DocumentsStatus
import ir.part.sdk.ara.ui.document.submitDocument.model.DocumentsStatusView


fun DocumentsStatus.toDocumentsStatusView(): DocumentsStatusView {
    return when (this) {
        DocumentsStatus.WAITING_FOR_PERSONAL_INFORMATION_CHECK -> DocumentsStatusView.WAITING_FOR_PERSONAL_INFORMATION_CHECK
        DocumentsStatus.PERSONAL_INFORMATION_CHECK -> DocumentsStatusView.PERSONAL_INFORMATION_CHECK
        DocumentsStatus.PERSONAL_INFORMATION_CHECK_ON_CORRECTION -> DocumentsStatusView.PERSONAL_INFORMATION_CHECK_ON_CORRECTION
        DocumentsStatus.PERSONAL_INFORMATION_CHECK_REJECTION -> DocumentsStatusView.PERSONAL_INFORMATION_CHECK_REJECTION
        DocumentsStatus.NEED_TO_CORRECTION -> DocumentsStatusView.NEED_TO_CORRECTION
        DocumentsStatus.CREDIT_BEHAVIOR_INQUIRY -> DocumentsStatusView.CREDIT_BEHAVIOR_INQUIRY
        DocumentsStatus.BOUNCED_CHEQUE_INQUIRY -> DocumentsStatusView.BOUNCED_CHEQUE_INQUIRY
        DocumentsStatus.CENTRAL_BANK_INQUIRY -> DocumentsStatusView.CENTRAL_BANK_INQUIRY
        DocumentsStatus.FINANCIAL_PERFORMANCE_INQUIRY -> DocumentsStatusView.FINANCIAL_PERFORMANCE_INQUIRY
        DocumentsStatus.VIEW_INQUIRIES_AND_VALIDATION_REQUESTS -> DocumentsStatusView.VIEW_INQUIRIES_AND_VALIDATION_REQUESTS
        DocumentsStatus.VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION -> DocumentsStatusView.VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION
        DocumentsStatus.REQUEST_EVALUATION -> DocumentsStatusView.REQUEST_EVALUATION
        DocumentsStatus.REQUEST_EVALUATION_REJECTION -> DocumentsStatusView.REQUEST_EVALUATION_REJECTION
        DocumentsStatus.SECONDARY_REQUEST_EVALUATION_REJECTION -> DocumentsStatusView.SECONDARY_REQUEST_EVALUATION_REJECTION
        DocumentsStatus.IMPROVER_COVERAGE_TRUE -> DocumentsStatusView.IMPROVER_COVERAGE_TRUE
        DocumentsStatus.IMPROVER_COVERAGE_FALSE -> DocumentsStatusView.IMPROVER_COVERAGE_FALSE
        DocumentsStatus.PAYMENT -> DocumentsStatusView.PAYMENT
        DocumentsStatus.DELETE_DOCUMENT -> DocumentsStatusView.DELETE_DOCUMENT
    }
}