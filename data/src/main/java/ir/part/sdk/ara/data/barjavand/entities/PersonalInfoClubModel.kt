package ir.part.sdk.ara.data.barjavand.entities



import com.squareup.moshi.Json
import ir.part.sdk.ara.domain.document.entities.PersonalInfoClub

import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class PersonalInfoClubModel(

    @field:Json(name = "id")
    var id: String = "",
    @field:Json(name = "name")
    var clubName: String? = null,
    @field:Json(name = "hasGuide")
    var hasGuide: Boolean? = null
) {
    fun toPersonalInfoClub() = PersonalInfoClub(
        id = id,
        clubName = clubName,
        hasGuide = hasGuide
    )
}
