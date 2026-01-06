package dev.geekpastor.ibtcartest.ui

import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate

sealed interface FareUiState {
    object Loading : FareUiState
    data class Content(val estimate: FareEstimate) : FareUiState
    data class Error(val message: String) : FareUiState
}
