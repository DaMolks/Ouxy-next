# Orientation du Projet Ouxy Next

## [15 Janvier 2025] Phase 1 - Restructuration

### État Initial
- Le projet dispose d'une architecture modulaire bien pensée
- Du code a été supprimé sans mise à jour du LOG
- Les implémentations étaient mélangées avec le core
- Problèmes avec l'injection de dépendances

### Analyses et Décisions
1. Séparation Core/Implémentation
   - Le core ne contient que les interfaces et modèles
   - Les implémentations sont dans l'app
   - Meilleure maintenabilité et flexibilité

2. Injection de Dépendances
   - Passage de @Binds à @Provides
   - Simplification de l'injection
   - Plus de flexibilité dans les implémentations

3. Interface de Debug
   - ViewBinding activé
   - Layouts configurés
   - Base pour le développement des modules

### Prochain Cycle
1. Core Solide
   - Implémentation de la persistence
   - Tests unitaires complets
   - Documentation technique

2. Modules Métier
   - Premier module fonctionnel
   - Tests d'intégration
   - Guide du développeur

3. Infrastructure
   - CI/CD
   - Gestion des versions
   - Déploiement des modules

### Points d'Attention
- Maintenir la séparation core/implémentation
- Documenter les changements architecturaux
- Tester chaque niveau (unit, intégration, e2e)
- Garder l'approche "vaisseau spatial" pour la clarté