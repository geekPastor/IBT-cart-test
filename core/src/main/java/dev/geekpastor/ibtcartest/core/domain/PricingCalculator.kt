package dev.geekpastor.ibtcartest.core.domain

import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft

/**
 * Composant métier responsable du calcul du tarif d’un trajet.
 *
 * 👉 Cette classe appartient au **Domain Layer** :
 * - Elle ne dépend d’aucune couche UI ou Data
 * - Elle contient uniquement des règles métier pures
 * - Elle est facilement testable
 */
class PricingCalculator {

    companion object {

        /**
         * Tarif de base appliqué à chaque course,
         * indépendamment de la distance ou de la durée.
         */
        const val BASE_FARE = 2.50

        /**
         * Coût par kilomètre parcouru.
         */
        const val PER_KM = 0.80

        /**
         * Coût par minute de trajet.
         */
        const val PER_MIN = 0.20

        /**
         * Coût additionnel par arrêt intermédiaire.
         */
        const val PER_STOP = 1.00

        /**
         * Tarif minimum garanti pour toute course.
         */
        const val MIN_FARE = 5.00

        /**
         * Devise utilisée pour tous les montants calculés.
         */
        const val CURRENCY = "EUR"
    }

    /**
     * Calcule une estimation tarifaire à partir d’un brouillon de trajet.
     *
     * @param draft Données du trajet (points, distance, durée, arrêts)
     * @return Une estimation complète et détaillée du tarif
     */
    fun calculate(draft: TripDraft): FareEstimate {

        // ---- Calcul des différentes composantes du tarif ----

        // Tarif de base fixe
        val baseFare = BASE_FARE

        // Coût lié à la distance parcourue
        val distanceFare = draft.distanceKm * PER_KM

        // Coût lié à la durée du trajet
        val timeFare = draft.durationMin * PER_MIN

        // Coût des arrêts intermédiaires
        val stopsFare = draft.stops.size * PER_STOP

        // ---- Calcul du total brut ----
        val rawTotal = baseFare + distanceFare + timeFare + stopsFare

        // ---- Application du tarif minimum ----
        val finalTotal = maxOf(rawTotal, MIN_FARE)

        // ---- Construction de l’objet de sortie ----
        return FareEstimate(
            baseFare = Money(baseFare, CURRENCY),
            distanceFare = Money(distanceFare, CURRENCY),
            timeFare = Money(timeFare, CURRENCY),
            stopsFee = Money(stopsFare, CURRENCY),
            total = Money(finalTotal, CURRENCY)
        )
    }
}
