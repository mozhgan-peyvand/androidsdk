package ir.part.sdk.ara.data.barjavand.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.di.scopes.FeatureDataScope
import ir.part.sdk.ara.base.exception.Exceptions
import ir.part.sdk.ara.base.model.InvokeError
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.model.InvokeSuccess
import ir.part.sdk.ara.base.model.Message
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.base.util.DocumentTasksName
import ir.part.sdk.ara.base.util.TaskStatus
import ir.part.sdk.ara.data.barjavand.entities.*
import ir.part.sdk.ara.data.barjavand.mappers.toBodyCommentEntity
import ir.part.sdk.ara.data.barjavand.mappers.toRemoveDocumentParamRequest
import ir.part.sdk.ara.data.dashboard.repositories.DashboardRemoteDataSource
import ir.part.sdk.ara.data.state.repositories.BaseStateRemoteDataSource
import ir.part.sdk.ara.data.userManager.repositories.UserManagerLocalDataSource
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
    private val barjavandLocalDataSource: BarjavandLocalDataSource,
    private val remoteDataSource: BarjavandRemoteDataSource,
    private val dashboardRemoteDataSource: DashboardRemoteDataSource,
    private val userManagerLocalDataSource: UserManagerLocalDataSource,
    private val stateRemoteDataSource: BaseStateRemoteDataSource,
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
            override suspend fun onRequestCall(): InvokeStatus<Unit> {

                val getDoingTasksResponse =
                    dashboardRemoteDataSource.getDoingTasks(listOf(("\"" + (removeDocumentParam.documentPiid) + "\"")).toString())

                if (getDoingTasksResponse is InvokeSuccess) {

                    val getDoingDeleteTask = getDoingTasksResponse.data.item?.find {
                        it.name == DocumentTasksName.DELETE_DOCUMENT.value
                    }

                    if (getDoingDeleteTask?.status == TaskStatus.DOING.value) {
                        val getDocumentsStateResponse = stateRemoteDataSource.documentsStates(
                            userManagerLocalDataSource.getNationalCode()
                        )

                        if (getDocumentsStateResponse is InvokeSuccess) {
                            val documentStateObject = getDocumentsStateResponse.data.item?.find {
                                it.processInstanceId == removeDocumentParam.documentPiid
                            }

                            val documentStateLastTaskStatus =
                                documentStateObject?.tasks?.toList()?.lastOrNull()?.second?.status
                            if (documentStateLastTaskStatus == TaskStatus.DOING.value) {
                                //del doc req and if success done

                                val deleteDocumentResponse = remoteDataSource.removeDocument(
                                    removeDocumentParam.toRemoveDocumentParamRequest(
                                        id = "${
                                            pref.getString(
                                                "CurrentUserNationalCode",
                                                null
                                            )?.let {
                                                AesEncryptor().decrypt(it, sk)
                                            } ?: ""
                                        }-${removeDocumentParam.documentId}",
                                        schema = Schema(name = "document", version = "1.0.0"),
                                        newTag = Tag(archive = "true"),
                                    )
                                )

                                if (deleteDocumentResponse is InvokeSuccess) {
                                    //done
                                    val doneResponse = dashboardRemoteDataSource.doneTask(
                                        processInstanceId = removeDocumentParam.documentPiid,
                                        taskId = getDoingDeleteTask.taskId ?: "",
                                        taskInstanceId = getDoingDeleteTask.id ?: "",
                                        taskName = getDoingDeleteTask.name ?: ""
                                    )

                                    if (doneResponse is InvokeSuccess) {
                                        return InvokeSuccess(Unit)
                                    } else {
                                        //return error
                                        return InvokeError(
                                            exception = Exceptions.RemoteDataSourceException(
                                                message = Message(
                                                    fa = "",
                                                    en = "error delete document done request"
                                                )
                                            )
                                        )
                                    }


                                } else {
                                    // return error deleting doc
                                    return InvokeError(
                                        exception = Exceptions.RemoteDataSourceException(
                                            message = Message(
                                                fa = "",
                                                en = "error delete document request"
                                            )
                                        )
                                    )

                                }

                            } else if (documentStateLastTaskStatus == TaskStatus.UNDONE.value) {
                                // done req
                                val doneResponse = dashboardRemoteDataSource.doneTask(
                                    processInstanceId = removeDocumentParam.documentPiid,
                                    taskId = getDoingDeleteTask.taskId ?: "",
                                    taskInstanceId = getDoingDeleteTask.id ?: "",
                                    taskName = getDoingDeleteTask.name ?: ""
                                )

                                if (doneResponse is InvokeSuccess) {
                                    return InvokeSuccess(Unit)
                                } else {
                                    // return error
                                    return InvokeError(
                                        exception = Exceptions.RemoteDataSourceException(
                                            message = Message(
                                                fa = "",
                                                en = "error delete document done request"
                                            )
                                        )
                                    )
                                }
                            }
                        }

                    } else {
                        val getDocumentTasksResponse = dashboardRemoteDataSource.getDocumentTasks(
                            removeDocumentParam.documentPiid,
                            userManagerLocalDataSource.getNationalCode()
                        )

                        if (getDocumentTasksResponse is InvokeSuccess) {
                            val documentDeleteDocumentTask =
                                getDocumentTasksResponse.data.item?.find {
                                    it.name == DocumentTasksName.DELETE_DOCUMENT.value
                                }

                            documentDeleteDocumentTask?.let {
                                // doing and if success request and done
                                val doingDocumentTask = dashboardRemoteDataSource.doingTask(
                                    processInstanceId = removeDocumentParam.documentPiid,
                                    taskId = documentDeleteDocumentTask.taskId ?: "",
                                    taskInstanceId = documentDeleteDocumentTask.id ?: "",
                                    taskName = documentDeleteDocumentTask.name ?: ""
                                )

                                if (doingDocumentTask is InvokeSuccess) {
                                    val deleteDocumentResponse = remoteDataSource.removeDocument(
                                        removeDocumentParam.toRemoveDocumentParamRequest(
                                            id = "${
                                                pref.getString(
                                                    "CurrentUserNationalCode",
                                                    null
                                                )?.let {
                                                    AesEncryptor().decrypt(it, sk)
                                                } ?: ""
                                            }-${removeDocumentParam.documentId}",
                                            schema = Schema(name = "document", version = "1.0.0"),
                                            newTag = Tag(archive = "true"),
                                        )
                                    )

                                    if (deleteDocumentResponse is InvokeSuccess) {
                                        //done
                                        val doneResponse = dashboardRemoteDataSource.doneTask(
                                            processInstanceId = removeDocumentParam.documentPiid,
                                            taskId = documentDeleteDocumentTask.taskId ?: "",
                                            taskInstanceId = documentDeleteDocumentTask.id ?: "",
                                            taskName = documentDeleteDocumentTask.name ?: ""
                                        )

                                        if (doneResponse is InvokeSuccess) {
                                            return InvokeSuccess(Unit)

                                        } else {
                                            // return error
                                            return InvokeError(
                                                exception = Exceptions.RemoteDataSourceException(
                                                    message = Message(
                                                        fa = "",
                                                        en = "error delete document done request"
                                                    )
                                                )
                                            )
                                        }


                                    } else {
                                        // return error deleting doc
                                        return InvokeError(
                                            exception = Exceptions.RemoteDataSourceException(
                                                message = Message(
                                                    fa = "",
                                                    en = "error delete document request"
                                                )
                                            )
                                        )
                                    }


                                } else {
                                    // return error
                                    return InvokeError(
                                        exception = Exceptions.RemoteDataSourceException(
                                            message = Message(
                                                fa = "",
                                                en = "error delete document doing request"
                                            )
                                        )
                                    )
                                }
                            }

                        } else {
                            //return error
                            return InvokeError(
                                exception = Exceptions.RemoteDataSourceException(
                                    message = Message(
                                        fa = "",
                                        en = "error delete document get tasks request"
                                    )
                                )
                            )
                        }

                    }
                } else {
                    // return error
                    return InvokeError(
                        exception = Exceptions.RemoteDataSourceException(
                            message = Message(
                                fa = "",
                                en = "error delete document get doing tasks request"
                            )
                        )
                    )
                }

                return InvokeError(
                    exception = Exceptions.RemoteDataSourceException(
                        message = Message(
                            fa = "",
                            en = "error delete document repository function"
                        )
                    )
                )

            }

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

    override fun getLastShownUpdateVersion(): Int? =
        barjavandLocalDataSource.getLastShownUpdateVersion()

    override fun saveLastShownUpdateVersion(version: Int?) {
        barjavandLocalDataSource.saveLastShownUpdateVersion(version)
    }


}