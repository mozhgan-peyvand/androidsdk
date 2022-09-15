package ir.part.sdk.ara.data.userManager.repositories

import ir.part.sdk.ara.data.userManager.entities.*
import ir.part.sdk.ara.model.PublicResponse
import retrofit2.Response
import retrofit2.http.*

interface UserManagerService {

    @POST
    suspend fun getLogin(
        @Url url: String,
        @Body loginParamModel: LoginParamModel
    ): Response<PublicResponse<LoginEntity>>

    @PUT
    suspend fun getForgetPassword(
        @Url url: String,
        @Body forgetPasswordParamModel: ForgetPasswordParamModel
    ): Response<Unit>

    @POST
    suspend fun getForgetPasswordVerification(
        @Url url: String,
        @Body forgetPasswordVerificationParamModel: ForgetPasswordVerificationParamModel
    ): Response<Unit>

    @GET
    suspend fun getCaptcha(@Url url: String): Response<PublicResponse<CaptchaEntity>>

    @PUT
    suspend fun getChangePassword(
        @Url url: String,
        @Header("process-instance-id") processInstanceId: String,
        @Header("task-instance-id") taskInstanceId: String,
        @Body changePasswordParamModel: ChangePasswordParamModel
    ): Response<Unit>

    @POST
    suspend fun signUp(
        @Url url: String,
        @Body data: RegisterParamModel,
    ): Response<PublicResponse<RegisterResponseNetwork>>

    @GET
    suspend fun logout(@Url url: String): Response<Unit>

}