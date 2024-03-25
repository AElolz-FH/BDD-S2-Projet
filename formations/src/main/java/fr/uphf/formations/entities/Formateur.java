package fr.uphf.formations.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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