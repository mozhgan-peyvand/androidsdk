package ir.part.sdk.ara.domain.menu.interactors

import dagger.Reusable
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.menu.entities.BodyComment
import ir.part.sdk.ara.domain.menu.repository.MenuBarjavandRepository
import ir.part.sdk.ara.util.ResultInteractor
import javax.inject.Inject

@Reusable
class SubmitCommentRemote @Inject constructor(
    private val commentBarjavandRepository: MenuBarjavandRepository
) : ResultInteractor<SubmitCommentRemote.Param, Boolean>() {

    //    private external fun detect()

    override suspend fun doWork(params: Param): InvokeStatus<Boolean> {
        //        val job = GlobalScope.launch {
//            System.loadLibrary("main-lib")
//            detect()
//        }
//        job.join()

        return commentBarjavandRepository.submitComment(params.bodyComment)
    }

    data class Param(val bodyComment: BodyComment)
}
