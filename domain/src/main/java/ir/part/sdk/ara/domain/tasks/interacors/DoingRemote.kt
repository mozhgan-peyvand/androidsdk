package ir.part.sdk.ara.domain.tasks.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

@FeatureDataScope
class DoingRemote @Inject constructor(
    private val repository: TaskRepository

) : ResultInteractor<Unit, String?>() {

//    private external fun detect()

    override suspend fun doWork(params: Unit): InvokeStatus<String?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.requestDoingTask()
    }


}