package fr.uphf.formations.ressources.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Formateur {
    @Id
    private String idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
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