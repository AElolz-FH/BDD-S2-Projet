package fr.uphf.formations.ressources;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalleFromAPIDTO {
    private Integer id;
    private Integer numeroSalle;
    private Integer capacite;
    private String batiment;
    private boolean disponible;
}
