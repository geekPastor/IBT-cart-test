package dev.geekpastor.ibtcartest.core.domain.model

data class TripDraft(
    val pickup: LatLng,
    val dropoff: LatLng,
    val stops: List<LatLng>,
    val distanceKm: Double,
    val durationMin: Int
)