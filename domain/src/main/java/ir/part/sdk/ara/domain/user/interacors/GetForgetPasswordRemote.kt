package ir.part.sdk.ara.domain.user.interacors

import dagger.Reusable
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.entities.ForgetPasswordParam
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

@Reusable
class GetForgetPasswordRemote @Inject constructor(
    private val repository: UserManagerRepository
) : ResultInteractor<GetForgetPasswordRemote.Param, Boolean>() {

//        private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Boolean> {
//        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()
        return repository.forgetPasswordRemote(params.forgetPasswordParam)
    }

    data class Param(val forgetPasswordParam: ForgetPasswordParam)

}
