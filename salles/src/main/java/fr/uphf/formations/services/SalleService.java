package fr.uphf.formations.services;

import fr.uphf.formations.config.RabbitMQConfig;
import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOInput;
import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOOutput;
import fr.uphf.formations.dto.getAllSallesDTO.getAllSallesDTOOutput;
import fr.uphf.formations.dto.getSalleByNumAndBatDTO.getSalleByNumAndBatDTOOutput;
import fr.uphf.formations.dto.getSalleByNumeroDTO.getSalleByNumeroDTOOutput;
import fr.uphf.formations.dto.getSalleDTOid.getSalleDTOidOutput;
import fr.uphf.formations.dto.modifierSalleDTO.modifierSalleDTOInput;
import fr.uphf.formations.dto.modifierSalleDTO.modifierSalleDTOOutput;
import fr.uphf.formations.dto.modifierSalleDispoDTO.modiferSalleDispoDTOInput;
import fr.uphf.formations.dto.modifierSalleDispoDTO.modifierSalleDispoDTOOutput;
import fr.uphf.formations.entities.Salles;
import fr.uphf.formations.entities.Seance;
import fr.uphf.formations.exceptions.SalleNotFoundException;
import fr.uphf.formations.repositories.SalleRepository;
import fr.uphf.formations.repositories.SeanceRepository;
import jakarta.ws.rs.NotFoundException;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceInputDTO;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceOutputDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public SalleService(SalleRepository salleRepository, SeanceRepository seanceRepository) {
        this.salleRepository = salleRepository;
        this.seanceRepository = seanceRepository;
    }

    public creationSalleDTOOutput createSalle(creationSalleDTOInput salleDTO) {
        List<Salles> salles = this.salleRepository.findAll();
        if(salles.stream().anyMatch(salle -> salle.getNumeroSalle().equals(salleDTO.getNumeroSalle()))){
            System.out.println("Salle already exists");
            return creationSalleDTOOutput.builder().message("La salle existe déjà").build();
        }

        if(salleDTO.getNumeroSalle().equals(null) || salleDTO.getNomSalle().equals(null) || salleDTO.getBatiment().equals(null)){
            return creationSalleDTOOutput.builder().message("La salle n'a pas été créée, les attributs ne sont pas tous référencés").build();
        }

        Salles salleBase = Salles.builder()
                .numeroSalle(salleDTO.getNumeroSalle())
                .nomSalle(salleDTO.getNomSalle())
                .capacite(salleDTO.getCapacite())
                .isDisponible(salleDTO.isDisponible())
                .batiment(salleDTO.getBatiment())
                .build();

        salleRepository.save(salleBase);

        return creationSalleDTOOutput.builder()
                .numeroSalle(salleBase.getNumeroSalle())
                .nomSalle(salleBase.getNomSalle())
                .capacite(salleBase.getCapacite())
                .batiment(salleBase.getBatiment())
                .message("La salle a été créée avec succès")
                .build();
    }

    public getSalleDTOidOutput getSalleById(Integer id) throws SalleNotFoundException {
        //si la salle n'est pas dans la base alors throw une exception salle non trouvée
        Salles salle = this.salleRepository.findById(id).orElseThrow(() -> new NotFoundException("Salle not found"));
        //sinon on retourne un dto en prenant les attributs de la salle
        return getSalleDTOidOutput.builder()
                .id(salle.getId())
                .numeroSalle(salle.getNumeroSalle())
                .capacite(salle.getCapacite())
                .batiment(salle.getBatiment())
                .isDisponible(salle.isDisponible())
                .message("La salle a été trouvée avec succès")
                .build();
    }

    public getAllSallesDTOOutput getAllSalles() {
        List<Salles> salles = salleRepository.findAll();
        if(salles.isEmpty()){
            return getAllSallesDTOOutput.builder().message("Aucune salle n'a été trouvée").build();
        }
        return getAllSallesDTOOutput.builder().salles(salles.stream().map(salle -> getAllSallesDTOOutput.getSallesDTOOutput.builder()
                        .id(salle.getId())
                        .numeroSalle(salle.getNumeroSalle())
                        .capacite(salle.getCapacite())
                        .nomSalle(salle.getNomSalle())
                        .batiment(salle.getBatiment())
                        .isDisponible(salle.isDisponible())
                        .build()).collect(Collectors.toList()))
                .message("Les salles ont été trouvées avec succès")
                .build();
    }

    public modifierSalleDTOOutput modifierSalle(modifierSalleDTOInput salleDTO,Integer numeroSalle) {
        Salles salle = this.salleRepository.findByNumeroSalle(numeroSalle);
        if (salle == null) {
            return modifierSalleDTOOutput.builder().message("La salle n'a pas été trouvée").build();
        }

        salle.setId(salle.getId());
        salle.setCapacite(salleDTO.getCapacite());
        salle.setNomSalle(salleDTO.getNomSalle());
        salle.setNumeroSalle(salleDTO.getNumeroSalle());
        salle.setBatiment(salleDTO.getBatiment());
        salle.setDisponible(salle.isDisponible());

        this.salleRepository.save(salle);

        return modifierSalleDTOOutput.builder()
                .numeroSalle(salle.getNumeroSalle())
                .nomSalle(salle.getNomSalle())
                .capacite(salle.getCapacite())
                .batiment(salle.getBatiment())
                .message("La salle a été modifiée avec succès")
                .build();
    }


    public modifierSalleDispoDTOOutput modifierDispoSalle(modiferSalleDispoDTOInput modifierSalleDTOInput,Integer numeroSalle){
        Salles salle = this.salleRepository.findByNumeroSalle(numeroSalle);

        if (salle == null) {
            return modifierSalleDispoDTOOutput.builder().message("La salle n'a pas été trouvée").build();
        }

        salle.setDisponible(modifierSalleDTOInput.isDisponible());
        this.salleRepository.save(salle);

        return modifierSalleDispoDTOOutput.builder()
                .numeroSalle(salle.getNumeroSalle())
                .isDisponible(salle.isDisponible())
                .message("La disponibilité de la salle a été modifiée avec succès")
                .build();
    }

    public getSalleByNumeroDTOOutput getSalleByNumero(@RequestParam(required = true) Integer numeroSalle){
        Salles salle = this.salleRepository.findByNumeroSalle(numeroSalle);
        if(salle == null){
            return getSalleByNumeroDTOOutput.builder().message("La salle n'a pas été trouvée").build();
        }
        return getSalleByNumeroDTOOutput.builder()
                .numeroSalle(String.valueOf(salle.getNumeroSalle()))
                .nomSalle(salle.getNomSalle())
                .capacite(salle.getCapacite())
                .batiment(salle.getBatiment())
                .isDisponible(salle.isDisponible())
                .message("La salle a été trouvée avec succès")
                .build();
    }

    public getSalleByNumAndBatDTOOutput getSalleByNumeroAndBat(@RequestParam(required = true) Integer numeroSalle,@RequestParam(required=true) String batiment){
        Salles salle = this.salleRepository.findByNumeroSalleAndBatiment(numeroSalle,batiment);
        if(salle == null){
            throw new RuntimeException("Salle non trouvée");
        }
        return getSalleByNumAndBatDTOOutput.builder()
                .numeroSalle(String.valueOf(salle.getNumeroSalle()))
                .nomSalle(salle.getNomSalle())
                .capacite(salle.getCapacite())
                .batiment(salle.getBatiment())
                .isDisponible(salle.isDisponible())
                .message("La salle a été trouvée avec succès")
                .build();
    }

    public Salles getSalleByNumAndBat2(Integer numeroSalle, String batiment) {
        return this.salleRepository.findByNumeroSalleAndBatiment(numeroSalle, batiment);
    }

    public String deleteSalle(Integer numeroSalle, String batiment) {
        Salles salle = this.salleRepository.findByNumeroSalleAndBatiment(numeroSalle, batiment);
        if(salle == null){
            return "La salle n'a pas été trouvée";
        }
        this.salleRepository.delete(salle);

        // Publier un message RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SALLE_DELETED, numeroSalle + "#" + batiment);

        return "La salle : " + salle.getNomSalle() + " a été supprimée";
    }

    public getSalleByNumAndBatDTOOutput getSalleByNumAndBat(Integer numeroSalle, String batiment) {
        Salles salle = this.salleRepository.findByNumeroSalleAndBatiment(numeroSalle,batiment);
        if(salle == null){
            return getSalleByNumAndBatDTOOutput.builder().message("La salle n'a pas été trouvée").build();
        }
        return getSalleByNumAndBatDTOOutput.builder()
                .numeroSalle(String.valueOf(salle.getNumeroSalle()))
                .nomSalle(salle.getNomSalle())
                .capacite(salle.getCapacite())
                .batiment(salle.getBatiment())
                .isDisponible(salle.isDisponible())
                .message("La salle a été trouvée avec succès")
                .build();
    }

    public creationSeanceOutputDTO creerSeance(creationSeanceInputDTO creationSeanceInputDTO) {

        if(creationSeanceInputDTO == null){
            return creationSeanceOutputDTO.builder()
                    .message("Le body en entrée est null")
                    .build();
        }

        System.out.println("body : "+creationSeanceInputDTO);

        int numeroSalle = creationSeanceInputDTO.getNumeroSalle();

        Salles salle = this.salleRepository.findByNumeroSalleAndBatiment(numeroSalle, creationSeanceInputDTO.getBatiment());

        if (salle == null) {
            return creationSeanceOutputDTO.builder().message("La salle n'a pas été trouvée").build();
        }

        Seance nouvelleSeance = Seance.builder()
                .date(creationSeanceInputDTO.getDate())
                .salle(salle)
                .duree(creationSeanceInputDTO.getDuree())
                .batiment(creationSeanceInputDTO.getBatiment())
                .libelleFormation(creationSeanceInputDTO.getLibelle())
                .build();

        // Sauvegarder la séance dans la base de données des séances
        this.seanceRepository.save(nouvelleSeance);

        // On ajoute la séance dans la liste des séances de la salle
        List<Seance> seances = salle.getSeances();
        if (seances == null) {
            seances = new ArrayList<>();
        } else {
            seances.add(nouvelleSeance);
        }
        // On sauvegarde la salle pour apporter les modifications
        this.salleRepository.save(salle);

        Salles salleOutput = Salles.builder()
                .id(salle.getId())
                .nomSalle(salle.getNomSalle())
                .numeroSalle(salle.getNumeroSalle())
                .batiment(salle.getBatiment())
                .capacite(salle.getCapacite())
                .isDisponible(salle.isDisponible())
                .build();


        return creationSeanceOutputDTO.builder()
                .idSeance(nouvelleSeance.getIdSeance())
                .duree(nouvelleSeance.getDuree())
                .date(nouvelleSeance.getDate())
                .salles(salleOutput)
                .message("La séance a été créée avec succès")
                .build();
    }
}

