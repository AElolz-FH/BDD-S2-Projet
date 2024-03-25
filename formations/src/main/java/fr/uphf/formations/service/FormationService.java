package fr.uphf.formations.service;
import fr.uphf.formations.entities.Formateur;
import fr.uphf.formations.ressources.creation.dto.CreateFormationInputDTO;
import fr.uphf.formations.ressources.creation.dto.CreateFormationResponseDTO;
import fr.uphf.formations.ressources.FormationDTOResponse;
import fr.uphf.formations.entities.Formations;
import fr.uphf.formations.repository.FormationRepository;
import fr.uphf.formations.ressources.modification.dto.ModifyFormationInputDTO;
import fr.uphf.formations.ressources.modification.dto.ModifyFormationOutputDTO;
import fr.uphf.formations.service.api.UtilisateurFromAPIDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationService {
    @Autowired
    private final FormationRepository formationRepository;

    @Autowired
    private WebClient.Builder webClient;
    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<CreateFormationInputDTO> getAllFormations() {
        return formationRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public CreateFormationResponseDTO createFormation(CreateFormationInputDTO createFormationInputDTO) {
        Formations formation = Formations.builder()
                .libelle(createFormationInputDTO.getLibelle())
                .description(createFormationInputDTO.getDescription())
                .prix(createFormationInputDTO.getPrix())
                .build();
        Formations savedFormation = formationRepository.save(formation);
        // TODO Creer une méthode EntityToCreateFormationResponseDTO()
        return EntityToCreateFormationResponseDTO(String.valueOf(savedFormation.getId()));
    }

    public CreateFormationResponseDTO EntityToCreateFormationResponseDTO(String idFormation) {
        Formations formation = formationRepository.findById(idFormation).orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        return CreateFormationResponseDTO.builder()
                .id(formation.getId())
                .libelle(formation.getLibelle())
                .description(formation.getDescription())
                .prix(formation.getPrix())
                .build();
    }



    private CreateFormationInputDTO mapEntityToDTO(Formations formationEntity) {
        return CreateFormationInputDTO.builder()
                .libelle(formationEntity.getLibelle())
                .description(formationEntity.getDescription())
                .prix(formationEntity.getPrix())
                .build();
    }

    private FormationDTOResponse mapEntityToDTOResponse(Formations formationEntity) {
        return FormationDTOResponse.builder()
                .idFormation(formationEntity.getId())
                .libelle(formationEntity.getLibelle())
                .description(formationEntity.getDescription())
                .prix(formationEntity.getPrix())
                .build();
    }

    //pour créer une formation
    private Formations creerFormation(CreateFormationInputDTO createFormationInputDTO) {
        Formations formation = Formations.builder()
                .libelle(createFormationInputDTO.getLibelle())
                .description(createFormationInputDTO.getDescription())
                .prix(createFormationInputDTO.getPrix())
                .build();
        formationRepository.save(formation);
        return formation;
    }

    public ModifyFormationOutputDTO modifyFormation(String idFormation, ModifyFormationInputDTO modifyFormationInputDTO) {
        // Verifier que la formation existe à partir de l'id formation fournit en entrée de la méthode
        Formations formation = formationRepository.findById(idFormation).orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        // Verifier que le formateur existe à partir de l'id formateur fournit en entrée de la méthode en appelant l'API sur utilisateur
        // Si le formateur n'existe pas, on renvoie une erreur
        UtilisateurFromAPIDTO formateur = webClient.baseUrl("http://utilisateurs/")
                .build()
                .get()
                .uri("/" + modifyFormationInputDTO.getIdFormateur())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UtilisateurFromAPIDTO.class)
                .block();

        if(formateur == null) {
            throw new RuntimeException("Formateur non trouvé");
        }
        if(!formateur.isFormateur()) {
            throw new RuntimeException("L'utilisateur n'est pas un formateur");
        }

        Formateur toSave = Formateur.builder()
                .email(formateur.getEmail())
                .nom(formateur.getNom())
                .prenom(formateur.getPrenom())
                .idUtilisateur(formateur.getId())
                .build();
        formation.setFormateur(toSave);

        this.formationRepository.save(formation);

        return ModifyFormationOutputDTO.builder()
                .libelle(formation.getLibelle())
                .description(formation.getDescription())
                .formateur(ModifyFormationOutputDTO.ModifyFormateurPayload.builder()
                        .email(formateur.getEmail())
                        .nom(formateur.getNom())
                        .prenom(formateur.getPrenom())
                        .id(formateur.getId())
                        .formateur(formateur.isFormateur())
                        .build())
                .build();
    }
}
