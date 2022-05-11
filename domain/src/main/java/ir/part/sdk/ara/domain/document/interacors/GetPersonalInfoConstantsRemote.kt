package ir.part.sdk.ara.domain.document.interacors


import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.PersonalInfoConstants
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject


@FeatureDataScope
class GetPersonalInfoConstantsRemote @Inject constructor(
    private val repository: BarjavandRepository

) : SuspendingWorkInteractor<Unit, PersonalInfoConstants?>() {

//    private external fun detect()

    override suspend fun doWork(params: Unit): InvokeStatus<PersonalInfoConstants?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.getPersonalInfoConstants()
    }


}