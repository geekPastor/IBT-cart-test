package dev.geekpastor.ibtcartest.core.domain.model

/**
 * Représente l’état courant d’un trajet en cours de préparation.
 *
 * Cette classe est utilisée pour :
 * - décrire un trajet avant ou pendant le calcul du tarif
 * - transmettre les données nécessaires au moteur de tarification
 *
 * Elle ne contient **aucune logique métier**.
 */
data class TripDraft(

    /**
     * Point de départ du trajet.
     *
     * En pratique :
     * - position GPS actuelle du client
     * - ou point sélectionné sur la carte
     */
    val pickup: LatLng,

    /**
     * Point d’arrivée du trajet.
     *
     * Peut être modifié dynamiquement (ex : changement de destination).
     */
    val dropoff: LatLng,

    /**
     * Liste des arrêts intermédiaires.
     *
     * - Peut être vide
     * - L’ordre est important (trajet séquentiel)
     */
    val stops: List<LatLng>,

    /**
     * Distance totale estimée du trajet en kilomètres.
     *
     * - Calculée par un service de routing (Google Maps, Mapbox, etc.)
     * - Utilisée pour le calcul du tarif distance
     */
    val distanceKm: Double,

    /**
     * Durée estimée du trajet en minutes.
     *
     * - Dépend du trafic, du type de route, etc.
     * - Utilisée pour le calcul du tarif temps
     */
    val durationMin: Int
)
