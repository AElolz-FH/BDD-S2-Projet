package fr.uphf.utilisateurs.dto.create;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUtilisateurResponseDTO {
    private String nom;
    private String prenom;
    private String message;
}
