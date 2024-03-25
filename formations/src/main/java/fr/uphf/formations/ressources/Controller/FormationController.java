package fr.uphf.formations.ressources.Controller;

import fr.uphf.formations.ressources.DTO.FormationDTO;
import fr.uphf.formations.ressources.DTO.FormationDTOResponse;
import fr.uphf.formations.ressources.Service.FormationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {

    private final FormationService formationService;

    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping("")
    public ResponseEntity<List<FormationDTO>> getAllFormation() {
        List<FormationDTO> formations = formationService.getAllFormations();
        return ResponseEntity.ok(formations);
    }

    @PostMapping("/create")
    public ResponseEntity<FormationDTOResponse> createFormation(@RequestBody FormationDTO formationDTO) {
        FormationDTOResponse createdFormation = formationService.createFormation(formationDTO);
        return ResponseEntity.ok(createdFormation);
    }
}
