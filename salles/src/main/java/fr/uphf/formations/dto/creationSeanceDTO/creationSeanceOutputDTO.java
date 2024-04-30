package fr.uphf.formations.dto.creationSeanceDTO;

import fr.uphf.formations.entities.Salles;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class creationSeanceOutputDTO {
    private Salles salles;
    private Integer idSeance;
    private LocalDateTime date;
    private Integer duree;
    private String message;
}
