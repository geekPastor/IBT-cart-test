package dev.geekpastor.ibtcartest.core.data

import dev.geekpastor.ibtcartest.core.domain.PricingCalculator
import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft
import kotlinx.coroutines.delay

/**
 * Implémentation FAKE du PricingRepository.
 *
 * Rôle :
 * - Simuler un appel réseau ou backend
 * - Introduire une latence artificielle
 * - Simuler des erreurs réseau
 *
 * Cette classe est volontairement simple et déterministe
 * afin de faciliter :
 * - le développement UI
 * - les tests
 * - les démonstrations (take-home test)
 */
class FakePricingApi(
    private val calculator: PricingCalculator
) : PricingRepository {

    /**
     * Estime le tarif d'un trajet à partir d'un TripDraft.
     *
     * @param draft Données du trajet (pickup, dropoff, arrêts, distance, durée)
     * @return Result<FareEstimate>
     *         - Success : tarif calculé
     *         - Failure : erreur simulée
     */
    override suspend fun estimateFare(
        draft: TripDraft
    ): Result<FareEstimate> {

        // ------------------------------
        // Simulation de latence réseau
        // ------------------------------
        delay(
            (500..1200).random().toLong()
        )

        // ------------------------------
        // Simulation d'erreur réseau
        // 20% de chances d'échec
        // ------------------------------
        if ((1..100).random() <= 20) {
            return Result.failure(
                Exception("Network error")
            )
        }

        // ------------------------------
        // Calcul du tarif réel
        // ------------------------------
        val estimate = calculator.calculate(draft)

        return Result.success(estimate)
    }
}
