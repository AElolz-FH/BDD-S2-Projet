package fr.uphf.formations.ressources.modification.dto.AddSeance;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AjoutSeanceDTOInput {
    private String idSeance;
    private String libelleFormation;
    private LocalDateTime date;
    private Integer numeroSalle;
    private String batiment;
}
