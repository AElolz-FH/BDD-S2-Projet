package fr.uphf.formations.dto;


import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class nestedPutDTOInput {
    private Integer numeroSalle;
    private String capacite;
    private String nomFormation;
    private String nomFormateur;
}
