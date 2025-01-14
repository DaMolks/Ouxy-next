# Limitations Techniques et Bonnes Pratiques GitHub

## Commandes GitHub

### ❌ À ne pas utiliser
- `create_or_modify` : Cette commande ne fonctionne pas
- Modifications partielles de fichiers
- Updates individuels de petites sections

### ✅ À utiliser
- `push_files` : Seule commande fiable pour la modification de fichiers
- Toujours push le document entier
- Push par lots de fichiers quand possible

## Limitations de Messages

### Limite de Caractères
- Une limite existe sur la longueur des messages
- La limite est permissive mais doit être prise en compte
- Prévoir une marge de sécurité

### Bonnes Pratiques
- Diviser les grands contenus en plusieurs fichiers logiques
- Privilégier une structure modulaire
- Garder les fichiers individuels concis
- Faire plusieurs pushs si nécessaire

## Optimisation des Pushs

### Structure Recommandée
- Toujours utiliser le format push_files
- Inclure le contenu complet du fichier
- Vérifier la cohérence avant push
- Utiliser des messages de commit clairs

### Points d'Attention
- Toujours vérifier que le contenu complet est dans le push
- Ne pas tenter de faire des modifications partielles
- Préparer le contenu complet avant de push
- Logger les changements de manière appropriée

## Stratégies de Gestion de Contenu

### Pour les Gros Fichiers
1. Diviser en sections logiques
2. Créer des fichiers séparés
3. Maintenir une table des matières
4. Cross-référencer si nécessaire

### Pour les Mises à Jour Fréquentes
1. Préparer les changements hors-ligne
2. Vérifier le contenu complet
3. Push en une seule fois
4. Logger les modifications
