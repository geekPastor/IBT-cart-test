package dev.geekpastor.ibtcartest.core.data

import dev.geekpastor.ibtcartest.core.domain.PricingCalculator
import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft
import kotlinx.coroutines.delay

class FakePricingApi(
    private val calculator: PricingCalculator
) : PricingRepository {

    override suspend fun estimateFare(draft: TripDraft): Result<FareEstimate> {
        delay((500..1200).random().toLong())

        if ((1..100).random() <= 20) {
            return Result.failure(Exception("Network error"))
        }

        return Result.success(calculator.calculate(draft))
    }
}
