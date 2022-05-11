package ir.part.sdk.ara.domain.user.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.entities.ForgetPasswordParam
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject

@FeatureDataScope
class GetForgetPasswordRemote @Inject constructor(
    private val repository: UserManagerRepository
) : SuspendingWorkInteractor<GetForgetPasswordRemote.Param, Unit>() {

//        private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Unit> {
//        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()
        return repository.forgetPasswordRemote(params.forgetPasswordParam)
    }

    data class Param(val forgetPasswordParam: ForgetPasswordParam)

}
