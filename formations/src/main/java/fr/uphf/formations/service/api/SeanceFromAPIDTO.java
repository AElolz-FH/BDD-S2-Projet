package fr.uphf.formations.service.api;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SeanceFromAPIDTO {
    private Integer id;
    private LocalDateTime date;
    private String duree;
    private String batiment;
    private String salle;
    private Integer numeroSalle;
}
