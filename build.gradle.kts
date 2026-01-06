import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
    }
}



//defining the common things that are required for all module
//for multi module support to avoid boilerplate codes, here are
fun BaseExtension.defaultConfig(){
    compileSdkVersion(36)

    defaultConfig {
        minSdk = 27
        targetSdk = 36

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions{
        kotlinCompilerExtensionVersion = libs.versions.kotlin.get()
    }

    packagingOptions {
        resources{
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

//applying the default config to all the modules
fun PluginContainer.applyDefaultConfig(project: Project){
    whenPluginAdded {
        when(this){
            is AppPlugin -> project.extensions.getByType<AppExtension>().apply{
                defaultConfig()
            }
            is LibraryPlugin -> project.extensions.getByType<LibraryExtension>().apply{
                defaultConfig()
            }
            is JavaPlugin -> project.extensions.getByType<JavaPluginExtension>().apply{
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }
    }
}

subprojects{
    project.plugins.applyDefaultConfig(project)

    tasks.withType<KotlinCompile>{
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
                )
            )
        }
    }
}