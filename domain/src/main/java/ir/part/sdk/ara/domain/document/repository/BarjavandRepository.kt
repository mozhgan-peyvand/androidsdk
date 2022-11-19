package ir.part.sdk.ara.domain.document.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.*
import ir.part.sdk.ara.domain.version.entities.VersionDetail

interface BarjavandRepository {
    suspend fun getPersonalDocumentRemote(): InvokeStatus<List<PersonalDocuments>?>
    suspend fun rejectRequestByUser(removeDocumentParam: RemoveDocumentParam): InvokeStatus<Boolean>
    suspend fun setHasUnreadMessage(
        documentId: String,
        hasUnreadMessage: Boolean
    ): InvokeStatus<Boolean>

    suspend fun getPersonalInfoConstants(): InvokeStatus<PersonalInfoConstants?>
    suspend fun getPersonalInfoClub(): InvokeStatus<List<PersonalInfoClub>?>
    suspend fun getApplicantInformationRemote(): InvokeStatus<PersonalInfoSubmitDocument?>
    suspend fun getVersion(): InvokeStatus<List<VersionDetail>?>
    fun getLastShownUpdateVersion(): Int?
    fun saveLastShownUpdateVersion(version: Int?)

}