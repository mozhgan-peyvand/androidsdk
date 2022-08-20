package ir.part.sdk.ara.domain.document.interacors


import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject


@FeatureDataScope
class SetHasUnreadMessageRemote @Inject constructor(
    private val repository: BarjavandRepository

) : ResultInteractor<SetHasUnreadMessageRemote.Param, Boolean>() {

//    private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Boolean> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.setHasUnreadMessage(params.documentId, params.hasUnreadMessage)
    }

    data class Param(val documentId: String, val hasUnreadMessage: Boolean)

}