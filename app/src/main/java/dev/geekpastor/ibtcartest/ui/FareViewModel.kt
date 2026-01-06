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
import kotlin.onSuccess

@HiltViewModel
class FareViewModel @Inject constructor(
    private val repository: PricingRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FareUiState>(FareUiState.Loading)
    val state: StateFlow<FareUiState> = _state

    private var currentDraft = initialDraft()

    fun recompute() {
        viewModelScope.launch {
            _state.value = FareUiState.Loading

            repository.estimateFare(currentDraft)
                .onSuccess {
                    _state.value = FareUiState.Content(it)
                }
                .onFailure {
                    _state.value = FareUiState.Error(it.message ?: "Unknown error")
                }
        }
    }

    fun addStop() {
        currentDraft = currentDraft.copy(
            stops = currentDraft.stops + randomLatLng()
        )
        recompute()
    }

    fun removeStop() {
        if (currentDraft.stops.isNotEmpty()) {
            currentDraft = currentDraft.copy(
                stops = currentDraft.stops.dropLast(1)
            )
            recompute()
        }
    }

    fun changeDestination() {
        currentDraft = currentDraft.copy(
            dropoff = randomLatLng()
        )
        recompute()
    }

    // ---------- Helpers ----------

    private fun initialDraft(): TripDraft {
        return TripDraft(
            pickup = randomLatLng(),
            dropoff = randomLatLng(),
            stops = emptyList(),
            distanceKm = 0.0,
            durationMin = 0
        )
    }


    private fun randomLatLng(): LatLng {
        return LatLng(
            latitude = (-90..90).random().toDouble(),
            longitude = (-180..180).random().toDouble()
        )
    }
}
