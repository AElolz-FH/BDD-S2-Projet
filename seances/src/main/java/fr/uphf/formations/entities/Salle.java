package fr.uphf.formations.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Salle {
    @Id
    private Integer id;
    private Integer numeroSalle;
    private Integer capacite;
    private String nomSalle;
    private String batiment;
    private boolean disponible;
    @OneToOne(mappedBy = "salles")
    private Seance seance;
}
