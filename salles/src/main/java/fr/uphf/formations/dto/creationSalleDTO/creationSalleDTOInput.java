package fr.uphf.formations.dto.creationSalleDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class creationSalleDTOInput {
    private Integer numeroSalle;
    private Integer capacite;
    private String nomSalle;
    private String batiment;
    private boolean isDisponible;
}
