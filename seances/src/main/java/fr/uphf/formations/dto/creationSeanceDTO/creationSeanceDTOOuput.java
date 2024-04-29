package fr.uphf.formations.dto.creationSeanceDTO;

import fr.uphf.formations.dto.ressources.FormationFromAPIDTO;
import fr.uphf.formations.dto.ressources.SalleFromAPIDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class creationSeanceDTOOuput {
    private String date;
    private String duree;
    private SalleFromAPIDTO salleFromAPIDTO;
    private FormationFromAPIDTO formationFromAPIDTO;
    private String message;


}
