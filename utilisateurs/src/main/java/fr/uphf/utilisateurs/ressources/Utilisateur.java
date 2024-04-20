package fr.uphf.utilisateurs.ressources;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    private Integer id;
    private boolean formateur;
    private LocalDateTime dateCreation;
    private String nom;
    private String email;
    private String prenom;
}