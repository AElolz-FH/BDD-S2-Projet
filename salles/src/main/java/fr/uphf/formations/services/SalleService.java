package fr.uphf.formations.services;

import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOInput;
import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOOutput;
import fr.uphf.formations.dto.getAllSallesDTO.getAllSallesDTOOutput;
import fr.uphf.formations.dto.getSalleDTOid.getSalleDTOidOutput;
import fr.uphf.formations.dto.modifierSalleDTO.modifierSalleDTOInput;
import fr.uphf.formations.dto.modifierSalleDTO.modifierSalleDTOOutput;
import fr.uphf.formations.dto.modifierSalleDispoDTO.modiferSalleDispoDTOInput;
import fr.uphf.formations.dto.modifierSalleDispoDTO.modifierSalleDispoDTOOutput;
import fr.uphf.formations.entities.Salles;
import fr.uphf.formations.exceptions.SalleNotFoundException;
import fr.uphf.formations.repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public creationSalleDTOOutput createSalle(creationSalleDTOInput salleDTO) {
        List<Salles> salles = this.salleRepository.findAll();
        if(salles.stream().anyMatch(salle -> salle.getNumeroSalle().equals(salleDTO.getNumeroSalle()))){
            System.out.println("Salle already exists");
            throw new RuntimeException("Salle already exists");
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
                .build();
    }

    public getSalleDTOidOutput getSalleById(Integer id) throws SalleNotFoundException {
        //si la salle n'est pas dans la base alors throw une exception salle non trouvée
        Salles salle = this.salleRepository.findById(id).orElseThrow(() -> new RuntimeException("Salles not found"));
        //sinon on retourne un dto en prenant les attributs de la salle
        return getSalleDTOidOutput.builder()
                .id(salle.getId())
                .numeroSalle(salle.getNumeroSalle())
                .capacite(salle.getCapacite())
                .batiment(salle.getBatiment())
                .isDisponible(salle.isDisponible())
                .build();

    }
    public getAllSallesDTOOutput getAllSalles() {
        return getAllSallesDTOOutput.builder()
                .salles(salleRepository.findAll().stream().map(salle -> getAllSallesDTOOutput.getSallesDTOOutput.builder()
                        .id(salle.getId())
                        .numeroSalle(salle.getNumeroSalle())
                        .capacite(salle.getCapacite())
                        .nomSalle(salle.getNomSalle())
                        .batiment(salle.getBatiment())
                        .isDisponible(salle.isDisponible())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    public modifierSalleDTOOutput modifierSalle(modifierSalleDTOInput salleDTO) {
        Salles salle = this.salleRepository.findByNumeroSalle(salleDTO.getNumeroSalle());
        if (salle == null) {
            throw new RuntimeException("Salle not found");
        }

        Salles temp = salle;

        Salles salleModifiee = Salles.builder()
                .numeroSalle(salleDTO.getNumeroSalle())
                .nomSalle(salleDTO.getNomSalle())
                .capacite(salleDTO.getCapacite())
                .batiment(salleDTO.getBatiment())
                .build();

        this.salleRepository.delete(salle);

        salleModifiee.setId(temp.getId());
        salleModifiee.setCapacite(salleDTO.getCapacite());
        salleModifiee.setNomSalle(salleDTO.getNomSalle());
        salleModifiee.setNumeroSalle(salleDTO.getNumeroSalle());
        salleModifiee.setBatiment(salleDTO.getBatiment());
        salleModifiee.setDisponible(temp.isDisponible());


        this.salleRepository.save(salleModifiee);

        return modifierSalleDTOOutput.builder()
                .numeroSalle(salleModifiee.getNumeroSalle())
                .nomSalle(salleModifiee.getNomSalle())
                .capacite(salleModifiee.getCapacite())
                .batiment(salleDTO.getBatiment())
                .build();
    }


    public modifierSalleDispoDTOOutput modifierDispoSalle(modiferSalleDispoDTOInput modifierSalleDTOInput) {
        Salles salle = this.salleRepository.findByNumeroSalle(modifierSalleDTOInput.getNumeroSalle());

        if (salle == null) {
            throw new RuntimeException("Salle not found");
        }

        salle.setDisponible(modifierSalleDTOInput.isDisponible());
        this.salleRepository.save(salle);

        return modifierSalleDispoDTOOutput.builder()
                .numeroSalle(salle.getNumeroSalle())
                .isDisponible(salle.isDisponible())
                .build();
    }

}
