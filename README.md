# BDD-S2-Projet

## Description du projet

Le projet est un système distribué de gestion de formations / utilisateurs / salles et séances.

Ce projet back-end est relié au projet front-end suivant : https://github.com/dylan72/frontbdd

## Lancement du projet en local :

- Lancer le Service Discovery (ServeurRegistreApplication.java)
- Lancer la Gateway (GatewayApplication.java)
- Lancer le Microservice Formation (FormationApplication.java)
- Lancer le Microservice Salle (SalleApplication.java)
- Lancer le Microservice Seance (SeanceApplication.java)
- Lancer le Microservice Utilisateur (UtilisateurApplication.java)

### Ce qui fonctionne dans le projet pour le moment :

- Collection postman
1. Formation
   1. POST : http://localhost:9000/formations/
   2. GET : http://localhost:9000/formations/ (getAll)
   3. GET : http://localhost:9000/formations/{id} (getById)
   4. PUT : http://localhost:9000/formations/users/{idFormation}/{idUser} (update ajout de formateur à une formation)
   5. PUT : http://localhost:9000/formations/ (update ajout d'une séance distante à une formation)
   6. DELETE : http://localhost:9000/formations/{id} (deleteById)
2. Salle
   1. POST : http://localhost:9000/salles/
   2. GET : http://localhost:9000/salles/{id} (getById)
   3. GET : http://localhost:9000/salles/ (getAll)
   4. PUT : http://localhost:9000/salles/{numeroSalle} (update)
   5. PUT : http://localhost:9000/salles/dispo={numeroSalle} (update la disponibilité de la salle)
   6. GET : http://localhost:9000/salles/byNum/{numeroSalle} (getByNumeroSalle)
   7. GET : http://localhost:9000/salles/numeroSalle={numeroSalle}/batiment={batiment} (getByNumeroSalleAndBatiment)
   8. DELETE : http://localhost:9000/salles/numeroSalle={numeroSalle}/batiment={batiment} (deleteByNumeroSaalleAndBatiment)
3. Utilisateurs :
   1. POST :http://localhost:9000/utilisateurs/
   2. GET : http://localhost:9000/utilisateurs/ (getAll)
   3. GET : http://localhost:9000/utilisateurs/{id} (getById) (il faudrait rajouter une requête par nom prénom pour faciliter les choses dans le front)
   4. PUT : http://localhost:9000/utilisateurs/{id} (update utilisateur en tant que formateur)
   5. DELETE : http://localhost:9000/utilisateurs/nom={nom}&prenom={prenom} (deleteByNomAndPrenom)
4. Séance :
   1. POST : http://localhost:9000/seances/ (on récupère la formation distante et la salle distante pour les ajouter à la séance)
   2. GET : http://localhost:9000/seances/{idSeance} (getById)
   3. PUT : http://localhost:9000/seances/{idSeance} (update)
   4. GET : http://localhost:9000/seances/ (getAll)
   5. DELETE : http://localhost:9000/seances/1 (deleteById)

### Ce qu'il reste à faire : 

- Une requête pour ajouter une séance dans une salle pour assurer plus de tolérance aux pannes dans le projet (avoir les données duppliquées dans plusieurs microservices)
- Implémentation complète de RabbitMQ (pour l'instant on aimerait l'intégrer dans les post et les delete) -> RabbitMQ permettrait d'éviter de faire des requêtes à chaque microservice pour mettre à jour les données et de le faire automatiquement.
- Implémentation du serveur d'authentification pour les tokens de sécurité