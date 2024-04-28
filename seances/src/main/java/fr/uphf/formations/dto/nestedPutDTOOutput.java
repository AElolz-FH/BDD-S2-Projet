package fr.uphf.formations.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class nestedPutDTOOutput {
    private Integer numeroSalle;
    private String batiment;
    private String nomFormation;
    private String nomFormateur;
    private String message;
}
