package fr.uphf.formations.ressources.Entity;

import fr.uphf.utilisateurs.ressources.Utilisateur;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Formations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String libelle;
    private String description;
    @OneToOne
    private Utilisateur formateur;
    @OneToMany
    private List<Utilisateur> participants;
}
