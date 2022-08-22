package ir.part.sdk.ara.data.barjavand.mappers

import ir.part.sdk.ara.data.barjavand.entities.RemoveDocumentParamRequest
import ir.part.sdk.ara.data.barjavand.entities.Schema
import ir.part.sdk.ara.data.barjavand.entities.Tag
import ir.part.sdk.ara.domain.document.entities.RemoveDocumentParam

fun RemoveDocumentParam.toRemoveDocumentParamRequest(schema: Schema, id: String, newTag: Tag) =
    RemoveDocumentParamRequest(
        schema = schema, id = id, newTags = newTag
    )