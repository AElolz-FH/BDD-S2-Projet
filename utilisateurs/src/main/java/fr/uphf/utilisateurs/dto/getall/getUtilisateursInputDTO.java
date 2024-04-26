package fr.uphf.utilisateurs.dto.getall;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class getUtilisateursInputDTO {
    @Id
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
}
