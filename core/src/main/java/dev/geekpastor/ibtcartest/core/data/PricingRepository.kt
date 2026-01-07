package dev.geekpastor.ibtcartest.core.data

import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft

/**
 * Contrat de la couche Data pour l’estimation d’un tarif.
 *
 * Rôle :
 * - Définir ce que la couche UI / ViewModel est autorisée à demander
 * - Masquer la source réelle des données (API, fake, base locale, etc.)
 *
 * IMPORTANT :
 * - Le ViewModel dépend de cette interface
 * - Jamais d’une implémentation concrète
 *
 * Cela permet :
 * - le remplacement facile de FakePricingApi par une vraie API
 * - les tests unitaires et mocks
 * - une architecture propre (Clean Architecture)
 */
interface PricingRepository {

    /**
     * Estime le tarif d’un trajet.
     *
     * @param draft Données du trajet à analyser :
     *  - point de départ (pickup)
     *  - destination (dropoff)
     *  - arrêts intermédiaires
     *  - distance estimée
     *  - durée estimée
     *
     * @return Result<FareEstimate>
     *
     * - Result.success(FareEstimate) :
     *      → estimation réussie
     *
     * - Result.failure(Throwable) :
     *      → erreur réseau, serveur ou logique métier
     *
     * Fonction suspend :
     * - Appel potentiellement long (réseau, calcul, IO)
     * - À appeler depuis une coroutine
     */
    suspend fun estimateFare(
        draft: TripDraft
    ): Result<FareEstimate>
}
