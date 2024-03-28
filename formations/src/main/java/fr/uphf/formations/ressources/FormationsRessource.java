package fr.uphf.formations.ressources;

import fr.uphf.formations.ressources.creation.dto.CreateFormationInputDTO;
import fr.uphf.formations.ressources.creation.dto.CreateFormationResponseDTO;
import fr.uphf.formations.ressources.modification.dto.ModifyFormationInputDTO;
import fr.uphf.formations.ressources.modification.dto.ModifyFormationOutputDTO;
import fr.uphf.formations.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class FormationsRessource {

    @Autowired
    private FormationService formationService;

    @PostMapping("/")
    public ResponseEntity<CreateFormationResponseDTO> postFormations(@RequestBody CreateFormationInputDTO formationDTO) {
        CreateFormationResponseDTO createFormationResponseDTO = this.formationService.createFormation(formationDTO);
        System.out.println("Requête reçue pour créer une formation");
        return ResponseEntity.ok(createFormationResponseDTO);
    }

    @PutMapping("/{idFormation}")
    public ResponseEntity<ModifyFormationOutputDTO> putFormations(@PathVariable String idFormation, @RequestBody ModifyFormationInputDTO modifyFormationInputDTO) {
        System.out.println("Requête reçue pour modifier une formation");
        return ResponseEntity.ok(this.formationService.modifyFormation(idFormation, modifyFormationInputDTO));
    }

    @GetMapping("/")
    public ResponseEntity<List<CreateFormationInputDTO>> getAllFormation() {
        List<CreateFormationInputDTO> formations = formationService.getAllFormations();
        return ResponseEntity.ok(formations);
    }

}
