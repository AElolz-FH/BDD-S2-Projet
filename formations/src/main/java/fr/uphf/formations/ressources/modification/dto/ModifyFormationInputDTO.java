package fr.uphf.formations.ressources.modification.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyFormationInputDTO {
    private String idFormateur;
    private String idUtilisateurs;
}
