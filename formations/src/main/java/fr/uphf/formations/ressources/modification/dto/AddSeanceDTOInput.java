package fr.uphf.formations.ressources.modification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSeanceDTOInput {
    private Integer idSeance;
    private LocalDateTime date;
    private LocalDateTime dateFin;
    private String duree;
    private String batiment;
    private String salle;
    private Integer numeroSalle;
}
