package ir.part.sdk.ara.data.state.mappers

import ir.part.sdk.ara.data.barjavand.entities.DocumentsStatusEntity
import ir.part.sdk.ara.data.state.entites.DocumentStateResponse
import ir.part.sdk.ara.domain.document.entities.DocumentState

fun DocumentStateResponse.toDocumentState() = DocumentState(
    processType = processType,
    processId = processId,
    processInstanceId = processInstanceId,
    status = status?.let {
        DocumentsStatusEntity.enumValueOf(it)?.toFilesStatus()
    }
)