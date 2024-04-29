package fr.uphf.formations.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Formation {
    @Id
    private Integer id;
    private String libelle;
    private String description;
    @OneToMany(mappedBy = "formation")
    private List<Seance> seance;
    private String message;

}
