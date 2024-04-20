package fr.uphf.formations.ressources;

import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOInput;
import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOOutput;
import fr.uphf.formations.dto.getAllSallesDTO.getAllSallesDTOOutput;
import fr.uphf.formations.dto.getSalleByNumAndBatDTO.getSalleByNumAndBatDTOOutput;
import fr.uphf.formations.dto.getSalleByNumeroDTO.getSalleByNumeroDTOOutput;
import fr.uphf.formations.dto.getSalleDTOid.getSalleDTOidOutput;
import fr.uphf.formations.dto.modifierSalleDTO.modifierSalleDTOInput;
import fr.uphf.formations.dto.modifierSalleDTO.modifierSalleDTOOutput;
import fr.uphf.formations.dto.modifierSalleDispoDTO.modiferSalleDispoDTOInput;
import fr.uphf.formations.dto.modifierSalleDispoDTO.modifierSalleDispoDTOOutput;
import fr.uphf.formations.exceptions.SalleNotFoundException;
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

    @GetMapping("/{id}")
    public ResponseEntity<getSalleDTOidOutput> getSalleById(@PathVariable Integer id) throws SalleNotFoundException {
        getSalleDTOidOutput getSalleByIdResponseDTO = this.salleService.getSalleById(id);
        System.out.println("Requête reçue pour obtenir une salle");
        return ResponseEntity.ok(getSalleByIdResponseDTO);
    }

    @GetMapping("/byNum/{numeroSalle}")
    public ResponseEntity<getSalleByNumeroDTOOutput> getSalleByNumero(@PathVariable Integer numeroSalle) throws SalleNotFoundException {
        getSalleByNumeroDTOOutput getSalleByNumeroDTOOutput = this.salleService.getSalleByNumero(numeroSalle);
        System.out.println("Requête reçue pour obtenir une salle par son numéro");
        return ResponseEntity.ok(getSalleByNumeroDTOOutput);
    }

    @GetMapping("/numeroSalle={numeroSalle}/batiment={batiment}")
    public ResponseEntity<getSalleByNumAndBatDTOOutput> getSalleByNumAndBat(@PathVariable(required = true) Integer numeroSalle,@PathVariable(required = true) String batiment) throws SalleNotFoundException {
        getSalleByNumAndBatDTOOutput getSalleByNumAndBatDTOOutput = this.salleService.getSalleByNumeroAndBat(numeroSalle,batiment);
        System.out.println("Requête reçue pour obtenir une salle par son numéro et son batiment");
        return ResponseEntity.ok(getSalleByNumAndBatDTOOutput);
    }

    @GetMapping("")
    public ResponseEntity<getAllSallesDTOOutput> getAllSalles() {
        getAllSallesDTOOutput getAllSallesResponseDTO = this.salleService.getAllSalles();
        System.out.println("Requête reçue pour obtenir toutes les salles");
        return ResponseEntity.ok(getAllSallesResponseDTO);
    }

    @PutMapping("/{numeroSalle}")
    public ResponseEntity<modifierSalleDTOOutput> modifierSalles(@RequestBody modifierSalleDTOInput salleDTO) {
        modifierSalleDTOOutput modifierSalleResponseDTO = this.salleService.modifierSalle(salleDTO);
        System.out.println("Requête reçue pour modifier une salle");
        return ResponseEntity.ok(modifierSalleResponseDTO);
    }

    @PutMapping("/dispo")
    public ResponseEntity<modifierSalleDispoDTOOutput> modifierDispoSalles(@RequestBody modiferSalleDispoDTOInput salleDTO) {
        modifierSalleDispoDTOOutput modifierSalleResponseDTO = this.salleService.modifierDispoSalle(salleDTO);
        System.out.println("Requête reçue pour modifier la disponibilité d'une salle");
        return ResponseEntity.ok(modifierSalleResponseDTO);
    }

    @DeleteMapping("/numeroSalle={numeroSalle}/batiment={batiment}")
    public ResponseEntity<String> deleteSalle(@PathVariable(required = true) Integer numeroSalle,@PathVariable(required = true) String batiment) {
        this.salleService.deleteSalle(numeroSalle,batiment);
        System.out.println("Requête reçue pour supprimer une salle");
        return ResponseEntity.ok("Salle supprimée");
    }

}
