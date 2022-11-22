package ir.part.sdk.ara.domain.tasks.interacors

import dagger.Reusable
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.tasks.entities.TaskInfo
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

@Reusable
class GetDoingTasksRemote @Inject constructor(
    private val repository: TaskRepository

) : ResultInteractor<Unit, TaskInfo?>() {

//    private external fun detect()

    override suspend fun doWork(params: Unit): InvokeStatus<TaskInfo?> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return repository.requestGetDoingTasks()
    }
}