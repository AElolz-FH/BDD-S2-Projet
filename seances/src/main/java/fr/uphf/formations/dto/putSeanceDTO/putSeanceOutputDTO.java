package fr.uphf.formations.dto.putSeanceDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class putSeanceOutputDTO {
    private Integer id;
    private String date;
    private String duree;
    private String batiment;
    private Integer numeroSalle;
    private String nomFormation;
    private String nomFormateur;
    private String message;
}
