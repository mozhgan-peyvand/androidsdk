package ir.part.sdk.ara.domain.document.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.*

interface BarjavandRepository {
    suspend fun getPersonalDocumentRemote(): InvokeStatus<List<PersonalDocuments>?>
    suspend fun rejectRequestByUser(documentRejectRequestByUserParam: DocumentRejectRequestByUserParam): InvokeStatus<Boolean>
    suspend fun setHasUnreadMessage(
        documentId: String,
        hasUnreadMessage: Boolean
    ): InvokeStatus<Boolean>

    suspend fun getPersonalInfoConstants(): InvokeStatus<PersonalInfoConstants?>
    suspend fun getPersonalInfoClub(): InvokeStatus<List<PersonalInfoClub>?>
    suspend fun getApplicantInformationRemote(): InvokeStatus<PersonalInfoSubmitDocument?>

}