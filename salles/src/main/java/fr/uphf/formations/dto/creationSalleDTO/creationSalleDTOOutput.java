package fr.uphf.formations.dto.creationSalleDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class creationSalleDTOOutput {
    private Integer numeroSalle;
    private Integer capacite;
    private String nomSalle;
    private String batiment;
    private String message;
}
