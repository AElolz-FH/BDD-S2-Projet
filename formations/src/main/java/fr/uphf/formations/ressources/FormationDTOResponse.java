package fr.uphf.formations.ressources;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FormationDTOResponse {
    private Integer idFormation;
    private String libelle;
    private String description;
    private Integer idFormateur;
    private String dateCreation;
    private double prix;
}
