package ir.part.sdk.ara.domain.user.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.Interactor
import javax.inject.Inject


@FeatureDataScope
class RemoveUser @Inject constructor(
    private val repository: UserManagerRepository
) : Interactor<Unit, Unit>() {
    override suspend fun doWork(params: Unit) {
        return repository.removeUser()
    }
}