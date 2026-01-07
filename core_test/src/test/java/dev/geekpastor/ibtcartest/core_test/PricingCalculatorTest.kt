package dev.geekpastor.ibtcartest.core_test

import dev.geekpastor.ibtcartest.core.domain.PricingCalculator
import dev.geekpastor.ibtcartest.core.domain.model.LatLng
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests unitaires du composant PricingCalculator.
 *
 * Objectif :
 * Vérifier que les règles métier de calcul du tarif
 * sont correctement appliquées, indépendamment de l’UI
 * ou de la couche réseau.
 */
class PricingCalculatorTest {

    /**
     * Vérifie que le tarif minimum est appliqué lorsque
     * le montant calculé est inférieur au seuil défini.
     *
     * Règle métier testée :
     * - Toute course doit coûter AU MOINS 5.00 EUR
     */
    @Test
    fun minimum_fare_is_applied() {

        // ---- GIVEN ----
        // Création du calculateur de prix (classe métier pure)
        val calculator = PricingCalculator()

        // Création d’un trajet volontairement très court
        // afin que le prix calculé soit inférieur au minimum
        val draft = TripDraft(
            pickup = LatLng(0.0, 0.0),
            dropoff = LatLng(0.0, 0.0),
            stops = emptyList(),
            distanceKm = 1.0,   // distance faible
            durationMin = 1     // durée très courte
        )

        // ---- WHEN ----
        // Calcul du tarif à partir du brouillon de trajet
        val result = calculator.calculate(draft)

        // ---- THEN ----
        // Vérification que le tarif minimum est bien appliqué
        assertEquals(
            expected = 5.00,
            actual = result.total.amount,
            message = "Le tarif minimum doit être appliqué lorsque le total est inférieur"
        )
    }
}
