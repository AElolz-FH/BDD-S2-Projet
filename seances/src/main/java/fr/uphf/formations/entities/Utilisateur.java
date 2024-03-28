package fr.uphf.formations.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    private Integer idUtilisateur;
    private boolean isFormateur;
    private String nom;
    private String prenom;
    private String email;
}
