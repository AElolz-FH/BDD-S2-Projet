package fr.uphf.formations.dto.creationSeanceDTO;

import fr.uphf.formations.entities.Salles;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class creationSeanceInputDTO {
    private Integer numeroSalle;
    private String batiment;
    private String libelle;
    private Integer duree;
    private LocalDateTime date;
}
