package ir.part.sdk.ara.domain.document.interacors


import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.ReadMessage
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject


@FeatureDataScope
class SetDisableCustomerFlagRemote @Inject constructor(
    private val repository: BarjavandRepository

) : SuspendingWorkInteractor<SetDisableCustomerFlagRemote.Param, Boolean>() {

//    private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Boolean> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.setDisableCustomerFlag(params.fileIdNew, params.readMessage)
    }
    data class Param(val fileIdNew: String, val readMessage: ReadMessage)

}