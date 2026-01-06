package dev.geekpastor.ibtcartest.core.domain

import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft

class PricingCalculator {

    companion object {
        const val BASE_FARE = 2.50
        const val PER_KM = 0.80
        const val PER_MIN = 0.20
        const val PER_STOP = 1.00
        const val MIN_FARE = 5.00
        const val CURRENCY = "EUR"
    }

    fun calculate(draft: TripDraft): FareEstimate {
        val base = BASE_FARE
        val distance = draft.distanceKm * PER_KM
        val time = draft.durationMin * PER_MIN
        val stops = draft.stops.size * PER_STOP

        val rawTotal = base + distance + time + stops
        val finalTotal = maxOf(rawTotal, MIN_FARE)

        return FareEstimate(
            baseFare = Money(base, CURRENCY),
            distanceFare = Money(distance, CURRENCY),
            timeFare = Money(time, CURRENCY),
            stopsFee = Money(stops, CURRENCY),
            total = Money(finalTotal, CURRENCY)
        )
    }
}
