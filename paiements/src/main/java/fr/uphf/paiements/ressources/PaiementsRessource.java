package fr.uphf.paiements.ressources;

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
public class PaiementsRessource {

    @Builder
    @Getter
    @Setter
    public static class PaiementsRessourceListDTO {
        private String idPaiement;
        private LocalDateTime datePaiement;
        private String montant;
        private String provenance; // carte ou virement
    }

    @GetMapping
    public ResponseEntity<List<PaiementsRessourceListDTO>> getCartes() {
        System.out.println("Requête reçue pour lister les cartes");
        return ResponseEntity.ok(Arrays.asList(
                PaiementsRessourceListDTO.builder().idPaiement("1").datePaiement(LocalDateTime.now()).montant("100").provenance("carte").build(),
                PaiementsRessourceListDTO.builder().idPaiement("2").datePaiement(LocalDateTime.now()).montant("200").provenance("virement").build()
        ));
    }
}
