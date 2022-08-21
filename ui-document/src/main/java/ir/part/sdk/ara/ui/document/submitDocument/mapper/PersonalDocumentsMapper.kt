package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.common.ui.view.DateUtil
import ir.part.sdk.ara.domain.document.entities.PersonalDocuments
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalDocumentsView

fun PersonalDocuments.toPersonalDocumentsView(dateUtil: DateUtil) = PersonalDocumentsView(
    amount = amount,
    fileIdNew = fileIdNew,
    processInstanceId = processInstanceId,
    fileId = fileId,
    applicantUsername = applicantUsername,
    unionId = unionId,
    messageList = messageList?.map { it.toPersonalDocumentMessageView(dateUtil) },
    hasUnreadMessage = hasUnreadMessage,
    validationResult = validationResult?.toDocumentResultValidationView(dateUtil),
    statusId = statusId?.toDocumentsStatusView(),
    requestDateView = createdAt?.let {
        if (it.length >= 8) {
            dateUtil.toDateView(createdAt)
        } else null
    } ?: "-",
    firstName = firstName,
    lastName = lastName,
    statusName = statusName,
    status = statusTitle,
    completionDate = messageList?.lastOrNull()?.dataTime?.let {
        if (it.length >= 8) {
            dateUtil.toDateView(it)
        } else null
    } ?: "-",
    completionDateView = messageList?.lastOrNull()?.dataTime?.let {
        if (it.length >= 8) {
            dateUtil.toDateView(it)
        } else null
    } ?: "-",
    showValidationProperties =
    listOf(
        validationResult?.perfectProportionate,
        validationResult?.proportionate,
        validationResult?.commitmentCeiling,
        validationResult?.disproportionate,
        validationResult?.deptCeiling,
        validationResult?.commitmentPrice,
        validationResult?.expireDate,
        validationResult?.chartData
    ).none { it != null }

)