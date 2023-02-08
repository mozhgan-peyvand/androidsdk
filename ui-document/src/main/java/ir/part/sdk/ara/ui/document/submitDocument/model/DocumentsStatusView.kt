package ir.part.sdk.ara.ui.document.submitDocument.model

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import ir.part.sdk.ara.common.ui.view.*
import ir.part.sdk.ara.ui.document.R


enum class DocumentsStatusView {
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

    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            WAITING_FOR_PERSONAL_INFORMATION_CHECK -> MaterialTheme.colors.primaryVariant()
            PERSONAL_INFORMATION_CHECK -> MaterialTheme.colors.primaryVariant()
            PERSONAL_INFORMATION_CHECK_ON_CORRECTION -> MaterialTheme.colors.primaryVariant()
            PERSONAL_INFORMATION_CHECK_REJECTION -> MaterialTheme.colors.error()
            NEED_TO_CORRECTION -> MaterialTheme.colors.primaryVariant()
            CREDIT_BEHAVIOR_INQUIRY -> MaterialTheme.colors.primaryVariant()
            BOUNCED_CHEQUE_INQUIRY -> MaterialTheme.colors.primaryVariant()
            CENTRAL_BANK_INQUIRY -> MaterialTheme.colors.primaryVariant()
            FINANCIAL_PERFORMANCE_INQUIRY -> MaterialTheme.colors.primaryVariant()
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS -> MaterialTheme.colors.primaryVariant()
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION -> MaterialTheme.colors.error()
            REQUEST_EVALUATION -> MaterialTheme.colors.primaryVariant()
            REQUEST_EVALUATION_REJECTION -> MaterialTheme.colors.error()
            SECONDARY_REQUEST_EVALUATION_REJECTION -> MaterialTheme.colors.error()
            IMPROVER_COVERAGE_TRUE -> MaterialTheme.colors.success()
            IMPROVER_COVERAGE_FALSE -> MaterialTheme.colors.primaryVariant()
            PAYMENT -> MaterialTheme.colors.primaryVariant()
            DELETE_DOCUMENT -> MaterialTheme.colors.error()
        }

    val backgroundColor: Color
        @Composable
        @ReadOnlyComposable
        get() = when (this) {

            WAITING_FOR_PERSONAL_INFORMATION_CHECK -> MaterialTheme.colors.highlightBackground()
            PERSONAL_INFORMATION_CHECK -> MaterialTheme.colors.highlightBackground()
            PERSONAL_INFORMATION_CHECK_ON_CORRECTION -> MaterialTheme.colors.highlightBackground()
            PERSONAL_INFORMATION_CHECK_REJECTION -> MaterialTheme.colors.errorBackground()
            NEED_TO_CORRECTION -> MaterialTheme.colors.highlightBackground()
            CREDIT_BEHAVIOR_INQUIRY -> MaterialTheme.colors.highlightBackground()
            BOUNCED_CHEQUE_INQUIRY -> MaterialTheme.colors.highlightBackground()
            CENTRAL_BANK_INQUIRY -> MaterialTheme.colors.highlightBackground()
            FINANCIAL_PERFORMANCE_INQUIRY -> MaterialTheme.colors.highlightBackground()
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS -> MaterialTheme.colors.highlightBackground()
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION -> MaterialTheme.colors.errorBackground()
            REQUEST_EVALUATION -> MaterialTheme.colors.highlightBackground()
            REQUEST_EVALUATION_REJECTION -> MaterialTheme.colors.errorBackground()
            SECONDARY_REQUEST_EVALUATION_REJECTION -> MaterialTheme.colors.errorBackground()
            IMPROVER_COVERAGE_TRUE -> MaterialTheme.colors.successBackground()
            IMPROVER_COVERAGE_FALSE -> MaterialTheme.colors.highlightBackground()
            PAYMENT -> MaterialTheme.colors.highlightBackground()
            DELETE_DOCUMENT -> MaterialTheme.colors.errorBackground()
        }

    val icon: Painter
        @Composable
        get() = when (this) {
            WAITING_FOR_PERSONAL_INFORMATION_CHECK -> painterResource(R.drawable.ara_ic_hourglass)
            PERSONAL_INFORMATION_CHECK -> painterResource(R.drawable.ara_ic_hourglass)
            PERSONAL_INFORMATION_CHECK_ON_CORRECTION -> painterResource(R.drawable.ara_ic_hourglass)
            PERSONAL_INFORMATION_CHECK_REJECTION -> painterResource(R.drawable.merat_ic_s_warning)
            NEED_TO_CORRECTION -> painterResource(R.drawable.ara_ic_hourglass)
            CREDIT_BEHAVIOR_INQUIRY -> painterResource(R.drawable.ara_ic_hourglass)
            BOUNCED_CHEQUE_INQUIRY -> painterResource(R.drawable.ara_ic_hourglass)
            CENTRAL_BANK_INQUIRY -> painterResource(R.drawable.ara_ic_hourglass)
            FINANCIAL_PERFORMANCE_INQUIRY -> painterResource(R.drawable.ara_ic_hourglass)
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS -> painterResource(R.drawable.ara_ic_hourglass)
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION -> painterResource(R.drawable.merat_ic_s_warning)
            REQUEST_EVALUATION -> painterResource(R.drawable.ara_ic_hourglass)
            REQUEST_EVALUATION_REJECTION -> painterResource(R.drawable.merat_ic_s_warning)
            SECONDARY_REQUEST_EVALUATION_REJECTION -> painterResource(R.drawable.merat_ic_s_warning)
            IMPROVER_COVERAGE_TRUE -> painterResource(R.drawable.merat_ic_i_check)
            IMPROVER_COVERAGE_FALSE -> painterResource(R.drawable.ara_ic_hourglass)
            PAYMENT -> painterResource(R.drawable.ara_ic_hourglass)
            DELETE_DOCUMENT -> painterResource(R.drawable.merat_ic_s_warning)
        }

    val messageId: Int
        get() = when (this) {
            WAITING_FOR_PERSONAL_INFORMATION_CHECK -> R.string.ara_label_waiting_for_personal_info_check_slash_personal_info_verification
            PERSONAL_INFORMATION_CHECK -> R.string.ara_label_waiting_to_receive_inquiry_slash_receive_inquiry
            PERSONAL_INFORMATION_CHECK_ON_CORRECTION -> R.string.ara_label_waiting_for_correction_by_applicant_slash_correction_by_applicant
            PERSONAL_INFORMATION_CHECK_REJECTION -> R.string.ara_label_rejection_by_validation_expert_slash_rejection_by_validation_expert
            NEED_TO_CORRECTION -> R.string.ara_label_waiting_for_correction_verification_slash_correction_verification
            CREDIT_BEHAVIOR_INQUIRY -> R.string.ara_label_waiting_to_receive_inquiry_slash_receive_inquiry
            BOUNCED_CHEQUE_INQUIRY -> R.string.ara_label_waiting_to_receive_inquiry_slash_receive_inquiry
            CENTRAL_BANK_INQUIRY -> R.string.ara_label_waiting_to_receive_inquiry_slash_receive_inquiry
            FINANCIAL_PERFORMANCE_INQUIRY -> R.string.ara_label_waiting_to_receive_inquiry_slash_receive_inquiry
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS -> R.string.ara_label_waiting_for_document_validation_approval_slash_validation_approval
            VIEW_INQUIRIES_AND_VALIDATION_REQUESTS_REJECTION -> R.string.ara_label_rejection_by_validation_expert_slash_rejection_by_validation_expert
            REQUEST_EVALUATION -> R.string.ara_label_waiting_for_payment_slash_validation_cost_payment
            REQUEST_EVALUATION_REJECTION -> R.string.ara_label_rejection_by_validation_expert_slash_rejection_by_validation_expert
            SECONDARY_REQUEST_EVALUATION_REJECTION -> R.string.ara_label_rejection_by_validation_expert_slash_rejection_by_validation_expert
            IMPROVER_COVERAGE_TRUE -> R.string.ara_label_validation_document_completed_slash_validation_document_completed
            IMPROVER_COVERAGE_FALSE -> R.string.ara_label_waiting_to_receive_credit_improver_slash_receive_credit_improver
            PAYMENT -> R.string.ara_label_waiting_to_receive_credit_improver_slash_receive_credit_improver
            DELETE_DOCUMENT -> R.string.ara_label_request_deletion_by_applicant_slash_request_deletion_by_applicant
        }
}