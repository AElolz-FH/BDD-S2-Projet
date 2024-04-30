package fr.uphf.formations.dto.creationSeanceDTO;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class creationSeanceDTOInput implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer numeroSalle;
    private String libelle;
    private String batiment;
    private LocalDateTime date;
    private Integer duree;
}
