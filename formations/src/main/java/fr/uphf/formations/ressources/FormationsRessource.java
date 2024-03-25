package fr.uphf.formations.ressources;

import fr.uphf.formations.ressources.DTO.FormationDTO;
import fr.uphf.formations.ressources.Entity.Formations;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;


@RequestMapping()
@RestController
public class FormationsRessource {

    @Builder
    @Getter
    @Setter
    public static class FormationsItemListDTO {
        private String idFormation;
        private String idFormateur;
        private LocalDateTime dateCreation;
        private List<Integer> idSalles;
        private List<Integer> idParticipants;
    }

    @GetMapping
    public ResponseEntity<List<FormationsItemListDTO>> getCartes() {
        System.out.println("Requête reçue pour lister des formations");
        return ResponseEntity.ok(Arrays.asList(
                FormationsItemListDTO.builder().idFormation("1").dateCreation(LocalDateTime.now()).build(),
                FormationsItemListDTO.builder().idFormation("2").dateCreation(LocalDateTime.now()).build()
        ));
    }
    @PostMapping("/create/{idFormation}")
    public ResponseEntity<Formations> postFormations(@PathVariable String idFormation, @RequestBody FormationDTO formationDTO) {
        System.out.println("Requête reçue pour créer une formation");
        return ResponseEntity.ok(Formations.builder().id(Integer.valueOf(idFormation))
                .libelle(formationDTO.getLibelle())
                .description(formationDTO.getDescription())
                .build());
    }

    @PutMapping("/modify/{idFormation}")
    public ResponseEntity<Formations> putFormations(@PathVariable String idFormation, @RequestBody Formations Formations) {
        System.out.println("Requête reçue pour modifier une formation");
        return ResponseEntity.ok(Formations.builder().id(Integer.valueOf(idFormation))
                .libelle(Formations.getLibelle())
                .description(Formations.getDescription())
                .formateur(Formations.getFormateur())
                .participants(Formations.getParticipants())
                .build());
    }


}
