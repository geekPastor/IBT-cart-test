package dev.geekpastor.ibtcartest.core.domain.model

import dev.geekpastor.ibtcartest.core.domain.Money

//immuable DTOs
data class FareEstimate(
    val baseFare: Money,
    val distanceFare: Money,
    val timeFare: Money,
    val stopsFee: Money,
    val total: Money
)
