package fr.uphf.formations.ressources.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Formateur {
    @Id
    private String idUtilisateur;
}

/*

POST /formations
{
    "libelle": "Formation Java",
    "description": "Formation pour apprendre Java",
 */

/*
PUT /formations/1
{
    "formateur": {
        "idUtilisateur": "1"
    }
}

GET /formations
1ere etape

        -- Aller appeler /utilisateurs/idUtilisateur
        -- Si l'utilisateur existe on ajoute le formateur à la formation
*/