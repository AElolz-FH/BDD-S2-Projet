package fr.uphf.utilisateurs.dto.create;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUtilisateurInputDTO {
    private String nom;
    private String prenom;
    private String email;
}
