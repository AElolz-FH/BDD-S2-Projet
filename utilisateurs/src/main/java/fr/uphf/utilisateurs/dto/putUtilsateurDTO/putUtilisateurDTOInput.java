package fr.uphf.utilisateurs.dto.putUtilsateurDTO;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class putUtilisateurDTOInput {
    private Integer id;
    private String nom;
    private String prenom;
    private boolean Formateur;
}
