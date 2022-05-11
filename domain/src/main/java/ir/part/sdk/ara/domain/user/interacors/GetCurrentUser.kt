package ir.part.sdk.ara.domain.user.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.domain.user.entities.User
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.Interactor
import javax.inject.Inject

@FeatureDataScope
class GetCurrentUser @Inject constructor(
    private val repository: UserManagerRepository
) : Interactor<Unit, User?>() {

    override suspend fun doWork(params: Unit): User? {
        return repository.getCurrentUser()
    }
}