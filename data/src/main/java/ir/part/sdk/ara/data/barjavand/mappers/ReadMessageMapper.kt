package ir.part.sdk.ara.data.barjavand.mappers

import ir.part.sdk.ara.data.barjavand.entities.ReadMessageEntity
import ir.part.sdk.ara.domain.document.entities.ReadMessage

fun ReadMessage.toReadMessageEntity() = ReadMessageEntity(
    hasUnreadMessage = hasUnreadMessage
)