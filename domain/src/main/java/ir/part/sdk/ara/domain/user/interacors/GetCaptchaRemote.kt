package ir.part.sdk.ara.domain.user.interacors

import dagger.Reusable
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.entities.Captcha
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

@Reusable
class GetCaptchaRemote @Inject constructor(
    private val repository: UserManagerRepository
) : ResultInteractor<Unit, Captcha?>() {

    override suspend fun doWork(params: Unit): InvokeStatus<Captcha?> {
        return repository.getCaptchaRemote()
    }

}