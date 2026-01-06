package dev.geekpastor.ibtcartest.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.geekpastor.ibtcartest.core.domain.Money
import dev.geekpastor.ibtcartest.core.domain.model.FareEstimate

@Composable
fun FareScreen(
    viewModel: FareViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState) {
            is FareUiState.Loading -> {
                LoadingView()
            }

            is FareUiState.Error -> {
                ErrorView(
                    message = (uiState as FareUiState.Error).message,
                    onRetry = { viewModel.recompute() }
                )
            }

            is FareUiState.Content -> {
                FareContent(
                    estimate = (uiState as FareUiState.Content).estimate,
                    onAddStop = { viewModel.addStop() },
                    onRemoveStop = { viewModel.removeStop() },
                    onChangeDestination = { viewModel.changeDestination() }
                )
            }
        }
    }
}


//loader to show when loading
@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


//error view to show when an error occurs
@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Une erreur est survenue",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Réessayer")
        }
    }
}


//content to show when the fare is computed
@Composable
fun FareContent(
    estimate: FareEstimate,
    onAddStop: () -> Unit,
    onRemoveStop: () -> Unit,
    onChangeDestination: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --------- Trip Info (mock) ----------
        Text(
            text = "Trajet",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Pickup: Position actuelle")
        Text("Dropoff: Destination sélectionnée")

        Spacer(modifier = Modifier.height(16.dp))

        // --------- Actions ----------
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onAddStop) {
                Text("➕ Ajouter un arrêt")
            }

            Button(onClick = onRemoveStop) {
                Text("➖ Retirer un arrêt")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onChangeDestination) {
            Text("🔁 Changer la destination")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --------- Fare Breakdown ----------
        Text(
            text = "Détail du tarif",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        FareRow("Tarif de base", estimate.baseFare)
        FareRow("Distance", estimate.distanceFare)
        FareRow("Durée", estimate.timeFare)
        FareRow("Arrêts", estimate.stopsFee)

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        FareRow(
            label = "TOTAL",
            money = estimate.total,
            isTotal = true
        )
    }
}

//reusable line

@Composable
fun FareRow(
    label: String,
    money: Money,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (isTotal)
                MaterialTheme.typography.titleMedium
            else
                MaterialTheme.typography.bodyMedium
        )

        Text(
            text = String.format(
                "%.2f %s",
                money.amount,
                money.currency
            ),
            style = if (isTotal)
                MaterialTheme.typography.titleMedium
            else
                MaterialTheme.typography.bodyMedium
        )
    }
}

