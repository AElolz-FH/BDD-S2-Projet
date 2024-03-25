package fr.uphf.formations.ressources;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;


@RequestMapping()
@RestController
public class FormationsRessource {

    @Builder
    @Getter
    @Setter
    public static class FormationsItemListDTO {
        private String idFormation;
        private String idFormateur;
        private LocalDateTime dateCreation;
        private List<Integer> idSalles;
        private List<Integer> idParticipants;
    }

    @GetMapping
    public ResponseEntity<List<FormationsItemListDTO>> getCartes() {
        System.out.println("Requête reçue pour lister des formations");
        return ResponseEntity.ok(Arrays.asList(
                FormationsItemListDTO.builder().idFormation("1").dateCreation(LocalDateTime.now()).build(),
                FormationsItemListDTO.builder().idFormation("2").dateCreation(LocalDateTime.now()).build()
        ));
    }
}
