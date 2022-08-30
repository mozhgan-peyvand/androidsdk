package ir.part.sdk.ara.data.userManager.repositories

import android.content.SharedPreferences
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.data.userManager.entities.CaptchaEntity
import ir.part.sdk.ara.data.userManager.entities.ForgetPasswordVerificationParamModel
import ir.part.sdk.ara.data.userManager.entities.RegisterResponseNetwork
import ir.part.sdk.ara.data.userManager.entities.UserManagerEntity
import ir.part.sdk.ara.data.userManager.mappers.toChangePasswordParamModel
import ir.part.sdk.ara.data.userManager.mappers.toForgetPasswordParamModel
import ir.part.sdk.ara.data.userManager.mappers.toLoginParamModel
import ir.part.sdk.ara.data.userManager.mappers.toRegisterParamModel
import ir.part.sdk.ara.domain.user.entities.*
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository
import ir.part.sdk.ara.model.PublicResponse
import ir.part.sdk.ara.util.api.RequestExecutor
import javax.inject.Inject

@FeatureDataScope
class UserManagerRepositoryImp @Inject constructor(
    private val localDataSource: UserManagerLocalDataSource,
    private val remoteDataSource: UserManagerRemoteDataSource,
    private val requestExecutor: RequestExecutor,
    @SK private val sk: String,
    private val pref: SharedPreferences,
) : UserManagerRepository {

    override suspend fun getCaptchaRemote(): InvokeStatus<Captcha?> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<PublicResponse<CaptchaEntity>, Captcha?> {
            override suspend fun onRequestCall(): InvokeStatus<PublicResponse<CaptchaEntity>> =
                remoteDataSource.captcha()

            override fun onConvertResult(data: PublicResponse<CaptchaEntity>): Captcha? =
                data.item?.toCaptcha()

        })

    override suspend fun getLoginRemote(loginParam: LoginParam): InvokeStatus<Boolean> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<UserManagerEntity, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<UserManagerEntity> {
                val result = remoteDataSource.getLogin(loginParam.toLoginParamModel())
                result.data?.let { localDataSource.saveUser(it) }
                return result
            }

            override fun onConvertResult(data: UserManagerEntity): Boolean = true

        })

    override suspend fun forgetPasswordRemote(forgetPasswordParam: ForgetPasswordParam): InvokeStatus<Boolean> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<Unit, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<Unit> =
                remoteDataSource.forgetPassword(forgetPasswordParam.toForgetPasswordParamModel())

            override fun onConvertResult(data: Unit): Boolean = true
        })

    override suspend fun changePasswordRemote(changePasswordParam: ChangePasswordParam): InvokeStatus<Boolean> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<Unit, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<Unit> {
                return remoteDataSource.changePassword(changePasswordParam.toChangePasswordParamModel())
            }
            override fun onConvertResult(data: Unit) = true
        })

    override suspend fun registerUserRemote(registerParam: RegisterParam): InvokeStatus<Boolean> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<RegisterResponseNetwork?, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<RegisterResponseNetwork?> =
                remoteDataSource.registerUser(registerParam.toRegisterParamModel())
                    .switchData { it?.item }


            override fun onConvertResult(data: RegisterResponseNetwork?) = true
        })


    override suspend fun forgetPasswordVerificationCodeRemote(
        nationalCode: String,
        verificationCode: String
    ): InvokeStatus<Boolean> =
        requestExecutor.execute(object :
            InvokeStatus.ApiEventListener<Unit, Boolean> {
            override suspend fun onRequestCall(): InvokeStatus<Unit> =
                remoteDataSource.forgetPasswordVerificationCode(
                    ForgetPasswordVerificationParamModel(nationalCode, verificationCode)
                )

            override fun onConvertResult(data: Unit): Boolean {
                return true
            }

        })


    override fun getCurrentUser(): User? {
        return localDataSource.getUser().let { it?.toUser() }
    }

    override suspend fun removeUser() {
        localDataSource.removeUsers()
    }

    override fun getNationalCode(): String {
        return localDataSource.getNationalCode()
    }

    override fun getPhoneNumber(): String {
        return localDataSource.getPhoneNumber()
    }

    override fun clearAllTables() {
        localDataSource.clearAllTables()
    }

    override suspend fun logout(): InvokeStatus<Boolean> = requestExecutor.execute(object :
        InvokeStatus.ApiEventListener<Unit, Boolean> {
        override suspend fun onRequestCall(): InvokeStatus<Unit> =
            remoteDataSource.logout()

        override fun onConvertResult(data: Unit): Boolean {
            pref.edit().remove("CurrentUserNationalCode").apply()
            pref.edit().remove("token").apply()
            pref.edit().remove("mobilePhone").apply()
            return true
        }
    })
}