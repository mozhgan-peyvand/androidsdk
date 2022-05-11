package ir.part.sdk.ara.domain.document.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.*

interface BarjavandRepository {
    suspend fun getPersonalDocumentRemote(nationalCode: String): InvokeStatus<List<PersonalDocuments>?>
    suspend fun rejectRequestByUser(documentRejectRequestByUserParam: DocumentRejectRequestByUserParam): InvokeStatus<Boolean>
    suspend fun setDisableCustomerFlag(
        fileIdNew: String,
        readMessage: ReadMessage
    ): InvokeStatus<Boolean>

    suspend fun getPersonalInfoConstants(): InvokeStatus<PersonalInfoConstants?>
    suspend fun getPersonalInfoCLub(unions: List<String>): InvokeStatus<List<PersonalInfoClub>?>

}