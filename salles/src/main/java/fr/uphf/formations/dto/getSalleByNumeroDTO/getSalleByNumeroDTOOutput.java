package fr.uphf.formations.dto.getSalleByNumeroDTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class getSalleByNumeroDTOOutput {
    private String numeroSalle;
    private String nomSalle;
    private int capacite;
    private String batiment;
    private boolean isDisponible;
}
