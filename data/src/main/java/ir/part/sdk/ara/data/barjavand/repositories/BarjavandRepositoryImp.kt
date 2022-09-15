package ir.part.sdk.ara.data.barjavand.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.data.barjavand.entities.*
import ir.part.sdk.ara.data.barjavand.mappers.toBodyCommentEntity
import ir.part.sdk.ara.data.barjavand.mappers.toRemoveDocumentParamRequest
import ir.part.sdk.ara.domain.document.entities.*
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.domain.menu.entities.BodyComment
import ir.part.sdk.ara.domain.menu.entities.Rahyar
import ir.part.sdk.ara.domain.menu.repository.MenuBarjavandRepository
import ir.part.sdk.ara.domain.version.entities.VersionDetail
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.model.PublicResponseData
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

@FeatureDataScope
class BarjavandRepositoryImp @Inject constructor(
    private val remoteDataSource: BarjavandRemoteDataSource,
    private val requestExecutor: RequestExecutor,
    @SK private val sk: String,
    private val pref: SharedPreferences,
) : BarjavandRepository, MenuBarjavandRepository {

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

    override suspend fun rejectRequestByUser(removeDocumentParam: RemoveDocumentParam): InvokeStatus<Boolean> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<Unit, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<Unit> =
                remoteDataSource.removeDocument(
                    removeDocumentParam.toRemoveDocumentParamRequest(
                        id = "${
                            pref.getString(
                                "CurrentUserNationalCode",
                                null
                            )?.let {
                                AesEncryptor().decrypt(it, sk)
                            } ?: ""
                        }-${removeDocumentParam.documentId.toString()}",
                        schema = Schema(name = "document", version = "1.0.0"),
                        newTag = Tag(archive = "true"),
                    )
                )

            override fun onConvertResult(data: Unit): Boolean = true
        })

    override suspend fun setHasUnreadMessage(
        documentId: String,
        hasUnreadMessage: Boolean,
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

    override suspend fun submitComment(bodyComment: BodyComment): InvokeStatus<Boolean> =
        requestExecutor.execute(object : InvokeStatus.ApiEventListener<Unit, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<Unit> =
                remoteDataSource.submitComment(
                    bodyComment.toBodyCommentEntity(),
                    bodyComment.captchaToken,
                    bodyComment.captchaValue
                )

            override fun onConvertResult(data: Unit): Boolean {
                return true
            }
        })

    override suspend fun getRahyar(province: String?): InvokeStatus<List<Rahyar>?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<List<RahyarModel>>, List<Rahyar>?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<List<RahyarModel>>> =
                remoteDataSource.getRahyar(province)

            override fun onConvertResult(data: PublicResponse<List<RahyarModel>>): List<Rahyar>? =
                data.item?.map { it.toRahyar() }
        })

    override suspend fun getVersion(): InvokeStatus<List<VersionDetail>?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<PublicResponseData<BarjavandResultEntity<VersionDetailModel>>>, List<VersionDetail>?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<PublicResponseData<BarjavandResultEntity<VersionDetailModel>>>> =
                remoteDataSource.getVersion()

            override fun onConvertResult(data: PublicResponse<PublicResponseData<BarjavandResultEntity<VersionDetailModel>>>): List<VersionDetail>? =
                data.item?.results?.map {
                    it.data.toVersionDetail()
                }
        })

}