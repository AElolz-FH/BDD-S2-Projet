package fr.uphf.formations.ressources;

import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOInput;
import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOOutput;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceInputDTO;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceOutputDTO;
import fr.uphf.formations.dto.getAllSallesDTO.getAllSallesDTOOutput;
import fr.uphf.formations.dto.getSalleByNumAndBatDTO.getSalleByNumAndBatDTOOutput;
import fr.uphf.formations.dto.getSalleByNumeroDTO.getSalleByNumeroDTOOutput;
import fr.uphf.formations.dto.getSalleDTOid.getSalleDTOidOutput;
import fr.uphf.formations.dto.modifierSalleDTO.modifierSalleDTOInput;
import fr.uphf.formations.dto.modifierSalleDTO.modifierSalleDTOOutput;
import fr.uphf.formations.dto.modifierSalleDispoDTO.modiferSalleDispoDTOInput;
import fr.uphf.formations.dto.modifierSalleDispoDTO.modifierSalleDispoDTOOutput;
import fr.uphf.formations.entities.Salles;
import fr.uphf.formations.exceptions.SalleNotFoundException;
import fr.uphf.formations.repositories.SalleRepository;
import fr.uphf.formations.services.SalleService;
import jakarta.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping()
@RestController
public class SalleRessource {

    @Autowired
    private SalleService salleService;
    @Autowired
    private SalleRepository salleRepository;

    SalleRessource(SalleService salleService) {
        this.salleService = salleService;
    }

    @PostMapping("/")
    public ResponseEntity<creationSalleDTOOutput> postSalles(@RequestBody creationSalleDTOInput salleDTO) {
        creationSalleDTOOutput createSalleResponseDTO = this.salleService.createSalle(salleDTO);
        if(createSalleResponseDTO.getMessage().equals("La salle n'a pas été créée, les attributs ne sont pas tous référencés")){
            System.out.println("La salle n'a pas été créée, les attributs ne sont pas tous référencés");
            return ResponseEntity.badRequest().body(createSalleResponseDTO.builder().message("La salle n'a pas été créée, les attributs ne sont pas tous référencés").build());
        }
        if(createSalleResponseDTO.getMessage().equals("La salle existe déjà")){
            System.out.println("La salle existe déjà");
            return ResponseEntity.badRequest().body(createSalleResponseDTO.builder().message("La salle existe déjà").build());
        }
        System.out.println("Requête reçue pour créer une salle");
        return ResponseEntity.ok(createSalleResponseDTO);
    }

    @PostMapping("/seance")
    public ResponseEntity<creationSeanceOutputDTO> creerSeanceInSalle(@RequestBody creationSeanceInputDTO creationSeanceInputDTO){
        creationSeanceOutputDTO creationSeanceOutputDTO = this.salleService.receiveAndAddSeance(creationSeanceInputDTO);
        if(creationSeanceOutputDTO.getMessage().equals("La salle n'a pas été trouvée")){
            System.out.println("La salle n'a pas été trouvée");
            return ResponseEntity.badRequest().body(creationSeanceOutputDTO.builder().message("La salle n'a pas été trouvée").build());
        }
        if(creationSeanceOutputDTO.getMessage().equals("La salle n'est pas disponible")){
            System.out.println("La salle n'est pas disponible");
            return ResponseEntity.badRequest().body(creationSeanceOutputDTO.builder().message("La salle n'est pas disponible").build());
        }
        if(creationSeanceOutputDTO.getMessage().equals("La salle n'a pas été trouvée")){
            System.out.println("La salle n'a pas été trouvée");
            return ResponseEntity.badRequest().body(creationSeanceOutputDTO.builder().message("La salle n'a pas été trouvée").build());
        }
        System.out.println("Requête reçue pour créer une séance dans une salle");
        return ResponseEntity.ok(creationSeanceOutputDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<getSalleDTOidOutput> getSalleById(@PathVariable Integer id) throws SalleNotFoundException {
        getSalleDTOidOutput getSalleByIdResponseDTO = this.salleService.getSalleById(id);
        if(getSalleByIdResponseDTO.getMessage().equals("La salle n'a pas été trouvée")){
            System.out.println("Salle non trouvée");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getSalleByIdResponseDTO.builder().message("Salle non trouvée").build());
        }
        System.out.println("Requête reçue pour obtenir une salle");
        return ResponseEntity.ok(getSalleByIdResponseDTO);
    }

    @GetMapping("/byNum/{numeroSalle}")
    public ResponseEntity<getSalleByNumeroDTOOutput> getSalleByNumero(@PathVariable Integer numeroSalle) throws SalleNotFoundException {
        getSalleByNumeroDTOOutput getSalleByNumeroDTOOutput = this.salleService.getSalleByNumero(numeroSalle);
        System.out.println("Requête reçue pour obtenir une salle par son numéro");
        if(getSalleByNumeroDTOOutput.getMessage().equals("La salle n'a pas été trouvée")){
            System.out.println("Salle non trouvée");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getSalleByNumeroDTOOutput.builder().message("Salle non trouvée").build());
        }

        return ResponseEntity.ok(getSalleByNumeroDTOOutput);
    }


