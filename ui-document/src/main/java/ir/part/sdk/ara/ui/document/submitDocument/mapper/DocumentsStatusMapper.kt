package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.domain.document.entities.DocumentsStatus
import ir.part.sdk.ara.ui.document.submitDocument.model.DocumentsStatusView


fun DocumentsStatus.toDocumentsStatusView(): DocumentsStatusView {
    return when (this) {
        DocumentsStatus.CODE_11 -> DocumentsStatusView.CODE_11
        DocumentsStatus.CODE_11_3 -> DocumentsStatusView.CODE_11_3
        DocumentsStatus.CODE_11_6 -> DocumentsStatusView.CODE_11_6
        DocumentsStatus.CODE_12 -> DocumentsStatusView.CODE_12
        DocumentsStatus.CODE_12_3 -> DocumentsStatusView.CODE_12_3
        DocumentsStatus.CODE_12_6 -> DocumentsStatusView.CODE_12_6
        DocumentsStatus.CODE_13 -> DocumentsStatusView.CODE_13
        DocumentsStatus.CODE_14 -> DocumentsStatusView.CODE_14
        DocumentsStatus.CODE_18 -> DocumentsStatusView.CODE_18
        DocumentsStatus.CODE_21 -> DocumentsStatusView.CODE_21
        DocumentsStatus.CODE_31 -> DocumentsStatusView.CODE_31
        DocumentsStatus.CODE_100 -> DocumentsStatusView.CODE_100
        DocumentsStatus.CODE_200 -> DocumentsStatusView.CODE_200
        DocumentsStatus.CODE_300 -> DocumentsStatusView.CODE_300
        DocumentsStatus.CODE_400 -> DocumentsStatusView.CODE_400
    }
}