package ir.part.sdk.ara.domain.tasks.interacors

import dagger.Reusable
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository
import javax.inject.Inject

@Reusable
class GetTaskInstanceId @Inject constructor(
    private val repository: TaskRepository,
) {

    operator fun invoke(): String {
        return repository.getTaskInstanceId()
    }

}