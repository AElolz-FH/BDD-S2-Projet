package fr.uphf.formations.dto.ressources;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalleFromAPIDTO {
    private Integer numeroSalle;
    private Integer capacite;
    private String batiment;
    private boolean disponible;
}
