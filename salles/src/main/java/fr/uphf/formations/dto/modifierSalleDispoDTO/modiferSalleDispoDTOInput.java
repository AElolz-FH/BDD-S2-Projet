package fr.uphf.formations.dto.modifierSalleDispoDTO;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class modiferSalleDispoDTOInput {
    private Integer numeroSalle;
    private boolean isDisponible;
}
