package fr.uphf.formations.dto.creationSeanceDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class creationSeanceDTOOuput {
    private String date;
    private String duree;
    private String message;
}
