package fr.uphf.utilisateurs.ressources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Getter
@Setter
@RequestMapping()
@RestController
public class CompteRessource {

    @Autowired
    private CarteApiService carteApiService;

    @GetMapping("/{id}")
    public ResponseEntity<CompteDetailDTO> getCartes(@PathVariable String id) {
        System.out.println("Requête reçue pour lister les cartes");
        return ResponseEntity.ok(CompteDetailDTO.builder().numero(id).cartes(this.carteApiService.listerCartes()).build());
    }





}
