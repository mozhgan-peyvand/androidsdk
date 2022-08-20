package ir.part.sdk.ara.ui.document.overviewDocument.model

import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoConstantsView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoSubmitDocumentView

class OverviewDocumentView(
    val personalInfoConstantsItem: PersonalInfoConstantsView? = null,
    val personalInfoSubmitDocumentView: PersonalInfoSubmitDocumentView? = null,
) {
    companion object {
        val Empty = OverviewDocumentView()
    }
}