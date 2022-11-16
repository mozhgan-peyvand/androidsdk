package ir.part.sdk.ara.domain.user.interacors

//import ir.part.sdk.ara.base.di.FeatureDataScope

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

//@FeatureDataScope
class GetForgetPasswordVerificationRemote @Inject constructor(
    private val repository: UserManagerRepository
) : ResultInteractor<GetForgetPasswordVerificationRemote.Param, Boolean>() {

//    private external fun detect()
    data class Param(val nationalCode: String, val verificationCode: String)

    override suspend fun doWork(params: Param): InvokeStatus<Boolean> {
//        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()
        return repository.forgetPasswordVerificationCodeRemote(
            params.nationalCode,
            params.verificationCode
        )
    }
}





