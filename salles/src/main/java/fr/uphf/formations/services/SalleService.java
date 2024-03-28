package fr.uphf.formations.services;

import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOInput;
import fr.uphf.formations.dto.creationSalleDTO.creationSalleDTOOutput;
import fr.uphf.formations.entities.Salles;
import fr.uphf.formations.repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
