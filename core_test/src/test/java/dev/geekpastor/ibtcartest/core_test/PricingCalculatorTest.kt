package dev.geekpastor.ibtcartest.core_test

import dev.geekpastor.ibtcartest.core.domain.PricingCalculator
import dev.geekpastor.ibtcartest.core.domain.model.LatLng
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft
import kotlin.test.Test
import kotlin.test.assertEquals

class PricingCalculatorTest {

    @Test
    fun minimum_fare_is_applied() {
        val calculator = PricingCalculator()

        val draft = TripDraft(
            pickup = LatLng(0.0, 0.0),
            dropoff = LatLng(0.0, 0.0),
            stops = emptyList(),
            distanceKm = 1.0,
            durationMin = 1
        )

        val result = calculator.calculate(draft)

        assertEquals(5.00, result.total.amount)
    }
}