    @GetMapping("/numeroSalle={numeroSalle}/batiment={batiment}")
    public ResponseEntity<getSalleByNumAndBatDTOOutput> getSalleByNumAndBat(@PathVariable(required = true) Integer numeroSalle,@PathVariable(required = true) String batiment) throws SalleNotFoundException {
        Salles salle = this.salleRepository.findByNumeroSalleAndBatiment(numeroSalle,batiment);
        if(salle.getNumeroSalle() != null || salle.getNumeroSalle() != 0 && (salle.getBatiment() != null)){
            getSalleByNumAndBatDTOOutput output = getSalleByNumAndBatDTOOutput.builder()
                    .nomSalle(salle.getNomSalle())
                    .numeroSalle(String.valueOf(salle.getNumeroSalle()))
                    .capacite(salle.getCapacite())
                    .batiment(salle.getBatiment())
                    .isDisponible(salle.isDisponible())
                    .build();
            return ResponseEntity.ok(output);
        }
        return ResponseEntity.badRequest().body(getSalleByNumAndBatDTOOutput.builder()
                .message("La salle n'a pas été trouvée")
                .build());
    }



    @GetMapping("")
    public ResponseEntity<getAllSallesDTOOutput> getAllSalles() {
        getAllSallesDTOOutput getAllSallesResponseDTO = this.salleService.getAllSalles();
        System.out.println("Requête reçue pour obtenir toutes les salles");
        if(getAllSallesResponseDTO.getMessage().equals("Aucune salle n'a été trouvée")){
            System.out.println("Aucune salle n'a été trouvée");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getAllSallesResponseDTO.builder().message("Aucune salle n'a été trouvée").build());
        }
        return ResponseEntity.ok(getAllSallesResponseDTO);
    }

    @PutMapping("/{numeroSalle}")
    public ResponseEntity<modifierSalleDTOOutput> modifierSalles(@RequestBody modifierSalleDTOInput salleDTO,@PathVariable Integer numeroSalle) {
        modifierSalleDTOOutput modifierSalleResponseDTO = this.salleService.modifierSalle(salleDTO,numeroSalle);
        System.out.println("Requête reçue pour modifier une salle");
        if(modifierSalleResponseDTO.getMessage().equals("La salle n'a pas été trouvée")){
            System.out.println("Salle non trouvée");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(modifierSalleResponseDTO.builder().message("Salle non trouvée").build());
        }
        return ResponseEntity.ok(modifierSalleResponseDTO);
    }

    @PutMapping("/dispo={numeroSalle}")
    public ResponseEntity<modifierSalleDispoDTOOutput> modifierDispoSalles(@RequestBody modiferSalleDispoDTOInput salleDTO,@PathVariable Integer numeroSalle) {
        modifierSalleDispoDTOOutput modifierSalleResponseDTO = this.salleService.modifierDispoSalle(salleDTO,numeroSalle);
        if(modifierSalleResponseDTO.getMessage().equals("La salle n'a pas été trouvée")){
            System.out.println("Salle non trouvée");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(modifierSalleResponseDTO.builder().message("Salle non trouvée").build());
        }
        System.out.println("Requête reçue pour modifier la disponibilité d'une salle");
        return ResponseEntity.ok(modifierSalleResponseDTO);
    }

    @DeleteMapping("/numeroSalle={numeroSalle}/batiment={batiment}")
    public ResponseEntity<String> deleteSalle(@PathVariable(required = true) Integer numeroSalle,@PathVariable(required = true) String batiment) {
        String message = this.salleService.deleteSalle(numeroSalle,batiment);
        if(message.equals("La salle n'a pas été trouvée")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La salle n'a pas été trouvée");
        }
        return ResponseEntity.ok("Salle supprimée");
    }

}
