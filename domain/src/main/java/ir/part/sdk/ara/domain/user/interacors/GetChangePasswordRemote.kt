package ir.part.sdk.ara.domain.user.interacors

//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.entities.ChangePasswordParam
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

//@FeatureDataScope
class GetChangePasswordRemote @Inject constructor(
    private val repository: UserManagerRepository
) : ResultInteractor<GetChangePasswordRemote.Param, Boolean>() {

//        private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Boolean> {
//        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()
        return repository.changePasswordRemote(params.changePasswordParam)
    }

    data class Param(val changePasswordParam: ChangePasswordParam)

}
