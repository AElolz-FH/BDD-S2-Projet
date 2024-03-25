package fr.uphf.formations.ressources.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class FormationDTO {
    private Integer idFormation;
    private String libelle;
    private String description;
    private String idFormateur;
    private LocalDateTime dateCreation;
    private double prix;
}