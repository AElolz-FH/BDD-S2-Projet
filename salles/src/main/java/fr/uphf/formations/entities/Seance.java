package fr.uphf.formations.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idSeance;
    private String libelleFormation;
    private LocalDateTime date;
    private Integer numeroSalle;
    private Integer duree;
    @ManyToOne
    private Salles salle;
    private String batiment;
}
