package fr.uphf.formations.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer numeroSalle;
    private Integer capacite;
    private String nomSalle;
    private String batiment;
    private boolean disponible;
    @OneToMany(mappedBy = "salles")
    private List<Seance> seance;
}
