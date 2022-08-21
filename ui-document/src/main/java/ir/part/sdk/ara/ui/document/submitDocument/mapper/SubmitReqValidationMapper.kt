package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.domain.document.entities.SubmitResponseValidation
import ir.part.sdk.ara.ui.document.submitDocument.model.SubmitResponseValidationView

fun SubmitResponseValidation.toSubmitResponseValidationView() = SubmitResponseValidationView(
    documentSerialNumber = documentSerialNumber
)