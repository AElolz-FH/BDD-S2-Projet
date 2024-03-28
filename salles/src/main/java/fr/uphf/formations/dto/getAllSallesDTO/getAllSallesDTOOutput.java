package fr.uphf.formations.dto.getAllSallesDTO;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class getAllSallesDTOOutput {
    List<getSallesDTOOutput> salles;
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getSallesDTOOutput {
        private Integer id;
        private Integer numeroSalle;
        private Integer capacite;
        private String nomSalle;
        private String batiment;
        private boolean isDisponible;
    }
}
