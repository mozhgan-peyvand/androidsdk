package ir.part.sdk.ara.data.state.repositories

import ir.part.sdk.ara.data.state.entites.BaseStateResponse
import ir.part.sdk.ara.data.state.entites.DocumentStateResponse
import ir.part.sdk.ara.data.state.entites.StateEntity
import ir.part.sdk.ara.model.StateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface BaseStateService {

    @POST
    suspend fun baseStateObject(
        @Url url: String,
        @Header("username") username: String,
        @Body stateEntity: StateEntity
    ): Response<StateResponse<BaseStateResponse>>

    @POST
    suspend fun documentsStatesObjects(
        @Url url: String,
        @Header("username") username: String,
        @Body stateEntity: StateEntity
    ): Response<StateResponse<DocumentStateResponse>>
}