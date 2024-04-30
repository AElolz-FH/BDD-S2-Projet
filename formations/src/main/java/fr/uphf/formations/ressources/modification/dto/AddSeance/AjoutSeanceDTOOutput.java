package fr.uphf.formations.ressources.modification.dto.AddSeance;

import fr.uphf.formations.entities.Formateur;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AjoutSeanceDTOOutput {
    private String libelle;
    private LocalDateTime date;
    private Formateur formateur;
    private String duree;
    private String batiment;
    private Integer numeroSalle;
    private String idSeance;
    private String message;
}
