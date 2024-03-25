package fr.uphf.formations.ressources;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;


@RequestMapping()
@RestController
public class SalleRessource {

    @Builder
    @Getter
    @Setter
    public static class SallesItemListDTO {
        private String numero;
        private List<LocalDateTime> horaires;
        private String reservant;
    }

    @GetMapping
    public ResponseEntity<List<SallesItemListDTO>> getCartes() {
        System.out.println("Requête reçue pour lister les cartes");
        return ResponseEntity.ok(Arrays.asList(
                SallesItemListDTO.builder().numero("1")
                        .horaires(List.of(LocalDateTime.now()))
                        .reservant("Moi").build(),
                SallesItemListDTO.builder().numero("2")
                        .horaires(List.of(LocalDateTime.now()))
                        .reservant("Moi").build()
        ));
    }
}
