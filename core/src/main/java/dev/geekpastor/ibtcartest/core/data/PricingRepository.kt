package dev.geekpastor.ibtcartest.core.data

import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft

interface PricingRepository {
    suspend fun estimateFare(draft: TripDraft): Result<FareEstimate>
}
