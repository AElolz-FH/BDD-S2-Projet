package fr.uphf.formations.dto.modifierSalleDispoDTO;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class modifierSalleDispoDTOOutput {
    private Integer numeroSalle;
    private boolean isDisponible;
}
