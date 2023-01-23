package ir.part.sdk.ara.domain.document.interacors

import dagger.Reusable
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.DocumentState
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

@Reusable
class GetDocumentsStatesRemote @Inject constructor(
    private val repository: BaseStateRepository
) : ResultInteractor<Unit, List<DocumentState>?>() {

//    private external fun detect()

    override suspend fun doWork(params: Unit): InvokeStatus<List<DocumentState>?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.getDocumentsStates()
    }

}