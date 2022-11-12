package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.domain.document.entities.PersonalInfoSubmitDocument
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoSubmitDocumentView

fun PersonalInfoSubmitDocument.toPersonalInfoSubmitDocumentView() = PersonalInfoSubmitDocumentView(
    name = name, lastname = lastName, nationalCode = nationalCode, province = province
)