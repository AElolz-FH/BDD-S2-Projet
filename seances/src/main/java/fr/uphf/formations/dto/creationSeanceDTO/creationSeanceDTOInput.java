package fr.uphf.formations.dto.creationSeanceDTO;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class creationSeanceDTOInput {
    private LocalDateTime date;
    private Integer duree;
    private List<Integer> idUtilisateurs;
}
