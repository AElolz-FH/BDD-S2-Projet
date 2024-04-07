package fr.uphf.formations.dto.modifierSalleDTO;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class modifierSalleDTOOutput {
    private Integer numeroSalle;
    private Integer capacite;
    private String nomSalle;
    private String batiment;
}
