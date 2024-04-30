package fr.uphf.formations.ressources.creation.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFormationInputDTO {
    private Integer id;
    private String libelle;
    private String description;
    private FormateurDTO formateur;
}

