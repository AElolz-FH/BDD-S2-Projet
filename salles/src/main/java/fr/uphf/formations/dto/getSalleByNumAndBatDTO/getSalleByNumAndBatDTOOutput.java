package fr.uphf.formations.dto.getSalleByNumAndBatDTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class getSalleByNumAndBatDTOOutput {
    private String numeroSalle;
    private String nomSalle;
    private int capacite;
    private String batiment;
    private boolean isDisponible;
}
