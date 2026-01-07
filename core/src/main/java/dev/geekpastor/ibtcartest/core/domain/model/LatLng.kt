package dev.geekpastor.ibtcartest.core.domain.model

/**
 * Représente une position géographique définie par :
 * - une latitude
 * - une longitude
 *
 * Cette classe appartient à la **couche Domain**.
 * Elle est :
 * - indépendante de toute API Android (Google Maps, Location, etc.)
 * - réutilisable côté Data, Domain et UI
 *
 * Elle ne doit contenir **aucune logique de calcul ou de conversion**.
 */
data class LatLng(

    /**
     * Latitude du point géographique.
     *
     * Valeurs valides :
     * -90.0 (Sud) à +90.0 (Nord)
     */
    val latitude: Double,

    /**
     * Longitude du point géographique.
     *
     * Valeurs valides :
     * -180.0 (Ouest) à +180.0 (Est)
     */
    val longitude: Double
)