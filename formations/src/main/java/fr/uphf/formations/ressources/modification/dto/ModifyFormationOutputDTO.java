package fr.uphf.formations.ressources.modification.dto;

import lombok.*;

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
}
