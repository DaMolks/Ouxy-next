package com.damolks.ouxynext.core.model

/**
 * Représente les informations d'un module dans le système.
 *
 * @property id Identifiant unique du module
 * @property name Nom du module
 * @property version Version actuelle du module
 * @property isActive État d'activation du module
 */
data class ModuleInfo(
    val id: String,
    val name: String,
    val version: String,
    val isActive: Boolean = false
)