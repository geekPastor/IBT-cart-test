package dev.geekpastor.ibtcartest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.geekpastor.ibtcartest.ui.FareScreen
import dev.geekpastor.ibtcartest.ui.theme.IBTCARTestTheme

/**
 * Activité principale de l'application.
 *
 * Rôle :
 * - Point d'entrée UI de l'application Android
 * - Héberge l'arbre Compose
 * - Sert de pont entre Android Framework et Jetpack Compose
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Méthode appelée lors de la création de l'Activity.
     *
     * Ici :
     * - On initialise Jetpack Compose via setContent
     * - On applique le thème Material 3
     * - On affiche l'écran principal (FareScreen)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Thème global de l'application (couleurs, typographies, formes)
            IBTCARTestTheme {

                // Écran principal de l'application
                // Le ViewModel est injecté automatiquement via Hilt
                FareScreen()
            }
        }
    }
}
