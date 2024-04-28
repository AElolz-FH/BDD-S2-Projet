package fr.uphf.formations.ressources.modification.dto.AddSeance;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSeanceDTOOutput {
    private Integer idSeance;
    private LocalDateTime date;
    private String duree;
    private Integer numeroSalle;
    private String batiment;
    private String nomFormateur;
    private String nomFormation;
    private String message;
}
