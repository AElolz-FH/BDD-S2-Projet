package fr.uphf.formations.ressources.creation.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormateurDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
}
