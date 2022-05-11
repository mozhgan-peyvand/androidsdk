package ir.part.sdk.ara.domain.document.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PersonalInfoClub(
    var id: String = "",
    var clubName: String? = null,
    var hasGuide: Boolean? = null
)
