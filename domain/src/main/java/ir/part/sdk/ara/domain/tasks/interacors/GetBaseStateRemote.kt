package ir.part.sdk.ara.domain.tasks.interacors

//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.tasks.entities.BaseStateInfo
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

//@FeatureDataScope
class GetBaseStateRemote @Inject constructor(private val repository: BaseStateRepository) :
    ResultInteractor<Unit, BaseStateInfo?>() {

//    private external fun detect()
    override suspend fun doWork(params: Unit): InvokeStatus<BaseStateInfo?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()
        return repository.getBaseState()
    }
}