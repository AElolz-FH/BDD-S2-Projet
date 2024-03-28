package fr.uphf.formations.services;

import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOInput;
import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOOutput;
import fr.uphf.formations.dto.getAllSallesDTO.getAllSallesDTOOutput;
import fr.uphf.formations.dto.getSalleDTOid.getSalleDTOidOutput;
import fr.uphf.formations.entities.Salles;
import fr.uphf.formations.exceptions.SalleNotFoundException;
import fr.uphf.formations.repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public creationSalleDTOOutput createSalle(creationSalleDTOInput salleDTO) {

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
}
