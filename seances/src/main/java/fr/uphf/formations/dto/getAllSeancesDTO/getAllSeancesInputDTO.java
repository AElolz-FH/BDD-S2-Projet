package fr.uphf.formations.dto.getAllSeancesDTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class getAllSeancesInputDTO {
    private String date;
    private String numeroSalle;
}
