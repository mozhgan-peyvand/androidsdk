package ir.part.sdk.ara.domain.menu.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.menu.entities.BodyComment
import ir.part.sdk.ara.domain.menu.entities.Rahyar

interface MenuBarjavandRepository {

    suspend fun submitComment(bodyComment: BodyComment): InvokeStatus<Boolean>
    suspend fun getRahyar(province: String?): InvokeStatus<List<Rahyar>?>

}