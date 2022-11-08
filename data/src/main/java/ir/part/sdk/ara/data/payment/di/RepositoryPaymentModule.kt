package ir.part.sdk.ara.data.payment.di

import dagger.Binds
import dagger.Module
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.payment.repositories.PaymentRepositoryImp
import ir.part.sdk.ara.domain.payment.repository.PaymentRepository


@Module
abstract class RepositoryPaymentModule {

    @FeatureDataScope
    @Binds
    abstract fun providePaymentRepository(paymentRepositoryImp: PaymentRepositoryImp): PaymentRepository

}