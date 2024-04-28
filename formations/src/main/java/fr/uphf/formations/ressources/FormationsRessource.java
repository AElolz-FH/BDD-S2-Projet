package fr.uphf.formations.ressources;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.uphf.formations.repository.FormateurRepository;
import fr.uphf.formations.ressources.creation.dto.CreateFormationInputDTO;
import fr.uphf.formations.ressources.creation.dto.CreateFormationResponseDTO;
import fr.uphf.formations.ressources.modification.dto.AddSeance.AddSeanceDTOOutput;
import fr.uphf.formations.ressources.modification.dto.AddFormateur.ModifyFormationInputDTO;
import fr.uphf.formations.ressources.modification.dto.AddFormateur.ModifyFormationOutputDTO;
import fr.uphf.formations.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class FormationsRessource {

    @Autowired
    private FormationService formationService;

    @PostMapping("/")
    public ResponseEntity<CreateFormationResponseDTO> postFormations(@RequestBody CreateFormationInputDTO formationDTO) {
        CreateFormationResponseDTO createFormationResponseDTO = this.formationService.createFormation(formationDTO);
        String message = createFormationResponseDTO.getMessage();
        if (message.equals("Le libellé de la formation ne peut pas être vide") || message==null){
            return ResponseEntity.badRequest().body(CreateFormationResponseDTO.builder().message(createFormationResponseDTO.getMessage()).build());
        }
        if(message.equals("La formation n'a pas été créée") || message==null){
            return ResponseEntity.internalServerError().body(CreateFormationResponseDTO.builder().message(createFormationResponseDTO.getMessage()).build());
        }
        if(message.equals("La formation existe déjà")){
            return ResponseEntity.badRequest().body(CreateFormationResponseDTO.builder().message(createFormationResponseDTO.getMessage()).build());
        }
        System.out.println("Requête reçue pour créer une formation");
        return ResponseEntity.ok(createFormationResponseDTO);
    }

    @PutMapping("users/{idFormation}/{idFormateur}")
    public ResponseEntity<ModifyFormationOutputDTO> putFormations(@PathVariable String idFormation, @PathVariable String idFormateur) throws JsonProcessingException {
        ModifyFormationOutputDTO modifyFormationOutputDTO = this.formationService.modifyFormation(idFormation, idFormateur);
        if(modifyFormationOutputDTO.getMessage().equals("L'utilisateur n'est pas un formateur")){
            return ResponseEntity.badRequest().body(ModifyFormationOutputDTO.builder().message(modifyFormationOutputDTO.getMessage()).build());
        }
        if(modifyFormationOutputDTO.getMessage().equals("La formation n'existe pas")){
            return ResponseEntity.status(404).body(ModifyFormationOutputDTO.builder().message(modifyFormationOutputDTO.getMessage()).build());
        }
        if(modifyFormationOutputDTO.getMessage().equals("Le formateur n'a pas été trouvé")){
            return ResponseEntity.status(404).body(ModifyFormationOutputDTO.builder().message(modifyFormationOutputDTO.getMessage()).build());
        }
        return ResponseEntity.ok(modifyFormationOutputDTO);
    }


    @PutMapping("/{idFormation}/{idSeance}")
    public ResponseEntity<AddSeanceDTOOutput> addSeanceToFormation(@PathVariable String idFormation, @PathVariable String idSeance) {
        AddSeanceDTOOutput addSeanceDTOOutput = this.formationService.addSeance(idFormation, idSeance);
        System.out.println("Requête reçue pour ajouter une séance d'id "+idSeance+" à une formation avec l'ID : " + idFormation);
        if(addSeanceDTOOutput.getMessage().equals("La séance distante n'a pas été trouvée")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AddSeanceDTOOutput.builder().message(addSeanceDTOOutput.getMessage()).build());
        }
        if(addSeanceDTOOutput.getMessage().equals("La liste de séances de la formation est vide, la séance n'a pas été ajoutée dans la liste")){
            return ResponseEntity.badRequest().body(AddSeanceDTOOutput.builder().message(addSeanceDTOOutput.getMessage()).build());
        }
        if(addSeanceDTOOutput.getMessage().equals("La séance n'a pas été ajoutée à la formation")){
            return ResponseEntity.internalServerError().body(AddSeanceDTOOutput.builder().message(addSeanceDTOOutput.getMessage()).build());
        }
        return ResponseEntity.ok(addSeanceDTOOutput);
    }




    @GetMapping("/")
    public ResponseEntity<List<CreateFormationInputDTO>> getAllFormation() {
        List<CreateFormationInputDTO> formations = formationService.getAllFormations();
        System.out.println("Requête reçue pour récupérer les formations");
        if(formations.isEmpty()){
            List<CreateFormationInputDTO> formationsResponse = new ArrayList<>();
            formationsResponse.add(CreateFormationInputDTO.builder().message("Aucune formation trouvée").build());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(formationsResponse);
        }
        return ResponseEntity.ok(formations);
    }

    @DeleteMapping("/{idFormation}")
    public ResponseEntity<String> deleteFormation(@PathVariable String idFormation) {
        String message = this.formationService.deleteFormation(idFormation);
        System.out.println("Requête reçue pour supprimer une formation avec l'ID : " + idFormation);
        return ResponseEntity.ok(message);
    }

}
