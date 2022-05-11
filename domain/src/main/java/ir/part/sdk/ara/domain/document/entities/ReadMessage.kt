package ir.part.sdk.ara.domain.document.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable

data class ReadMessage(val hasUnreadMessage: Boolean)