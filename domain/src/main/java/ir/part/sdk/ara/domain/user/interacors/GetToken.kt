package ir.part.sdk.ara.domain.user.interacors

import dagger.Reusable
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import javax.inject.Inject

@Reusable
class GetToken @Inject constructor(
    private val repository: UserManagerRepository
) {

    operator fun invoke(): String {
        return repository.getToken()
    }

}