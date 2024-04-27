package fr.uphf.formations.ressources;


import fr.uphf.formations.dto.addSalleToSeanceOutputDTO;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOInput;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOOuput;
import fr.uphf.formations.dto.getAllSeancesDTO.getAllSeancesDTOOutput;
import fr.uphf.formations.dto.getSeanceByIdDTO.getSeanceByIdDTOOutput;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceInputDTO;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceOutputDTO;
import fr.uphf.formations.services.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
public class SeanceRessource {
    @Autowired
    private SeanceService seanceService;
    public SeanceRessource(SeanceService seanceService) {
        this.seanceService = seanceService;
    }


    @PostMapping("/")
    public ResponseEntity<creationSeanceDTOOuput> creerSeance(@RequestBody creationSeanceDTOInput seanceDTO) {
        creationSeanceDTOOuput seance = this.seanceService.createSeance(seanceDTO);
        if(seance.getMessage().equals("La séance n'a pas été créée")){
            return ResponseEntity.internalServerError().body(creationSeanceDTOOuput.builder().message("La séance n'a pas été créée").build());
        }
        if(seance.getMessage().equals("La séance n'a pas été créée, les attributs ne sont pas tous référencés"))
        {
            return ResponseEntity.badRequest().body(creationSeanceDTOOuput.builder().message("La séance n'a pas été créée, les attributs ne sont pas tous référencés").build());
        }
        System.out.println("Requête reçue pour créer la séance avec la date : " + seanceDTO.getDate() + " ...");
        return ResponseEntity.ok(this.seanceService.createSeance(seanceDTO));

    }

    @GetMapping("/{id}")
    public ResponseEntity<getSeanceByIdDTOOutput> getSeanceById(@PathVariable Integer id) {
        System.out.println("Requête reçue pour lister la séance avec l'id : " + id + " ...");
        getSeanceByIdDTOOutput seance = this.seanceService.getSeanceById(id);
        if(seance.getMessage().equals("La séance n'existe pas ou n'a pas été trouvée")){
            return ResponseEntity.status(404).body(getSeanceByIdDTOOutput.builder().message("La séance n'existe pas").build());
        }
        return ResponseEntity.ok(this.seanceService.getSeanceById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<getAllSeancesDTOOutput>> getAllSeances() {
        List<getAllSeancesDTOOutput> seances = this.seanceService.getAllSeances();
        if(seances.isEmpty()){
            return ResponseEntity.status(404).body(List.of(getAllSeancesDTOOutput.builder().message("Aucune séance n'a été trouvée").build()));
        }
        System.out.println("Requête reçue pour lister toutes les séances ...");
        return ResponseEntity.ok(this.seanceService.getAllSeances());
    }


    @PutMapping("/{id}")
    public ResponseEntity<putSeanceOutputDTO> putSeanceById(@PathVariable Integer id, @RequestBody putSeanceInputDTO seanceDTO) {
        System.out.println("Requête reçue pour modifier la séance avec l'id : " + id + " ...");
        putSeanceOutputDTO salle = this.seanceService.putSeanceById(id, seanceDTO);
        if(salle.getMessage().equals("La séance n'existe pas ou n'a pas été trouvée")){
            return ResponseEntity.status(404).body(putSeanceOutputDTO.builder().message("La séance n'existe pas").build());
        }
        return ResponseEntity.ok(this.seanceService.putSeanceById(id, seanceDTO));
    }

    @PutMapping("/{idSalle}/{idSeance}")
    public ResponseEntity<addSalleToSeanceOutputDTO> addSalleToSeance(@PathVariable Integer idSalle, @PathVariable Integer idSeance) {
        addSalleToSeanceOutputDTO salle = this.seanceService.addSalleToSeance(idSalle, idSeance);
        System.out.println("Requête reçue pour ajouter la salle avec l'id : " + idSalle + " à la séance avec l'id : " + idSeance + " ...");
        if(salle.getMessage().equals("La ressource de la séance n'a pas été trouvée")){
            return ResponseEntity.status(404).body(addSalleToSeanceOutputDTO.builder().message("La ressource de la salle n'a pas été trouvée").build());
        }
        if(salle.getMessage().equals("La ressource de la salle n'a pas été trouvée")){
            return ResponseEntity.status(404).body(addSalleToSeanceOutputDTO.builder().message("La ressource de la salle distante n'a pas été trouvée").build());
        }

        return ResponseEntity.ok(this.seanceService.addSalleToSeance(idSalle, idSeance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeanceById(@PathVariable Integer id) {
        System.out.println("Requête reçue pour supprimer la séance avec l'id : " + id + " ...");
        return ResponseEntity.ok(this.seanceService.deleteSeanceById(id));
    }

}
