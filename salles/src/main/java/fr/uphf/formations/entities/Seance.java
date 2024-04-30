package fr.uphf.formations.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idSeance;
    private String libelleFormation;
    private String date;
    private Integer numeroSalle;
    @ManyToOne
    private Salles salle;
    private String batiment;
}
