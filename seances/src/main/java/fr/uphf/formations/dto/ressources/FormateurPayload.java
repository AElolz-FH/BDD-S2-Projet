package fr.uphf.formations.dto.ressources;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormateurPayload {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
}
