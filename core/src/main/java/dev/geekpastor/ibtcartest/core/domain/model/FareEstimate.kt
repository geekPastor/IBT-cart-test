package dev.geekpastor.ibtcartest.core.domain.model

import dev.geekpastor.ibtcartest.core.domain.Money

/**
 * Représente le résultat final de l’estimation d’un tarif de trajet.
 *
 * Il s’agit d’un **DTO immuable** (Data Transfer Object) :
 * - uniquement des données
 * - aucune logique métier
 * - facilement sérialisable / testable
 *
 * Cette classe appartient à la **couche Domain**
 * → indépendante de Android, UI ou Data.
 */
data class FareEstimate(

    /**
     * Tarif de base du trajet.
     * Montant fixe appliqué à chaque course,
     * indépendamment de la distance ou du temps.
     */
    val baseFare: Money,

    /**
     * Coût lié à la distance parcourue.
     * Calculé en fonction du nombre de kilomètres.
     */
    val distanceFare: Money,

    /**
     * Coût lié à la durée du trajet.
     * Calculé en fonction du temps estimé (en minutes).
     */
    val timeFare: Money,

    /**
     * Frais supplémentaires dus aux arrêts intermédiaires.
     * Peut être nul si aucun arrêt n’est ajouté.
     */
    val stopsFee: Money,

    /**
     * Montant total à payer.
     *
     * Somme de :
     * - baseFare
     * - distanceFare
     * - timeFare
     * - stopsFee
     */
    val total: Money
)
