package ir.part.sdk.ara.data.barjavand.mappers

import ir.part.sdk.ara.data.barjavand.entities.DocumentRejectRequestByUserParamModel
import ir.part.sdk.ara.domain.document.entities.DocumentRejectRequestByUserParam

fun DocumentRejectRequestByUserParam.toDocumentRejectRequestByUserParamModel() =
    DocumentRejectRequestByUserParamModel(
        fileId = fileId
    )