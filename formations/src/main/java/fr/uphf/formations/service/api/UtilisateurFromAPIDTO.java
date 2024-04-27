package fr.uphf.formations.service.api;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurFromAPIDTO {
    private Integer id;
    private boolean formateur;
    private LocalDateTime dateCreation;
    private String nom;
    private String email;
    private String prenom;
}
