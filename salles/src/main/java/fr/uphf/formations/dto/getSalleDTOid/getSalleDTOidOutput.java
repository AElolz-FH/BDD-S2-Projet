package fr.uphf.formations.dto.getSalleDTOid;


import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class getSalleDTOidOutput {
    private Integer id;
    private Integer numeroSalle;
    private Integer capacite;
    private String batiment;
    private boolean isDisponible;
}
