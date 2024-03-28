package fr.uphf.utilisateurs.dto.getall;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class getUtilisateursResponseDTO {
    private String nom;
    private String prenom;
    private String email;
}
