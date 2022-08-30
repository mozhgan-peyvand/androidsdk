package ir.part.sdk.ara.domain.user.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import javax.inject.Inject

@FeatureDataScope
class GetPhoneNumber @Inject constructor(
    private val repository: UserManagerRepository,
) {

    operator fun invoke(): String {
        return repository.getPhoneNumber()
    }

}