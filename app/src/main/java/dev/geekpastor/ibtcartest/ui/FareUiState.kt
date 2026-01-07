package dev.geekpastor.ibtcartest.ui

import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate

/**
 * Représente l’état de l’interface utilisateur de l’écran "Fare".
 *
 * Cette sealed interface permet de modéliser **tous les états possibles**
 * de l’écran de manière exhaustive :
 * - Chargement
 * - Succès avec données
 * - Erreur
 *
 * Elle est consommée par Jetpack Compose via StateFlow.
 */
sealed interface FareUiState {

    /**
     * État affiché lorsque :
     * - une requête est en cours
     * - l’application attend une réponse (API / calcul)
     *
     * L’UI affiche généralement un loader (CircularProgressIndicator).
     */
    object Loading : FareUiState

    /**
     * État affiché lorsque le calcul du tarif a réussi.
     *
     * @param estimate Résultat du calcul du tarif,
     *                 prêt à être affiché par l’UI.
     */
    data class Content(
        val estimate: FareEstimate
    ) : FareUiState

    /**
     * État affiché lorsqu’une erreur survient.
     *
     * @param message Message lisible destiné à l’utilisateur
     *                (ex : erreur réseau, calcul impossible).
     */
    data class Error(
        val message: String
    ) : FareUiState
}
