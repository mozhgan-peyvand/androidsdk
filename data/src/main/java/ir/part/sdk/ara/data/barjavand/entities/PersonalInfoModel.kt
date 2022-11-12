package ir.part.sdk.ara.data.barjavand.entities


import com.squareup.moshi.Json
import ir.part.sdk.ara.domain.document.entities.PersonalInfoSubmitDocument
import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class PersonalInfoModel(
    @field:Json(name = "identity")
    var identity: IdentityEntity? = null,
    @field:Json(name = "personal")
    var personal: PersonalEntity? = null
) {
    fun toPersonalInfoSubmitDocument() = identity?.let {
        PersonalInfoSubmitDocument(
            name = it.firstName,
            lastName = it.lastName,
            nationalCode = it.nationalCode,
            province = personal?.province
        )
    }

    @JsonSerializable
    data class IdentityEntity(
        @field:Json(name = "birthDate")
        val birthDate: String?,
        @field:Json(name = "fathersName")
        val fathersName: String?,
        @field:Json(name = "firstName")
        val firstName: String?,
        @field:Json(name = "gender")
        val gender: String?,
        @field:Json(name = "lastName")
        val lastName: String?,
        @field:Json(name = "nationalCode")
        val nationalCode: String
    )

    @JsonSerializable
    data class PersonalEntity(
        @field:Json(name = "province")
        val province: Int?,
    )
}

