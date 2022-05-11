package ir.part.sdk.ara.domain.tasks.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.tasks.entities.Done
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject

@FeatureDataScope
class DoneTaskRemote @Inject constructor(
    private val repository: TaskRepository

) : SuspendingWorkInteractor<DoneTaskRemote.Param, Done?>() {

//    private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Done?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.requestDoneTask(params.processInstanceId, params.taskId)
    }

    data class Param(val processInstanceId: String, val taskId: String)

}