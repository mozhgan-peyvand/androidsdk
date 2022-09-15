package ir.part.sdk.ara.domain.tasks.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository
import javax.inject.Inject

@FeatureDataScope
class GetTaskInstanceId @Inject constructor(
    private val repository: TaskRepository,
) {

    operator fun invoke(): String {
        return repository.getTaskInstanceId()
    }

}