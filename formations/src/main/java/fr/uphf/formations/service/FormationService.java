package fr.uphf.formations.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import fr.uphf.formations.config.WebClientConfig;
import fr.uphf.formations.entities.Formateur;
import fr.uphf.formations.entities.Seance;
import fr.uphf.formations.repository.FormateurRepository;
import fr.uphf.formations.repository.SeanceRepository;
import fr.uphf.formations.ressources.creation.dto.CreateFormationInputDTO;
import fr.uphf.formations.ressources.creation.dto.CreateFormationResponseDTO;
import fr.uphf.formations.entities.Formations;
import fr.uphf.formations.repository.FormationRepository;
import fr.uphf.formations.ressources.modification.dto.AddSeance.AddSeanceDTOOutput;
import fr.uphf.formations.ressources.modification.dto.AddFormateur.ModifyFormationOutputDTO;
import fr.uphf.formations.service.api.SeanceFromAPIDTO;
import fr.uphf.formations.service.api.UtilisateurFromAPIDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationService {
    @Autowired
    private final FormationRepository formationRepository;
    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private WebClientConfig webClient;
    /*
    @Autowired
    private UserService userService;

     */
    public FormationService(FormationRepository formationRepository, WebClientConfig webClientConfig/*UserService userService*/,FormateurRepository formateurRepository,SeanceRepository seanceRepository) {
        this.formationRepository = formationRepository;
        this.webClient = webClientConfig;
        //this.userService = userService;
        this.formateurRepository = formateurRepository;
        this.seanceRepository = seanceRepository;
    }

    public List<CreateFormationInputDTO> getAllFormations() {
        // la liste est chargée deux fois /!\ il vaudrait mieux la charger qu'une seule et unique fois
        List<Formations> formations = this.formationRepository.findAll();
        if (formations == null) {
            CreateFormationResponseDTO.builder()
                    .message("Aucune formation n'a été trouvée")
                    .build();
        }
        return formationRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public CreateFormationResponseDTO createFormation(CreateFormationInputDTO createFormationInputDTO) {

        List<Formations> formations = this.formationRepository.findAll();

        Formations formation = Formations.builder()
                .libelle(createFormationInputDTO.getLibelle())
                .description(createFormationInputDTO.getDescription())
                .prix(createFormationInputDTO.getPrix())
                .formateur(null)
                .participants(null)
                .build();

        if(formations.stream().anyMatch(f -> f.getLibelle().equals(formation.getLibelle()))){
            return CreateFormationResponseDTO.builder()
                    .message("La formation existe déjà")
                    .build();
        }

        if (formation.getLibelle().isEmpty()) {
            return CreateFormationResponseDTO.builder()
                    .message("Le libellé de la formation ne peut pas être vide")
                    .build();
        }
        if(formation == null){
            return CreateFormationResponseDTO.builder()
                    .message("La formation n'a pas été créée")
                    .build();
        }
        Formations savedFormation = formationRepository.save(formation);

        return CreateFormationResponseDTO.builder()
                .id(savedFormation.getId())
                .libelle(savedFormation.getLibelle())
                .description(savedFormation.getDescription())
                .prix(savedFormation.getPrix())
                .message("La formation a été créée")
                .build();
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
                .id(formationEntity.getId())
                .libelle(formationEntity.getLibelle())
                .description(formationEntity.getDescription())
                .prix(formationEntity.getPrix())
                .build();
    }

    public ModifyFormationOutputDTO modifyFormation(String idFormation, String idFormateur) throws JsonProcessingException {
        Formations formation = formationRepository.findById(idFormation).orElseThrow();


        //UtilisateurFromAPIDTO formateur = userService.getUserData(Integer.parseInt(idFormateur));
        UtilisateurFromAPIDTO formateur = webClient.webClientBuilder().baseUrl("http://localhost:9000/utilisateurs/")
                .build()
                .get()
                .uri("/" + idFormateur)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UtilisateurFromAPIDTO.class)
                .block();

        //http://localhost:9000/utilisateurs/1

        if(formateur == null) {
            return ModifyFormationOutputDTO.builder()
                    .message("Le formateur n'a pas été trouvé")
                    .build();
        }

        if(!formateur.isFormateur()) {
            return ModifyFormationOutputDTO.builder()
                    .message("L'utilisateur n'est pas un formateur")
                    .build();
        }

        Formateur toSave = Formateur.builder()
                .idUtilisateur(formateur.getId())
                .email(formateur.getEmail())
                .nom(formateur.getNom())
                .prenom(formateur.getPrenom())
                .build();

        this.formateurRepository.save(toSave);
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
                .message("Le formateur a été ajouté à la formation")
                .build();
    }
    /*

    @Transactional
    public AddSeanceDTOOutput addSeance(String idFormation,String idSeance) {
        Formations formation = formationRepository.findById(idFormation).orElseThrow();
        if(formation == null) {
            return AddSeanceDTOOutput.builder()
                    .message("La formation n'existe pas")
                    .build();
        }
        List<Seance> seances = this.seanceRepository.findAll();
        if (seances == null) {
            System.out.println("La liste de séances est vide pour le moment, création de la liste de séances");
            seances = new ArrayList<>();
        }


        SeanceFromAPIDTO seanceFromAPIDTO = webClient.baseUrl("http://localhost:9000/seances/")
                .build()
                .get()
                .uri("/" + idSeance)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SeanceFromAPIDTO.class)
                .block();



        if(seanceFromAPIDTO == null) {
            return AddSeanceDTOOutput.builder()
                    .message("La séance distante n'a pas été trouvée")
                    .build();
        }

        Seance SeanceToAdd = Seance.builder()
                .id(Integer.valueOf(idSeance))
                .date(seanceFromAPIDTO.getDate())
                .duree(seanceFromAPIDTO.getDuree())
                .dateFin(seanceFromAPIDTO.getDate().plusHours(Long.parseLong(seanceFromAPIDTO.getDuree())))
                .numeroSalle(seanceFromAPIDTO.getNumeroSalle())
                .batiment(seanceFromAPIDTO.getBatiment())
                .build();

        this.seanceRepository.save(SeanceToAdd);
        System.out.println("Sauvegarde de la séance...");

        List<Seance> seancesFromFormation = formation.getSeances();
        seancesFromFormation.add(SeanceToAdd);
        if(seancesFromFormation == null || seancesFromFormation.isEmpty()) {
            return AddSeanceDTOOutput.builder()
                    .message("La liste de séances de la formation est vide, la séance n'a pas été ajoutée dans la liste")
                    .build();


        }



        formation.setSeances(seancesFromFormation);
        System.out.println("La séance a été ajoutée à la liste de séances de la formation");

        this.formationRepository.save(formation);
        this.formationRepository.flush();

        if (formation.getSeances() == null) {
            return AddSeanceDTOOutput.builder()
                    .message("La séance n'a pas été ajoutée à la formation")
                    .build();
        }

        return AddSeanceDTOOutput.builder()
                .numeroSalle(seanceFromAPIDTO.getNumeroSalle())
                .idSeance(Integer.valueOf(idSeance))
                .duree(seanceFromAPIDTO.getDuree())
                .batiment(seanceFromAPIDTO.getBatiment())
                .date(seanceFromAPIDTO.getDate())
                .message("La séance a été ajoutée à la formation")
                .build();
    }


     */


    public String deleteFormation(String idFormation) {
        Formations formation = formationRepository.findById(idFormation).orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        formationRepository.delete(formation);
        return "La formation a été supprimée";
    }
}
