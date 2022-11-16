package ir.part.sdk.ara.domain.version.interactors

//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.domain.version.entities.VersionDetail
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

//@FeatureDataScope
class GetVersionRemote @Inject constructor(
    private val repository: BarjavandRepository
) : ResultInteractor<Unit, List<VersionDetail>?>() {

    //    private external fun detect()

    override suspend fun doWork(params: Unit): InvokeStatus<List<VersionDetail>?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()
        return repository.getVersion()
    }
}