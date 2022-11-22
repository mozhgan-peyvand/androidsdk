package ir.part.sdk.ara.domain.document.interacors


import dagger.Reusable
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.SubmitReqValidationParam
import ir.part.sdk.ara.domain.document.entities.SubmitResponseValidation
import ir.part.sdk.ara.domain.document.repository.DashboardRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject


@Reusable
class SubmitReqValidationRemote @Inject constructor(
    private val repository: DashboardRepository

) : ResultInteractor<SubmitReqValidationRemote.Param, SubmitResponseValidation?>() {

//    private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<SubmitResponseValidation?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.submitReqValidation(params.submitReqValidationParam)
    }
    data class Param(val submitReqValidationParam: SubmitReqValidationParam)

}