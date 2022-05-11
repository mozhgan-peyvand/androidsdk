package ir.part.sdk.ara.domain.user.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.entities.ChangePasswordParam
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject

@FeatureDataScope
class GetChangePasswordRemote @Inject constructor(
    private val repository: UserManagerRepository
) : SuspendingWorkInteractor<GetChangePasswordRemote.Param, Unit>() {

//        private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Unit> {
//        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()
        return repository.changePasswordRemote(params.changePasswordParam)
    }

    data class Param(val changePasswordParam: ChangePasswordParam)

}
