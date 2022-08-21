package ir.part.sdk.ara.domain.document.interacors


import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.PersonalDocuments
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject


@FeatureDataScope
class GetPersonalDocumentRemote @Inject constructor(
    private val repository: BarjavandRepository

) : ResultInteractor<Unit, List<PersonalDocuments>?>() {

//    private external fun detect()

    override suspend fun doWork(params: Unit): InvokeStatus<List<PersonalDocuments>?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.getPersonalDocumentRemote()
    }
}