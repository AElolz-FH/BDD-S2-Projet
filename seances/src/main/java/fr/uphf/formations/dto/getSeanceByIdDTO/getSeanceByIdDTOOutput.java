package fr.uphf.formations.dto.getSeanceByIdDTO;

import lombok.*;

import java.util.List;

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
    private List<Integer> idUtilisateurs;
    private String message;
}
