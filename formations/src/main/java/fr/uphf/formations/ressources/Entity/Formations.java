package fr.uphf.formations.ressources.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Formations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String libelle;
    private String description;
    @OneToOne
    private Formateur formateur;
    @OneToMany
    private List<Participant> participants;
}
