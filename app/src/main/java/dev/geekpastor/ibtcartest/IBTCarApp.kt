package dev.geekpastor.ibtcartest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe Application principale de l'application.
 *
 * Rôle :
 * - Point d'entrée global de l'application Android
 * - Initialisation du framework Hilt
 *
 * IMPORTANT :
 * Cette classe NE DOIT PAS être une Activity.
 * Elle est référencée dans le AndroidManifest.xml
 * via l'attribut android:name de la balise <application>.
 */
@HiltAndroidApp
class IBTCarApp : Application()
