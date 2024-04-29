package fr.uphf.formations.dto.ressources;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormationFromAPIDTO {
    private Integer id;
    private String libelle;
    private String description;
    private String message;
}
