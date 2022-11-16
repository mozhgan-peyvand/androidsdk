package ir.part.sdk.ara.di

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import ir.part.sdk.ara.base.di.DK
import ir.part.sdk.ara.base.di.SK
import ir.part.sdk.ara.base.util.AesEncryptor
import ir.part.sdk.ara.data.BuildConfig
import ir.part.sdk.ara.util.CustomInterceptor
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Suppress("unused")
@Module
class NetworkModule {

    private fun getNP(pref: SharedPreferences, skc: String): String {
        return pref.getString("np", null) ?: run {
            var np = UUID.randomUUID().toString()
            if (np.isNotBlank())
                pref.edit { putString("np", np) }
            else {
                np = skc
                pref.edit { putString("np", np) }
            }
            np
        }
    }

    /**
     * Provides SharedPreferences password
     *
     * @param pref, an instance of SharedPreferences
     * @return a String as password
     */
//    @DataScope
    @SK
    @Provides
    fun getSK(pref: SharedPreferences): String {
        // TODO: fix cpp in domains and add main-lib
        val skc = "testPasstestPasstestPasstestPass"
        return AesEncryptor().encrypt(getNP(pref, skc), skc) ?: ""
    }

    /**
     * Provides Database password
     *
     * @param pref, an instance of SharedPreferences
     * @return a String as password
     */
//    @DataScope
    @DK
    @Provides
    fun getDK(pref: SharedPreferences): String {
        // TODO: fix cpp in domains and add main-lib
        return "testtesttesttesttesttesttesttest"
//       return AesEncryptor().encrypt(getNP(pref, skc), skc) ?: ""
    }

    //    @DataScope
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    //    @DataScope
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        customInterceptor: CustomInterceptor
    ): OkHttpClient {


        val timeOut = 30L

        val dispatcher = Dispatcher(Executors.newFixedThreadPool(20))
        dispatcher.maxRequests = 20
        dispatcher.maxRequestsPerHost = 20

        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
            .dispatcher(dispatcher)
            .connectionPool(ConnectionPool(100, timeOut, TimeUnit.SECONDS))
            .addInterceptor(customInterceptor)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }

    //    @DataScope
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("http://barjavand-v3-dev.partdp.ir")
        .build()

    //    @DataScope
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(ApplicationJsonAdapterFactory)
        .build()

}
