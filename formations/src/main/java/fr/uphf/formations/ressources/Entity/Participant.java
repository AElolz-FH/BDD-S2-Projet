package fr.uphf.formations.ressources.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Participant {
    @Id
    private String idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
}
