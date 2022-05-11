package ir.part.sdk.ara.data.barjavand.mappers

import ir.part.sdk.ara.data.barjavand.entities.DocumentsStatusEntity
import ir.part.sdk.ara.domain.document.entities.DocumentsStatus

fun DocumentsStatus.toFilesStatusEntity(): DocumentsStatusEntity {
    return when (this) {
        DocumentsStatus.Code_11 -> DocumentsStatusEntity.Code_11
        DocumentsStatus.Code_11_3 -> DocumentsStatusEntity.Code_11_3
        DocumentsStatus.Code_11_6 -> DocumentsStatusEntity.Code_11_6
        DocumentsStatus.Code_12 -> DocumentsStatusEntity.Code_12
        DocumentsStatus.Code_12_3 -> DocumentsStatusEntity.Code_12_3
        DocumentsStatus.Code_12_6 -> DocumentsStatusEntity.Code_12_6
        DocumentsStatus.Code_13 -> DocumentsStatusEntity.Code_13
        DocumentsStatus.Code_14 -> DocumentsStatusEntity.Code_14
        DocumentsStatus.Code_18 -> DocumentsStatusEntity.Code_18
        DocumentsStatus.Code_21 -> DocumentsStatusEntity.Code_21
        DocumentsStatus.Code_31 -> DocumentsStatusEntity.Code_31
        DocumentsStatus.Code_100 -> DocumentsStatusEntity.Code_100
        DocumentsStatus.Code_200 -> DocumentsStatusEntity.Code_200
        DocumentsStatus.Code_300 -> DocumentsStatusEntity.Code_300
        DocumentsStatus.Code_400 -> DocumentsStatusEntity.Code_400
    }
}