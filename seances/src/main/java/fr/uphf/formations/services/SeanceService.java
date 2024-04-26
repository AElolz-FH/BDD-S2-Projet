package fr.uphf.formations.services;

import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOInput;
import fr.uphf.formations.dto.creationSeanceDTO.creationSeanceDTOOuput;
import fr.uphf.formations.dto.getAllSeancesDTO.getAllSeancesDTOOutput;
import fr.uphf.formations.dto.getSeanceByIdDTO.getSeanceByIdDTOOutput;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceInputDTO;
import fr.uphf.formations.dto.putSeanceDTO.putSeanceOutputDTO;
import fr.uphf.formations.entities.Seance;
import fr.uphf.formations.repositories.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;
    public SeanceService(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
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

        if(seances.stream().anyMatch(s -> s.getDate().equals(seance.getDate()) && s.getNumeroSalle().equals(seance.getNumeroSalle()) && s.getBatiment().equals(seance.getBatiment())))
            throw new IllegalArgumentException(String.format("La salle %s du batiment %s est déjà occupée à cette date", seance.getNumeroSalle(), seance.getBatiment()));

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
                .build();

    }

    public getSeanceByIdDTOOutput getSeanceById(Integer id) {
        Seance seance = this.seanceRepository.findById(id).get();
        return getSeanceByIdDTOOutput.builder()
                .date(seance.getDate().toString())
                .duree(seance.getDuree())
                .batiment(seance.getBatiment())
                .idUtilisateurs(seance.getIdUtilisateurs())
                .nomFormateur(seance.getNomFormateur())
                .numeroSalle(seance.getNumeroSalle())
                .nomFormation(seance.getNomFormation())
                .build();
    }

    public putSeanceOutputDTO putSeanceById(Integer id, putSeanceInputDTO putSeanceInputDTO)
    {
        Seance seance = this.seanceRepository.findById(id).orElseThrow();

        seance.setDate(LocalDateTime.parse(putSeanceInputDTO.getDate()));
        seance.setDuree(putSeanceInputDTO.getDuree());
        seance.setBatiment(putSeanceInputDTO.getBatiment());
        seance.setNumeroSalle(putSeanceInputDTO.getNumeroSalle());

        this.seanceRepository.save(seance);

        return putSeanceOutputDTO.builder()
                .id(seance.getId())
                .date(seance.getDate().toString())
                .duree(seance.getDuree())
                .batiment(seance.getBatiment())
                .numeroSalle(seance.getNumeroSalle())
                .nomFormation(seance.getNomFormation())
                .nomFormateur(seance.getNomFormateur())
                .build();

    }

    public List<getAllSeancesDTOOutput> getAllSeances() {
        List<Seance> seances = this.seanceRepository.findAll();
        List<getAllSeancesDTOOutput> seancesDTO = new ArrayList<>();
        for (Seance seance : seances) {
            seancesDTO.add(getAllSeancesDTOOutput.builder()
                    .id(seance.getId())
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
        Seance s = this.seanceRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("La séance avec l'id " + id + " n'existe pas"));
        this.seanceRepository.delete(s);
        return "La séance avec l'id " + id + " a été supprimée";
    }

}
