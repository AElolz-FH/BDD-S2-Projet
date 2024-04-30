package fr.uphf.formations.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer numeroSalle;
    private Integer capacite;
    private String nomSalle;
    private String batiment;
    @OneToMany
    private List<Seance> seances;
    private boolean isDisponible;
}
