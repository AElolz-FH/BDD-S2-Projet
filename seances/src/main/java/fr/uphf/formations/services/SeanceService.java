package fr.uphf.formations.services;

import fr.uphf.formations.config.WebClientConfig;
import fr.uphf.formations.dto.addSalleToSeanceOutputDTO;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOInput;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOOuput;
import fr.uphf.formations.dto.getAllSeancesDTO.getAllSeancesDTOOutput;
import fr.uphf.formations.dto.getSeanceByIdDTO.getSeanceByIdDTOOutput;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceInputDTO;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceOutputDTO;
import fr.uphf.formations.entities.Salle;
import fr.uphf.formations.entities.Seance;
import fr.uphf.formations.repositories.SalleRepository;
import fr.uphf.formations.repositories.SeanceRepository;
import fr.uphf.formations.ressources.SalleFromAPIDTO;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private WebClientConfig webClient;
    public SeanceService(SeanceRepository seanceRepository,WebClientConfig webClient, SalleRepository salleRepository) {
        this.seanceRepository = seanceRepository;
        this.webClient = webClient;
        this.salleRepository = salleRepository;
    }

    public creationSeanceDTOOuput createSeance(creationSeanceDTOInput seanceDTO) {
        List<Seance> seances = this.seanceRepository.findAll();
        Seance seance = Seance.builder()
                .date(seanceDTO.getDate())
                .duree(seanceDTO.getDuree())
                .batiment(seanceDTO.getBatiment())
                .idUtilisateurs(seanceDTO.getIdUtilisateurs())
                .nomFormateur(seanceDTO.getNomFormateur())
                .numeroSalle(seanceDTO.getNumeroSalle())
                .nomSalle(seanceDTO.getNomFormation())
                .nomFormation(seanceDTO.getNomFormation())
                .build();
        if(seance==null){
            return creationSeanceDTOOuput.builder().message("La séance n'a pas été créée").build();
        }
        if(seance.getDate().equals(null) || seance.getDuree().equals(null) || seance.getBatiment().equals(null) || seance.getNomFormation().equals(null) || seance.getNumeroSalle().equals(null)){
            return creationSeanceDTOOuput.builder().message("La séance n'a pas été créée, les attributs ne sont pas tous référencés").build();
        }

        if(seances.stream().anyMatch(s -> s.getDate().equals(seance.getDate()) && s.getNumeroSalle().equals(seance.getNumeroSalle()) && s.getBatiment().equals(seance.getBatiment())))
        {return creationSeanceDTOOuput.builder().message("La séance n'a pas été créée, une séance existe déjà à cette date dans cette salle").build();}
        //sauvegarder la séance en base
        this.seanceRepository.save(seance);

        return creationSeanceDTOOuput.builder()
                .date(seance.getDate().toString())
                .duree(seance.getDuree())
                .batiment(seance.getBatiment())
                .idUtilisateurs(seance.getIdUtilisateurs())
                .nomFormateur(seance.getNomFormateur())
                .numeroSalle(seance.getNumeroSalle())
                .nomFormation(seance.getNomFormation())
                .message("La séance a été créée")
                .build();

    }

    public getSeanceByIdDTOOutput getSeanceById(Integer id) {
        Seance seance = this.seanceRepository.findById(id).get();
        if(seance.equals(null)){
            return getSeanceByIdDTOOutput.builder().message("La séance n'existe pas ou n'a pas été trouvée").build();
        }
        return getSeanceByIdDTOOutput.builder()
                .date(seance.getDate().toString())
                .duree(seance.getDuree())
                .batiment(seance.getBatiment())
                .idUtilisateurs(seance.getIdUtilisateurs())
                .nomFormateur(seance.getNomFormateur())
                .numeroSalle(seance.getNumeroSalle())
                .nomFormation(seance.getNomFormation())
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
                .id(seance.getIdSalle())
                .date(seance.getDate().toString())
                .duree(seance.getDuree())
                .batiment(seance.getBatiment())
                .numeroSalle(seance.getNumeroSalle())
                .nomFormation(seance.getNomFormation())
                .nomFormateur(seance.getNomFormateur())
                .message("La séance a été modifiée")
                .build();

    }

    public List<getAllSeancesDTOOutput> getAllSeances() {
        List<Seance> seances = this.seanceRepository.findAll();
        List<getAllSeancesDTOOutput> seancesDTO = new ArrayList<>();
        if(seances.isEmpty()) {
            seancesDTO.add(getAllSeancesDTOOutput.builder().message("Aucune séance n'a été trouvée").build());
            return seancesDTO;
        }
        seancesDTO.get(0).setMessage("Les séances ont été trouvées");
        for (Seance seance : seances) {
            seancesDTO.add(getAllSeancesDTOOutput.builder()
                    .id(seance.getIdSalle())
                    .date(seance.getDate().toString())
                    .duree(seance.getDuree())
                    .batiment(seance.getBatiment())
                    .numeroSalle(seance.getNumeroSalle())
                    .nomFormation(seance.getNomFormation())
                    .nomFormateur(seance.getNomFormateur())
                    .build());
        }
        return seancesDTO;
    }

    public String deleteSeanceById(Integer id) {
        Seance seance = this.seanceRepository.findById(id).orElseThrow( () -> new NotFoundException("La séance avec l'id " + id + " n'existe pas"));
        this.seanceRepository.delete(seance);
        return "La séance avec l'id " + id + " a été supprimée";
    }

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

        //on sauvegarde la salle distante en base avant d'effectuer l'association
        this.salleRepository.save(toSave);

        Seance seanceToSave = seance.get();
        //on associe la salle à la séance
        seanceToSave.setSalles(toSave);
        //on save la seance
        this.seanceRepository.save(seanceToSave);

        return addSalleToSeanceOutputDTO.builder().numeroSalle(toSave.getNumeroSalle())
                .batiment(toSave.getBatiment())
                .idSeance(idSeance)
                .message("La salle a été ajoutée à la séance")
                .build();
    }

}
