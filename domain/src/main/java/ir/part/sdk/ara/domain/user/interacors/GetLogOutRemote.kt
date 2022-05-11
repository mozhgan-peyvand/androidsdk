package ir.part.sdk.ara.domain.user.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject

@FeatureDataScope
class GetLogOutRemote @Inject constructor(
    private val repository: UserManagerRepository
) : SuspendingWorkInteractor<Unit, Boolean>() {
    override suspend fun doWork(params: Unit): InvokeStatus<Boolean> {
        return repository.getLogOut()
    }
}