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
    private Integer idSeance;
    private LocalDateTime date;
    private String duree;
    private String batiment;
    private Integer numeroSalle;
    @ManyToOne
    private Formation formation;
    @ManyToOne
    private Salle salles;
}
