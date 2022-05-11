package ir.part.sdk.ara.domain.document.interacors


import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.PersonalInfoClub
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject


@FeatureDataScope
class GetPersonalInfoClubRemote @Inject constructor(
    private val repository: BarjavandRepository

) : SuspendingWorkInteractor<GetPersonalInfoClubRemote.Param, List<PersonalInfoClub>?>() {

//    private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<List<PersonalInfoClub>?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.getPersonalInfoCLub(params.unions)
    }
    data class Param(val unions: List<String>)

}