package fr.uphf.formations.ressources.modification.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyFormationOutputDTO {
    private String libelle;
    private String description;
    private double prix;
    private ModifyFormateurPayload formateur;
    private List<ModifySeancePayload> seances;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyFormateurPayload {
        private Integer id;
        private boolean formateur;
        private String nom;
        private String email;
        private String prenom;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ModifySeancePayload{
        private Integer id;
        private LocalDateTime dateDebut;
        private LocalDateTime dateFin;
    }
}
