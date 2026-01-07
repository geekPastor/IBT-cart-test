package dev.geekpastor.ibtcartest.core.domain

/**
 * Représente une valeur monétaire dans le domaine métier.
 *
 * Cette classe est un **Value Object** :
 * - Elle est immuable
 * - Elle n’a pas d’identité propre
 * - Deux instances avec les mêmes valeurs sont équivalentes
 *
 * Exemple :
 *  Money(amount = 12.50, currency = "USD")
 */
data class Money(

    /**
     * Montant numérique de l'argent.
     *
     * Le type Double est utilisé ici pour la simplicité du test technique.
     * ⚠️ En production (finance, banque), on préférerait BigDecimal
     * pour éviter les erreurs d’arrondi.
     */
    val amount: Double,

    /**
     * Devise associée au montant.
     *
     * Idéalement, il s'agit d'un code ISO 4217 :
     *  - "USD" → Dollar américain
     *  - "EUR" → Euro
     *  - "CDF" → Franc congolais
     */
    val currency: String
)
