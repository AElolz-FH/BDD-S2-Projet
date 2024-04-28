package fr.uphf.formations.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Formateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
}

