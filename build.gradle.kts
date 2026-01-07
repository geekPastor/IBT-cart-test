import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Fichier Gradle racine (Root build.gradle.kts)
 *
 * Rôle :
 * - Centraliser la configuration commune à tous les modules
 * - Éviter la duplication (boilerplate)
 * - Garantir la cohérence des versions et options de compilation
 */

// ----------------------------------------------------
// Plugins disponibles pour les sous-modules (apply false)
// ----------------------------------------------------
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

// ----------------------------------------------------
// Buildscript : plugins Gradle nécessaires au build
// ----------------------------------------------------
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Plugin Gradle pour Hilt (DI)
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
    }
}

// ----------------------------------------------------
// Configuration Android commune (App & Library)
// ----------------------------------------------------

/**
 * Extension utilitaire qui applique la configuration
 * Android par défaut à tous les modules Android.
 *
 * BaseExtension est la classe mère de :
 * - AppExtension
 * - LibraryExtension
 */
fun BaseExtension.defaultConfig() {

    // Version du SDK utilisée pour compiler
    compileSdkVersion(36)

    defaultConfig {
        minSdk = 27
        targetSdk = 36

        // Runner pour les tests instrumentés
        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    // Configuration Java / JVM
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Configuration Compose
    composeOptions {
        // ⚠️ ATTENTION :
        // Cette valeur devrait être une version de Compose Compiler
        // et NON la version Kotlin
        kotlinCompilerExtensionVersion =
            libs.versions.kotlin.get()
    }

    // Exclusions de ressources conflictuelles
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// ----------------------------------------------------
// Application automatique de la config aux plugins
// ----------------------------------------------------

/**
 * Cette fonction applique automatiquement la configuration
 * par défaut dès qu'un plugin est ajouté à un module.
 *
 * Avantage :
 * - Zéro duplication dans les build.gradle des modules
 */
fun PluginContainer.applyDefaultConfig(project: Project) {
    whenPluginAdded {
        when (this) {

            // Module Application
            is AppPlugin -> {
                project.extensions
                    .getByType<AppExtension>()
                    .apply { defaultConfig() }
            }

            // Module Library
            is LibraryPlugin -> {
                project.extensions
                    .getByType<LibraryExtension>()
                    .apply { defaultConfig() }
            }

            // Module Java pur (si présent)
            is JavaPlugin -> {
                project.extensions
                    .getByType<JavaPluginExtension>()
                    .apply {
                        sourceCompatibility = JavaVersion.VERSION_11
                        targetCompatibility = JavaVersion.VERSION_11
                    }
            }
        }
    }
}

// ----------------------------------------------------
// Application globale à tous les sous-modules
// ----------------------------------------------------

subprojects {

    // Applique automatiquement la config Android
    plugins.applyDefaultConfig(project)

    // Configuration Kotlin commune
    tasks.withType<KotlinCompile> {
        compilerOptions {

            // Target JVM pour Kotlin
            jvmTarget.set(JvmTarget.JVM_11)

            // Opt-in pour APIs expérimentales
            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
                )
            )
        }
    }
}
