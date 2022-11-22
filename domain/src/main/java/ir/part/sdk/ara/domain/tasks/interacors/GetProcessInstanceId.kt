package ir.part.sdk.ara.domain.tasks.interacors

import dagger.Reusable
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository
import javax.inject.Inject

@Reusable
class GetProcessInstanceId @Inject constructor(
    private val repository: BaseStateRepository
) {

    operator fun invoke(): String {
        return repository.getProcessInstanceId()
    }

}