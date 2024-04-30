package fr.uphf.formations.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Seance {
    @Id
    private Integer id;
    private LocalDateTime date;
    private String duree;
    private Integer numeroSalle;
    private String batiment;
    private String libelleFormation;
}
