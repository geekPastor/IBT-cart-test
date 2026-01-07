# IBT Car – Fare Estimation App

## 📱 Description

**IBT Car** est une application Android de démonstration permettant de calculer et d’afficher une **estimation de tarif de trajet** (type VTC / Taxi).

Le projet met l’accent sur :

* une **architecture propre (Clean Architecture)**
* l’utilisation de **Jetpack Compose**
* une **séparation claire des responsabilités**
* des **règles métier testées unitairement**

---

## 🎯 Fonctionnalités

* Estimation d’un tarif de trajet à partir de :

  * une position de départ (pickup)
  * une destination (dropoff)
  * des arrêts intermédiaires
  * une distance et une durée simulées
* Affichage détaillé du tarif :

  * tarif de base
  * coût par distance
  * coût par durée
  * frais d’arrêts
  * total (avec tarif minimum garanti)
* Gestion des états UI :

  * Chargement
  * Succès
  * Erreur (simulation d’erreur réseau)
* Ajout / suppression d’arrêts
* Changement de destination
* Tests unitaires des règles métier

---

## 🧱 Architecture

Le projet suit les principes de la **Clean Architecture**.

```
app/
 ├── ui/                 → UI Jetpack Compose + ViewModel
 ├── di/                 → Injection de dépendances (Hilt)
 └── MainActivity.kt
 └── IBTCarApp.kt


core/
 ├── domain/
 │   ├── model/          → Entités métier (TripDraft, FareEstimate, Money, LatLng)
 │   ├── PricingCalculator.kt
 │
 ├── data/
 │   ├── PricingRepository.kt
 │   ├── FakePricingApi.kt
 │
core_test/
 └── PricingCalculatorTest.kt
```

### Séparation des responsabilités

| Couche    | Rôle                                 |
| --------- | ------------------------------------ |
| UI        | Affichage et interaction utilisateur |
| ViewModel | Gestion de l’état et orchestration   |
| Domain    | Règles métier pures                  |
| Data      | Source de données simulée            |
| Test      | Validation des règles métier         |

---

## 🧠 Logique métier

Le calcul du tarif est géré par `PricingCalculator` :

### Règles de calcul

* Tarif de base : **2.50 €**
* Coût par km : **0.80 €**
* Coût par minute : **0.20 €**
* Coût par arrêt : **1.00 €**
* Tarif minimum : **5.00 €**
* Devise : **EUR**

Si le total calculé est inférieur au tarif minimum, **le minimum est automatiquement appliqué**.

---

## 🔄 Gestion des états UI

L’UI repose sur un `StateFlow` exposé par le `FareViewModel` :

```kotlin
sealed interface FareUiState {
    object Loading : FareUiState
    data class Content(val estimate: FareEstimate) : FareUiState
    data class Error(val message: String) : FareUiState
}
```

Cela garantit :

* une UI réactive
* un code lisible
* une gestion claire des transitions d’état

---

## 🧪 Tests

Le projet inclut des **tests unitaires** sur la couche métier.

Exemple :

* vérification de l’application du tarif minimum
* calcul correct des composantes du tarif

Les tests sont écrits sans dépendance Android, ce qui les rend :

* rapides
* fiables
* faciles à maintenir

---

## 🛠️ Stack technique

* **Kotlin**
* **Jetpack Compose**
* **Material 3**
* **Hilt** (Dependency Injection)
* **StateFlow / Coroutines**
* **JUnit / kotlin.test**

---

## ▶️ Lancer le projet

### Prérequis

* Android Studio (Giraffe ou plus récent)
* SDK Android 27+
* Kotlin 1.9+

### Étapes

1. Cloner le projet
2. Ouvrir dans Android Studio
3. Synchroniser Gradle
4. Lancer sur un émulateur ou un appareil physique

---

## 📌 Notes importantes

* L’API est **simulée** (`FakePricingApi`)
* Des erreurs réseau sont volontairement générées aléatoirement
* Le projet est conçu comme un **test technique**, pas une application production

---

## ✅ Points clés mis en avant

* Architecture propre et maintenable
* Code lisible et commenté
* Logique métier testée
* UI moderne avec Compose
* Bonnes pratiques Android

---

## 👤 Auteur

Projet réalisé par **MUKEBA MUKEBA Chrinovic (The Geek Pastor)**
Android Developer

---

## 🧠 Remarque finale

Ce projet est volontairement **simple fonctionnellement**, mais **riche techniquement**, afin de démontrer :

* la qualité du code
* la compréhension des architectures modernes
* la capacité à structurer un projet Android professionnel.
