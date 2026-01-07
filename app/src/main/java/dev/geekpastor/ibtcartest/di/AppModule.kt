package dev.geekpastor.ibtcartest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.geekpastor.ibtcartest.core.data.FakePricingApi
import dev.geekpastor.ibtcartest.core.data.PricingRepository
import dev.geekpastor.ibtcartest.core.domain.PricingCalculator

/**
 * Module Hilt principal de l'application.
 *
 * Rôle :
 * - Déclarer comment créer les dépendances
 * - Fournir les implémentations concrètes aux ViewModels et autres couches
 *
 * Ce module est installé dans le SingletonComponent :
 * ➜ Les dépendances fournies vivent pendant toute la durée de l'application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Fournit une instance de PricingCalculator.
     *
     * PricingCalculator contient la logique pure de calcul du tarif :
     * - distance
     * - durée
     * - frais supplémentaires
     *
     * Cette classe ne dépend d'aucune autre, elle peut donc être instanciée directement.
     */
    @Provides
    fun provideCalculator() = PricingCalculator()

    /**
     * Fournit une implémentation de PricingRepository.
     *
     * Ici, nous utilisons FakePricingApi :
     * - Elle simule un appel réseau ou backend
     * - Elle repose sur PricingCalculator pour produire les résultats
     *
     * Grâce à l'injection :
     * - Le ViewModel dépend uniquement de l'interface PricingRepository
     * - L'implémentation peut être remplacée facilement (API réelle plus tard)
     */
    @Provides
    fun provideRepository(
        calculator: PricingCalculator
    ): PricingRepository =
        FakePricingApi(calculator)
}
