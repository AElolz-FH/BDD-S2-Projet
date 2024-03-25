package fr.uphf.utilisateurs.ressources;


import fr.uphf.utilisateurs.dto.create.CreateUtilisateurInputDTO;
import fr.uphf.utilisateurs.dto.create.CreateUtilisateurResponseDTO;
import fr.uphf.utilisateurs.dto.getall.getUtilisateursInputDTO;
import fr.uphf.utilisateurs.dto.getall.getUtilisateursResponseDTO;
import fr.uphf.utilisateurs.repositories.UtilisateurRepository;
import fr.uphf.utilisateurs.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UtilisateursRessource {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/")
    public ResponseEntity<CreateUtilisateurResponseDTO> postUtilisateur(@RequestBody CreateUtilisateurInputDTO createUtilisateurInputDTO){
        CreateUtilisateurResponseDTO createUtilisateurResponseDTO = this.utilisateurService.createUtilisateur(createUtilisateurInputDTO);
        return ResponseEntity.ok(createUtilisateurResponseDTO);
    }
    @GetMapping("/")
    public ResponseEntity<List<getUtilisateursResponseDTO>> getUtilisateur(){
        List<getUtilisateursResponseDTO> getUtilisateursResponseDTO = this.utilisateurService.getUtilisateur();
        return ResponseEntity.ok(getUtilisateursResponseDTO);
    }
}
