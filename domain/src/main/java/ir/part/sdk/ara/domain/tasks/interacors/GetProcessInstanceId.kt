package ir.part.sdk.ara.domain.tasks.interacors

//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository
import javax.inject.Inject

//@FeatureDataScope
class GetProcessInstanceId @Inject constructor(
    private val repository: BaseStateRepository
) {

    operator fun invoke(): String {
        return repository.getProcessInstanceId()
    }

}