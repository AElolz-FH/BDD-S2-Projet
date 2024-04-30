package fr.uphf.formations.services;

import fr.uphf.formations.config.RabbitMQConfig;
import fr.uphf.formations.config.WebClientConfig;
import fr.uphf.formations.dto.addSalleToSeanceOutputDTO;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOInput;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOOuput;
import fr.uphf.formations.dto.getAllSeancesDTO.getAllSeancesDTOOutput;
import fr.uphf.formations.dto.getSeanceByIdDTO.getSeanceByIdDTOOutput;
import fr.uphf.formations.dto.nestedPutDTOInput;
import fr.uphf.formations.dto.nestedPutDTOOutput;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceInputDTO;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceOutputDTO;
import fr.uphf.formations.dto.ressources.FormationFromAPIDTO;
import fr.uphf.formations.entities.Formation;
import fr.uphf.formations.entities.Salle;
import fr.uphf.formations.entities.Seance;
import fr.uphf.formations.repositories.FormationRepository;
import fr.uphf.formations.repositories.SalleRepository;
import fr.uphf.formations.repositories.SeanceRepository;
import fr.uphf.formations.dto.ressources.SalleFromAPIDTO;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private WebClientConfig webClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public SeanceService(SeanceRepository seanceRepository,WebClientConfig webClient, SalleRepository salleRepository) {
        this.seanceRepository = seanceRepository;
        this.webClient = webClient;
        this.salleRepository = salleRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleFormationDeleted(String formationId) {
        System.out.println("Received formation ID to delete: " + formationId);
        List<Seance> seances = seanceRepository.findByFormationId(Integer.valueOf(formationId));
        System.out.println("Found seances: " + seances.size());
        if (!seances.isEmpty()) {
            seanceRepository.deleteAll(seances);
            System.out.println("Séances liées à la formation " + formationId + " ont été supprimées.");
        } else {
            System.out.println("Aucune séance à supprimer pour la formation " + formationId);
        }
    }


    @RabbitListener(queues = RabbitMQConfig.SALLE_QUEUE_NAME)
    public void handleSalleDeleted(String salleInfo) {
        String[] details = salleInfo.split("#");
        String numeroSalle = details[0];
        String batiment = details[1];

        List<Seance> seances = seanceRepository.findBySalles_NumeroSalleAndSalles_Batiment(Integer.valueOf(numeroSalle), batiment);
        if (!seances.isEmpty()) {
            seanceRepository.deleteAll(seances);
            System.out.println("Séances liées à la salle numéro " + numeroSalle + " dans le bâtiment " + batiment + " ont été supprimées.");
        } else {
            System.out.println("Aucune séance à supprimer pour la salle et le bâtiment spécifiés.");
        }
    }


    public creationSeanceDTOOuput createSeance(creationSeanceDTOInput seanceDTO) {
        if(seanceDTO.getDate() == null || seanceDTO.getDuree() == null){
            return creationSeanceDTOOuput.builder().message("La séance n'a pas été créée, les attributs ne sont pas tous référencés").build();
        }

        List<Seance> seances = this.seanceRepository.findAll();

        if (seances.stream().anyMatch(s -> s.getDate().equals(seanceDTO.getDate()) && s.getDuree().equals(String.valueOf(seanceDTO.getDuree())))) {
            return creationSeanceDTOOuput.builder().message("La séance n'a pas été créée, une séance existe déjà à cette date avec cette durée").build();
        }

        FormationFromAPIDTO formationFromAPIDTO = webClient.webClientBuilder()
                .baseUrl("http://localhost:9000/formations/")
                .build()
                .get()
                .uri("/search/" + seanceDTO.getLibelle())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(FormationFromAPIDTO.class)
                .block();

        if (formationFromAPIDTO == null) {
            return creationSeanceDTOOuput.builder().message("La formation n'a pas été trouvée").build();
        }

        SalleFromAPIDTO salleFromAPIDTO = webClient.webClientBuilder()
                .baseUrl("http://localhost:9000/salles/")
                .build()
                .get()
                .uri("/numeroSalle="+seanceDTO.getNumeroSalle()+"/batiment="+seanceDTO.getBatiment())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SalleFromAPIDTO.class)
                .block();

        // Vérifier si la salle existe
        if (salleFromAPIDTO == null) {
            return creationSeanceDTOOuput.builder().message("La salle n'a pas été trouvée").build();
        }
        Salle salleToSave = Salle.builder()
                .numeroSalle(salleFromAPIDTO.getNumeroSalle())
                .capacite(salleFromAPIDTO.getCapacite())
                .batiment(salleFromAPIDTO.getBatiment())
                .disponible(salleFromAPIDTO.isDisponible())
                .build();

        Formation formationToSave = Formation.builder()
                .libelle(formationFromAPIDTO.getLibelle())
                .description(formationFromAPIDTO.getDescription())
                .build();

        Seance seanceToSave = Seance.builder()
                .date(seanceDTO.getDate())
                .duree(String.valueOf(seanceDTO.getDuree()))
                .batiment(seanceDTO.getBatiment())
                .numeroSalle(seanceDTO.getNumeroSalle())
                .salles(salleToSave)
                .formation(formationToSave)
                .build();

        this.salleRepository.save(salleToSave);
        this.formationRepository.save(formationToSave);
        this.seanceRepository.save(seanceToSave);

        this.rabbitTemplate.convertAndSend(RabbitMQConfig.SEANCE_EXCHANGE_NAME, RabbitMQConfig.SEANCE_ROUTING_KEY, seanceDTO);

        return creationSeanceDTOOuput.builder()
                .date(seanceDTO.getDate().toString())
                .duree(String.valueOf(seanceDTO.getDuree()))
                .salleFromAPIDTO(SalleFromAPIDTO.builder()
                        .numeroSalle(salleFromAPIDTO.getNumeroSalle())
                        .capacite(salleFromAPIDTO.getCapacite())
                        .batiment(salleFromAPIDTO.getBatiment())
                        .disponible(salleFromAPIDTO.isDisponible())
                        .build()
                )
                .formationFromAPIDTO(FormationFromAPIDTO.builder()
                        .id(formationToSave.getId())
                        .libelle(formationFromAPIDTO.getLibelle())
                        .description(formationFromAPIDTO.getDescription())
                        .message("La formation a été trouvée")
                        .build()
                )
                .message("La séance a été créée")
                .build();
    }


    public getSeanceByIdDTOOutput getSeanceById(Integer id) {
        Seance seance = this.seanceRepository.findById(id).orElseThrow();
        if(seance.equals(null)){
            return getSeanceByIdDTOOutput.builder().message("La séance n'existe pas ou n'a pas été trouvée").build();
        }
        return getSeanceByIdDTOOutput.builder()
                .date(seance.getDate().toString())
                .duree(seance.getDuree())
                .batiment(seance.getBatiment())
                .numeroSalle(seance.getNumeroSalle())
                .message("La séance a été trouvée")
                .build();
    }

    public putSeanceOutputDTO putSeanceById(Integer id, putSeanceInputDTO putSeanceInputDTO)
    {
        Seance seance = this.seanceRepository.findById(id).orElseThrow( () -> new NotFoundException("La séance avec l'id " + id + " n'existe pas ou n'a pas été trouvée"));

        seance.setDate(LocalDateTime.parse(putSeanceInputDTO.getDate()));
        seance.setDuree(putSeanceInputDTO.getDuree());
        seance.setBatiment(putSeanceInputDTO.getBatiment());
        seance.setNumeroSalle(putSeanceInputDTO.getNumeroSalle());

        this.seanceRepository.save(seance);

        return putSeanceOutputDTO.builder()
                .id(seance.getIdSeance())
                .date(seance.getDate().toString())
                .duree(seance.getDuree())
                //modif ici
                .batiment(seance.getBatiment())
                .numeroSalle(seance.getNumeroSalle())
                .nomFormation(seance.getFormation() != null ? seance.getFormation().getLibelle() : null)
                .message("La séance a été modifiée")
                .build();

    }

    public List<getAllSeancesDTOOutput> getAllSeances() {
        List<Seance> seances = this.seanceRepository.findAll();
        List<getAllSeancesDTOOutput> seancesDTO = new ArrayList<>();
        for (Seance seance : seances) {
            seancesDTO.add(getAllSeancesDTOOutput.builder()
                    .id(seance.getIdSeance())
                    .date(seance.getDate().toString())
                    .duree(seance.getDuree())
                    .batiment(seance.getBatiment())
                    .numeroSalle(seance.getNumeroSalle())
                    .nomFormation(seance.getFormation() != null ? seance.getFormation().getLibelle() : null)
                    .message("La séance a été trouvée")
                    .build());
        }
        return seancesDTO;
    }


    public String deleteSeanceById(Integer id) {
        Seance seance = this.seanceRepository.findById(id).orElseThrow( () -> new NotFoundException("La séance avec l'id " + id + " n'existe pas"));
        this.seanceRepository.delete(seance);
        return "La séance avec l'id " + id + " a été supprimée";
    }

    /*
    public addSalleToSeanceOutputDTO addSalleToSeance(Integer idSeance, Integer idSalle){
        Optional<Seance> seance = this.seanceRepository.findById(idSeance);
        if(seance.isEmpty()) {
            return addSalleToSeanceOutputDTO.builder().message("La ressource de la séance n'a pas été trouvée").build();
        }
        SalleFromAPIDTO salleFromAPIDTO = this.webClient.webClientBuilder()
                .baseUrl("http://localhost:9000/salles/")
                .build()
                .get()
                .uri("/" + idSalle)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SalleFromAPIDTO.class)
                .block();

        if(salleFromAPIDTO == null) {
            return addSalleToSeanceOutputDTO.builder().message("La ressource de la salle n'a pas été trouvée").build();
        }

        Salle toSave = Salle.builder()
                .id(idSalle)
                .numeroSalle(salleFromAPIDTO.getNumeroSalle())
                .capacite(salleFromAPIDTO.getCapacite())
                .batiment(salleFromAPIDTO.getBatiment())
                .disponible(salleFromAPIDTO.isDisponible())
                .build();
        Seance seanceToSave = seance.get();
        this.seanceRepository.save(seanceToSave);
        if(toSave.getSeance() == null) {
            toSave.setSeance(new ArrayList<>());
        }
        toSave.getSeance().add(seanceToSave);
        this.salleRepository.save(toSave);

        addSalleToSeanceOutputDTO salle = addSalleToSeanceOutputDTO.builder().numeroSalle(toSave.getNumeroSalle())
                .batiment(toSave.getBatiment())
                .idSeance(idSeance)
                .numeroSalle(toSave.getNumeroSalle())
                .batiment(toSave.getBatiment())
                .message("La salle a été ajoutée à la séance")
                .build();

        this.seanceRepository.save(seanceToSave);

        return salle;
    }  */

}
