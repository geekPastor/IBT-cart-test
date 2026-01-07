package dev.geekpastor.ibtcartest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.geekpastor.ibtcartest.core.data.PricingRepository
import dev.geekpastor.ibtcartest.core.domain.model.LatLng
import dev.geekpastor.ibtcartest.core.domain.model.TripDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsable de :
 * - maintenir l'état de l'écran de calcul du tarif
 * - gérer les actions utilisateur (ajout/suppression d'arrêts, changement de destination)
 * - orchestrer l'appel au repository pour estimer le tarif
 *
 * Ce ViewModel respecte une architecture unidirectionnelle :
 * UI -> ViewModel -> Repository -> ViewModel -> UI
 */
@HiltViewModel
class FareViewModel @Inject constructor(
    /**
     * Repository injecté via Hilt.
     * Il encapsule toute la logique métier liée au calcul du tarif.
     */
    private val repository: PricingRepository
) : ViewModel() {

    /**
     * StateFlow interne mutable.
     * Il ne doit JAMAIS être exposé directement à l'UI.
     */
    private val _state = MutableStateFlow<FareUiState>(FareUiState.Loading)

    /**
     * StateFlow exposé à l'UI en lecture seule.
     * L'UI observe cet état et se recompose automatiquement.
     */
    val state: StateFlow<FareUiState> = _state

    /**
     * Brouillon courant du trajet.
     * Il représente l'état métier actuel (pickup, dropoff, arrêts, etc.).
     *
     * Ce draft est modifié à chaque interaction utilisateur,
     * puis utilisé pour recalculer le tarif.
     */
    private var currentDraft: TripDraft = initialDraft()

    /**
     * Bloc d'initialisation du ViewModel.
     *
     * Appelé une seule fois à la création du ViewModel.
     * On déclenche ici le premier calcul de tarif afin
     * d'éviter un écran bloqué sur le loader.
     */
    init {
        recompute()
    }

    /**
     * Lance (ou relance) le calcul du tarif à partir du draft courant.
     *
     * - Passe l'état en Loading
     * - Appelle le repository
     * - Met à jour l'état selon le résultat (Success / Error)
     */
    fun recompute() {
        viewModelScope.launch {
            // Indique à l'UI que le calcul est en cours
            _state.value = FareUiState.Loading

            // Appel au repository pour estimer le tarif
            repository.estimateFare(currentDraft)
                .onSuccess { estimate ->
                    // Succès : on expose le contenu à l'UI
                    _state.value = FareUiState.Content(estimate)
                }
                .onFailure { throwable ->
                    // Erreur : on expose un message exploitable par l'UI
                    _state.value = FareUiState.Error(
                        throwable.message ?: "Unknown error"
                    )
                }
        }
    }

    /**
     * Ajoute un arrêt intermédiaire au trajet.
     *
     * - Met à jour le draft
     * - Relance immédiatement le calcul du tarif
     */
    fun addStop() {
        currentDraft = currentDraft.copy(
            stops = currentDraft.stops + randomLatLng()
        )
        recompute()
    }

    /**
     * Supprime le dernier arrêt intermédiaire s'il existe.
     *
     * La suppression est sécurisée pour éviter toute erreur
     * lorsque la liste des arrêts est vide.
     */
    fun removeStop() {
        if (currentDraft.stops.isNotEmpty()) {
            currentDraft = currentDraft.copy(
                stops = currentDraft.stops.dropLast(1)
            )
            recompute()
        }
    }

    /**
     * Change la destination finale du trajet.
     *
     * Utile pour simuler une modification du trajet en cours.
     */
    fun changeDestination() {
        currentDraft = currentDraft.copy(
            dropoff = randomLatLng()
        )
        recompute()
    }

    // ----------------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------------

    /**
     * Crée le draft initial du trajet.
     *
     * Dans un vrai projet :
     * - pickup viendrait du GPS
     * - dropoff serait choisi par l'utilisateur
     */
    private fun initialDraft(): TripDraft {
        return TripDraft(
            pickup = randomLatLng(),
            dropoff = randomLatLng(),
            stops = emptyList(),
            distanceKm = 0.0,
            durationMin = 0
        )
    }

    /**
     * Génère une position géographique aléatoire.
     *
     * Cette méthode sert uniquement à simuler
     * des changements de trajet dans ce test.
     */
    private fun randomLatLng(): LatLng {
        return LatLng(
            latitude = (-90..90).random().toDouble(),
            longitude = (-180..180).random().toDouble()
        )
    }
}
