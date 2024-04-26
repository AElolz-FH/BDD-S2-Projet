package fr.uphf.formations.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idSalle;
    private LocalDateTime date;
    private String duree;
    private String batiment;
    @ElementCollection //gérer les listes d'id de manière persistantes
    private List<Integer> idUtilisateurs;
    private String nomSalle;
    private Integer numeroSalle;
    private String nomFormation;
    private String nomFormateur;
    @OneToOne
    private Salle salles;
}
