package fr.uphf.formations.dto.getSeanceByIdDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class getSeanceByIdDTOOutput {
    private String date;
    private String duree;
    private Integer numeroSalle;
    private String batiment;
    private String nomFormation;
    private String nomFormateur;
    private String message;
}
