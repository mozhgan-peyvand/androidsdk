package ir.part.sdk.ara.domain.user.interacors

import ir.part.sdk.ara.base.di.DomainScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.base.model.InvokeSuccess
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject

@DomainScope
class GetLogoutRemote @Inject constructor(private val repository: UserManagerRepository) :
    SuspendingWorkInteractor<Unit, Unit>() {

    override suspend fun doWork(params: Unit): InvokeStatus<Unit> {
        return InvokeSuccess(Unit)
    }
}