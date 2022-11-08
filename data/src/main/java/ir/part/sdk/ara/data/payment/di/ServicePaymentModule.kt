package ir.part.sdk.ara.data.payment.di

import dagger.Module
import dagger.Provides
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.payment.repositories.PaymentService
import retrofit2.Retrofit


@Module
class ServicePaymentModule {

    @FeatureDataScope
    @Provides
    fun providePaymentService(retrofit: Retrofit): PaymentService {
        return retrofit.create(PaymentService::class.java)
    }
}