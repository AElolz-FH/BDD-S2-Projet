package fr.uphf.formations.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class addSalleToSeanceOutputDTO {
    private Integer idSeance;
    private Integer numeroSalle;
    private String batiment;
    private String message;
}
