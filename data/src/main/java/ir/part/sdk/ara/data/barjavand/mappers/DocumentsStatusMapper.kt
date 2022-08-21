package ir.part.sdk.ara.data.barjavand.mappers

import ir.part.sdk.ara.data.barjavand.entities.DocumentsStatusEntity
import ir.part.sdk.ara.domain.document.entities.DocumentsStatus

fun DocumentsStatus.toFilesStatusEntity(): DocumentsStatusEntity {
    return when (this) {
        DocumentsStatus.CODE_11 -> DocumentsStatusEntity.CODE_11
        DocumentsStatus.CODE_11_3 -> DocumentsStatusEntity.CODE_11_3
        DocumentsStatus.CODE_11_6 -> DocumentsStatusEntity.CODE_11_6
        DocumentsStatus.CODE_12 -> DocumentsStatusEntity.CODE_12
        DocumentsStatus.CODE_12_3 -> DocumentsStatusEntity.CODE_12_3
        DocumentsStatus.CODE_12_6 -> DocumentsStatusEntity.CODE_12_6
        DocumentsStatus.CODE_13 -> DocumentsStatusEntity.CODE_13
        DocumentsStatus.CODE_14 -> DocumentsStatusEntity.CODE_14
        DocumentsStatus.CODE_18 -> DocumentsStatusEntity.CODE_18
        DocumentsStatus.CODE_21 -> DocumentsStatusEntity.CODE_21
        DocumentsStatus.CODE_31 -> DocumentsStatusEntity.CODE_31
        DocumentsStatus.CODE_100 -> DocumentsStatusEntity.CODE_100
        DocumentsStatus.CODE_200 -> DocumentsStatusEntity.CODE_200
        DocumentsStatus.CODE_300 -> DocumentsStatusEntity.CODE_300
        DocumentsStatus.CODE_400 -> DocumentsStatusEntity.CODE_400
    }
}