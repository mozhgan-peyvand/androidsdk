package ir.part.sdk.ara.builder.entities

import ir.part.sdk.ara.domain.document.entities.PersonalDocuments
import ir.part.sdk.ara.domain.document.entities.PersonalInfoClub
import ir.part.sdk.ara.domain.document.entities.PersonalInfoConstants
import ir.part.sdk.ara.domain.tasks.entities.BaseStateInfo

data class RegisterView(
    val userId: String? = null,
)

data class AraState(
    val register: RegisterView? = null,
    val PersonalDocumentList: List<PersonalDocuments>? = null,
    val getRejectRequestByUserRemote: Boolean? = null,
    val getPersonalInfoConstantsRemote: PersonalInfoConstants? = null,
    val getPersonalInfoClubRemote: PersonalInfoClub? = null,
    val setDisableCustomerFlagRemote: Boolean? = null,
    val getBaseStateRemote: BaseStateInfo? = null
) {
    companion object {
        val Empty = AraState()
    }
}