package ir.part.sdk.ara.domain.document.interacors


//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.RemoveDocumentParam
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject


//@FeatureDataScope
class SetRemoveDocumentRemote @Inject constructor(
    private val repository: BarjavandRepository

) : ResultInteractor<SetRemoveDocumentRemote.Param, Boolean>() {

//    private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Boolean> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.rejectRequestByUser(params.removeDocumentParam)
    }

    data class Param(val removeDocumentParam: RemoveDocumentParam)

}