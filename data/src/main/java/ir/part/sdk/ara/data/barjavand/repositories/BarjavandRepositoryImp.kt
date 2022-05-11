package ir.part.sdk.ara.data.barjavand.repositories

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.data.barjavand.entities.PersonalDocumentsEntity
import ir.part.sdk.ara.data.barjavand.entities.PersonalInfoClubModel
import ir.part.sdk.ara.data.barjavand.entities.PersonalInfoConstantsModel
import ir.part.sdk.ara.data.barjavand.mappers.toDocumentRejectRequestByUserParamModel
import ir.part.sdk.ara.data.barjavand.mappers.toReadMessageEntity
import ir.part.sdk.ara.domain.document.entities.*
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

@FeatureDataScope
class BarjavandRepositoryImp @Inject constructor(
    private val remoteDataSource: BarjavandRemoteDataSource,
    private val requestExecutor: RequestExecutor,
) : BarjavandRepository {

    override suspend fun getPersonalInfoCLub(unions: List<String>): InvokeStatus<List<PersonalInfoClub>?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<List<PersonalInfoClubModel>>, List<PersonalInfoClub>?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<List<PersonalInfoClubModel>>> =
                remoteDataSource.getUnion(unions)

            override fun onConvertResult(data: PublicResponse<List<PersonalInfoClubModel>>): List<PersonalInfoClub>? =
                data.item?.map { it.toPersonalInfoClub() }
        })


    override suspend fun getPersonalDocumentRemote(nationalCode: String): InvokeStatus<List<PersonalDocuments>?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<List<PersonalDocumentsEntity>>, List<PersonalDocuments>?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<List<PersonalDocumentsEntity>>> =
                remoteDataSource.getDocumentOverView(nationalCode)

            override fun onConvertResult(data: PublicResponse<List<PersonalDocumentsEntity>>): List<PersonalDocuments>? =
                data.item?.map { it.toPersonalDocuments() }
        })

    override suspend fun rejectRequestByUser(documentRejectRequestByUserParam: DocumentRejectRequestByUserParam): InvokeStatus<Boolean> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<Unit, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<Unit> =
                remoteDataSource.rejectRequestByUser(
                    documentRejectRequestByUserParam.toDocumentRejectRequestByUserParamModel()
                )

            override fun onConvertResult(data: Unit): Boolean = true
        })

    override suspend fun setDisableCustomerFlag(
        fileIdNew: String,
        readMessage: ReadMessage
    ): InvokeStatus<Boolean> =

        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<Unit, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<Unit> =
                remoteDataSource.setHasUnReadMessage(
                    fileIdNew,
                    readMessage.toReadMessageEntity()
                )

            override fun onConvertResult(data: Unit): Boolean = true
        })

    override suspend fun getPersonalInfoConstants(
    ): InvokeStatus<PersonalInfoConstants?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<PersonalInfoConstantsModel>, PersonalInfoConstants?> {

            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<PersonalInfoConstantsModel>> =
                remoteDataSource.getConstant()

            override fun onConvertResult(data: PublicResponse<PersonalInfoConstantsModel>): PersonalInfoConstants? =
                data.item?.toPersonalInfoConstants()
        })


}