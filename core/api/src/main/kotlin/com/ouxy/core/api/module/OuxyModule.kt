package com.ouxy.core.api.module

interface OuxyModule {
    /**
     * Identifiant unique du module
     */
    val id: String

    /**
     * Version du module
     */
    val version: String

    /**
     * Liste des permissions requises par le module
     */
    val requiredPermissions: Set<String>

    /**
     * Initialise le module
     */
    suspend fun initialize()

    /**
     * Démarre le module
     */
    suspend fun start()

    /**
     * Arrête le module
     */
    suspend fun stop()

    /**
     * Nettoie les ressources du module
     */
    suspend fun cleanup()
}
