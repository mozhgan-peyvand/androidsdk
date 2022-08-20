package ir.part.sdk.ara.data.barjavand.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.data.barjavand.entities.*
import ir.part.sdk.ara.data.barjavand.mappers.toDocumentRejectRequestByUserParamModel
import ir.part.sdk.ara.domain.document.entities.*
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.model.PublicResponseData
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

@FeatureDataScope
class BarjavandRepositoryImp @Inject constructor(
    private val remoteDataSource: BarjavandRemoteDataSource,
    private val requestExecutor: RequestExecutor,
    @SK private val sk: String,
    private val pref: SharedPreferences
) : BarjavandRepository {

    override suspend fun getPersonalInfoClub(): InvokeStatus<List<PersonalInfoClub>?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoClubModel>>>, List<PersonalInfoClub>?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoClubModel>>>> =
                remoteDataSource.getUnion()

            override fun onConvertResult(data: PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoClubModel>>>): List<PersonalInfoClub>? =
                data.item?.results?.map {
                    it.data.toPersonalInfoClub()
                }
        })

    override suspend fun getApplicantInformationRemote(): InvokeStatus<PersonalInfoSubmitDocument?> =

        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoModel>>>, PersonalInfoSubmitDocument?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoModel>>>> =
                remoteDataSource.getApplicantInformation(
                    pref.getString(
                        "CurrentUserNationalCode",
                        null
                    )?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: "")

            override fun onConvertResult(data: PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoModel>>>): PersonalInfoSubmitDocument? =
                data.item?.results?.firstOrNull()?.data?.toPersonalInfoSubmitDocument()
        })


    override suspend fun getPersonalDocumentRemote(): InvokeStatus<List<PersonalDocuments>?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalDocumentsEntity>>>, List<PersonalDocuments>?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalDocumentsEntity>>>> =
                remoteDataSource.getDocumentOverView(
                    pref.getString(
                        "CurrentUserNationalCode",
                        null
                    )?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: ""
                )

            override fun onConvertResult(data: PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalDocumentsEntity>>>): List<PersonalDocuments>? =
                data.item?.results?.map {
                    it.data.toPersonalDocuments()
                }

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

    override suspend fun setHasUnreadMessage(
        documentId: String,
        hasUnreadMessage: Boolean
    ): InvokeStatus<Boolean> =

        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<Unit, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<Unit> =
                remoteDataSource.setHasUnReadMessage(
                    documentId = documentId,
                    hasUnreadMessage = hasUnreadMessage,
                    nationalCode = pref.getString(
                        "CurrentUserNationalCode",
                        null
                    )?.let {
                        AesEncryptor().decrypt(it, sk)
                    } ?: ""
                )

            override fun onConvertResult(data: Unit): Boolean = true
        })

    override suspend fun getPersonalInfoConstants(
    ): InvokeStatus<PersonalInfoConstants?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoConstantsModel>>>, PersonalInfoConstants?> {

            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoConstantsModel>>>> =
                remoteDataSource.getConstant()

            override fun onConvertResult(data: PublicResponse<PublicResponseData<BarjavandResultEntity<PersonalInfoConstantsModel>>>): PersonalInfoConstants? =
                data.item?.results?.firstOrNull()?.data?.toPersonalInfoConstants()
        })


}