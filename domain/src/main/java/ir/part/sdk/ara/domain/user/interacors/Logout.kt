package ir.part.sdk.ara.domain.user.interacors

//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject


//@FeatureDataScope
class Logout @Inject constructor(
    private val repository: UserManagerRepository,
) : ResultInteractor<Unit, Boolean>() {

//    private external fun detect()

    override suspend fun doWork(params: Unit): InvokeStatus<Boolean> {

//                val job = GlobalScope.launch {
//            System.load("main-lib")
//            detect()
//        }
//        job.join()
        return repository.logout()
    }

}