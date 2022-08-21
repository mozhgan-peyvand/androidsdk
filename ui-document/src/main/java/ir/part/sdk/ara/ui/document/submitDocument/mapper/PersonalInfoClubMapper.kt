package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.domain.document.entities.PersonalInfoClub
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoClubView

fun PersonalInfoClub.toPersonalInfoClubView() = PersonalInfoClubView(
    id = id,
    clubName = clubName,
    hasGuide = hasGuide
)