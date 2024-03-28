package fr.uphf.formations.ressources;

import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOInput;
import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOOutput;
import fr.uphf.formations.repositories.SalleRepository;
import fr.uphf.formations.services.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping()
@RestController
public class SalleRessource {
    /*
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
     */
    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SalleService salleService;

    SalleRessource(SalleRepository salleRepository, SalleService salleService) {
        this.salleRepository = salleRepository;
        this.salleService = salleService;
    }

    @PostMapping("/")
    public ResponseEntity<creationSalleDTOOutput> postSalles(@RequestBody creationSalleDTOInput salleDTO) {
        creationSalleDTOOutput createSalleResponseDTO = this.salleService.createSalle(salleDTO);
        System.out.println("Requête reçue pour créer une salle");
        return ResponseEntity.ok(createSalleResponseDTO);
    }

}
