package dev.geekpastor.ibtcartest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.geekpastor.ibtcartest.core.data.FakePricingApi
import dev.geekpastor.ibtcartest.core.data.PricingRepository
import dev.geekpastor.ibtcartest.core.domain.PricingCalculator

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideCalculator() = PricingCalculator()

    @Provides
    fun provideRepository(
        calculator: PricingCalculator
    ): PricingRepository =
        FakePricingApi(calculator)
}