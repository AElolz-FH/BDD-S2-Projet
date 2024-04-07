package fr.uphf.formations.dto.putSeanceDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class putSeanceInputDTO {
    private String date;
    private String duree;
    private String batiment;
    private Integer numeroSalle;
}
