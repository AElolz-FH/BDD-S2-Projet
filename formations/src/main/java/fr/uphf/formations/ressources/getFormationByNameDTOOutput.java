package fr.uphf.formations.ressources;

import fr.uphf.formations.ressources.creation.dto.FormateurDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class getFormationByNameDTOOutput {
    private Integer id;
    private String libelle;
    private String description;
    private FormateurDTO formateur;
    private String message;
}
