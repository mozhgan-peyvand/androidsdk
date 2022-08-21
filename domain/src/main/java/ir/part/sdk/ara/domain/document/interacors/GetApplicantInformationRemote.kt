package ir.part.sdk.ara.domain.document.interacors


import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.PersonalInfoSubmitDocument
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject


@FeatureDataScope
class GetApplicantInformationRemote @Inject constructor(
    private val repository: BarjavandRepository

) : SuspendingWorkInteractor<Unit, PersonalInfoSubmitDocument?>() {

//    private external fun detect()

    override suspend fun doWork(params: Unit): InvokeStatus<PersonalInfoSubmitDocument?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.getApplicantInformationRemote()
    }


}