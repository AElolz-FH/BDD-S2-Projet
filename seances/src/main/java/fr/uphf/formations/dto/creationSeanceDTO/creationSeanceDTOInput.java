package fr.uphf.formations.dto.creationSeanceDTO;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class creationSeanceDTOInput {
    private LocalDateTime date;
    private String duree;
    private String batiment;
    private Integer numeroSalle;
    private String nomFormation;
    private String nomFormateur;
    private List<Integer> idUtilisateurs;
}
