package fr.uphf.formations.ressources.creation.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateFormationInputDTO {
    private Integer id;
    private String libelle;
    private String description;
    private Integer prix;
    private String message;
}