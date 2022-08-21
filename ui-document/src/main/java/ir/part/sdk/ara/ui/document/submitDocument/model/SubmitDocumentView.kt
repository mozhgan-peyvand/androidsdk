package ir.part.sdk.ara.ui.document.submitDocument.model

class SubmitDocumentView(
    val personalInfoClubView: List<PersonalInfoClubView>? = null,
    val personalInfoSubmitDocumentView: PersonalInfoSubmitDocumentView? = null
) {
    companion object {
        val Empty = SubmitDocumentView()
    }
}