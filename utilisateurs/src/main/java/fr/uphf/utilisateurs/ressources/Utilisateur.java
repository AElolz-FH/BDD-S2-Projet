package fr.uphf.utilisateurs.ressources;

import fr.uphf.formations.ressources.Entity.Formations;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean formateur;
    private LocalDateTime dateCreation;
    private String nom;
    @OneToMany
    private List<Formations> formations;
}