package fr.uphf.formations.ressources;


import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOInput;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOOuput;
import fr.uphf.formations.dto.getAllSeancesDTO.getAllSeancesDTOOutput;
import fr.uphf.formations.dto.getSeanceByIdDTO.getSeanceByIdDTOOutput;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceInputDTO;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceOutputDTO;
import fr.uphf.formations.repositories.SeanceRepository;
import fr.uphf.formations.services.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
public class SeanceRessource {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SeanceService seanceService;
    public SeanceRessource(SeanceRepository seanceRepository, SeanceService seanceService) {
        this.seanceRepository = seanceRepository;
        this.seanceService = seanceService;
    }


    @PostMapping("/")
    public ResponseEntity<creationSeanceDTOOuput> creerSeance(@RequestBody creationSeanceDTOInput seanceDTO) {
        System.out.println("Requête reçue pour créer la séance avec la date : " + seanceDTO.getDate() + " ...");
        return ResponseEntity.ok(this.seanceService.createSeance(seanceDTO));

    }

    @GetMapping("/{id}")
    public ResponseEntity<getSeanceByIdDTOOutput> getSeanceById(@PathVariable Integer id) {
        System.out.println("Requête reçue pour lister la séance avec l'id : " + id + " ...");
        return ResponseEntity.ok(this.seanceService.getSeanceById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<getAllSeancesDTOOutput>> getAllSeances() {
        System.out.println("Requête reçue pour lister toutes les séances ...");
        return ResponseEntity.ok(this.seanceService.getAllSeances());
    }


    @PutMapping("/{id}")
    public ResponseEntity<putSeanceOutputDTO> putSeanceById(@PathVariable Integer id, @RequestBody putSeanceInputDTO seanceDTO) {
        System.out.println("Requête reçue pour modifier la séance avec l'id : " + id + " ...");
        return ResponseEntity.ok(this.seanceService.putSeanceById(id, seanceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeanceById(@PathVariable Integer id) {
        System.out.println("Requête reçue pour supprimer la séance avec l'id : " + id + " ...");
        return ResponseEntity.ok(this.seanceService.deleteSeanceById(id));
    }

}
