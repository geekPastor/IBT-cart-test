plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "dev.geekpastor.ibtcartest.core_test"

    buildFeatures{
        compose = true
    }
}

dependencies {
    implementation(projects.core)
    // JUnit (OBLIGATOIRE)
    testImplementation(libs.junit)

    // Kotlin test (recommandé)
    testImplementation(kotlin("test"))
}