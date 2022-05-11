package ir.part.sdk.ara.domain.user.interacors

import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.user.entities.Captcha
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.util.SuspendingWorkInteractor
import javax.inject.Inject

@FeatureDataScope
class GetCaptchaRemote @Inject constructor(
    private val repository: UserManagerRepository
) : SuspendingWorkInteractor<Unit, Captcha?>() {

    override suspend fun doWork(params: Unit): InvokeStatus<Captcha?> {
        return repository.getCaptchaRemote()
    }

}