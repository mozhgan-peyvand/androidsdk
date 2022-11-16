package ir.part.sdk.ara.domain.menu.interactors

//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.menu.entities.Rahyar
import ir.part.sdk.ara.domain.menu.repository.MenuBarjavandRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject

//@FeatureDataScope
class GetRahyarRemote @Inject constructor(
    private val repository: MenuBarjavandRepository
) : SuspendingWorkInteractor<GetRahyarRemote.Param, List<Rahyar>?>() {
    //    private external fun detect()
    override suspend fun doWork(params: Param): InvokeStatus<List<Rahyar>?> {
//        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()
        return repository.getRahyar(params.province)
    }

    data class Param(val province: String?)

}