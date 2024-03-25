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
public class CarteRessource {

    @Builder
    @Getter
    @Setter
    public static class CartesItemListDTO {
        private String numero;
        private LocalDateTime dateExpiration;
    }

    @GetMapping
    public ResponseEntity<List<CartesItemListDTO>> getCartes() {
        System.out.println("Requête reçue pour lister les cartes");
        return ResponseEntity.ok(Arrays.asList(
                CartesItemListDTO.builder().numero("1").dateExpiration(LocalDateTime.now()).build(),
                CartesItemListDTO.builder().numero("2").dateExpiration(LocalDateTime.now()).build()
        ));
    }
}
