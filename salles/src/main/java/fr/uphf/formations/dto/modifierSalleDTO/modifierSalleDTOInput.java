package fr.uphf.formations.dto.modifierSalleDTO;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class modifierSalleDTOInput {
    private Integer numeroSalle;
    private String nomSalle;
    private String batiment;
    private Integer capacite;
}
