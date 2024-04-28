package fr.uphf.utilisateurs.ressources;


import fr.uphf.utilisateurs.dto.create.CreateUtilisateurInputDTO;
import fr.uphf.utilisateurs.dto.create.CreateUtilisateurResponseDTO;
import fr.uphf.utilisateurs.dto.getall.getUtilisateursResponseDTO;
import fr.uphf.utilisateurs.dto.putUtilsateurDTO.putUtilisateurDTOInput;
import fr.uphf.utilisateurs.dto.putUtilsateurDTO.putUtilisateurDTOOutput;
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
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/")
    public ResponseEntity<CreateUtilisateurResponseDTO> postUtilisateur(@RequestBody CreateUtilisateurInputDTO createUtilisateurInputDTO){
        CreateUtilisateurResponseDTO createUtilisateurResponseDTO = this.utilisateurService.createUtilisateur(createUtilisateurInputDTO);
        if(createUtilisateurResponseDTO.getMessage().equals("Utilisateur déjà existant")){
            return ResponseEntity.status(409).body(createUtilisateurResponseDTO);
        }
        return ResponseEntity.ok(createUtilisateurResponseDTO);
    }
    @GetMapping("/")
    public ResponseEntity<List<getUtilisateursResponseDTO>> getUtilisateur(){
        List<getUtilisateursResponseDTO> getUtilisateursResponseDTO = this.utilisateurService.getUtilisateur();
        if(getUtilisateursResponseDTO.isEmpty()){
            return ResponseEntity.status(404).body(getUtilisateursResponseDTO);
        }
        return ResponseEntity.ok(getUtilisateursResponseDTO);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<getUtilisateursResponseDTO> getOneUserById(@PathVariable Integer id){
        System.out.println("Requête reçue pour l'utilisateur avec l'id : " + id);
        getUtilisateursResponseDTO user = this.utilisateurService.getOneUserById(id);
        if(user == null || user.getMessage().equals("Aucun utilisateur trouvé")){
            return ResponseEntity.status(404).body(user);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<putUtilisateurDTOOutput> putUtilisateur(@PathVariable Integer id, @RequestBody putUtilisateurDTOInput putUtilisateurDTOInput){
        System.out.println("Requête reçue pour modifier l'utilisateur avec l'id : " + id);
        putUtilisateurDTOOutput putUtilisateurDTOOutput = this.utilisateurService.modifyUtilisateur(id, putUtilisateurDTOInput);
        if(putUtilisateurDTOOutput.getMessage().equals("Aucun utilisateur trouvé")){
            return ResponseEntity.status(404).body(putUtilisateurDTOOutput);
        }
        return ResponseEntity.ok(putUtilisateurDTOOutput);
    }

    @DeleteMapping("/nom={nom}&prenom={prenom}")
    public ResponseEntity<String> deleteUtilisateur(@PathVariable String nom, @PathVariable String prenom){
        System.out.println("Requête reçue pour supprimer l'utilisateur avec le nom : " + nom + " et le prénom : " + prenom);
        String message = this.utilisateurService.deleteUser(nom, prenom);
        return ResponseEntity.ok(message);
    }

}
