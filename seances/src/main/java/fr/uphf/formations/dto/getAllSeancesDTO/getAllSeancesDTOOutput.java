package fr.uphf.formations.dto.getAllSeancesDTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class getAllSeancesDTOOutput {
    private Integer id;
    private String date;
    private String duree;
    private String batiment;
    private Integer numeroSalle;
    private String nomFormation;
    private String message;
}
