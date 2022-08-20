package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.common.ui.view.DateUtil
import ir.part.sdk.ara.domain.document.entities.PersonalDocumentMessage
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalDocumentMessageView


fun PersonalDocumentMessage.toPersonalDocumentMessageView(dateUtil: DateUtil) =
    PersonalDocumentMessageView(
        message = message,
        dateTime = dataTime?.let {
            if (it.length >= 8) {
                dateUtil.toDateView(dataTime)
            } else null
        } ?: "-",
        statusCode = statusCode?.toDocumentsStatusView()
    )