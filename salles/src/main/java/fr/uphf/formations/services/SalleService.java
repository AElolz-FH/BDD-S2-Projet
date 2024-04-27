package fr.uphf.formations.services;

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
import fr.uphf.formations.exceptions.SalleNotFoundException;
import fr.uphf.formations.repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
            return creationSalleDTOOutput.builder().message("La salle existe déjà").build();
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
        Salles salle = this.salleRepository.findById(id).orElseThrow(() -> new RuntimeException("Salle not found"));
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

    public modifierSalleDTOOutput modifierSalle(modifierSalleDTOInput salleDTO) {
        Salles salle = this.salleRepository.findByNumeroSalle(salleDTO.getNumeroSalle());
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


    public modifierSalleDispoDTOOutput modifierDispoSalle(modiferSalleDispoDTOInput modifierSalleDTOInput) {
        Salles salle = this.salleRepository.findByNumeroSalle(modifierSalleDTOInput.getNumeroSalle());

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

    public getSalleByNumAndBatDTOOutput getSalleByNumeroAndBat(Integer numeroSalle,String batiment){
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

    public String deleteSalle(Integer numeroSalle, String batiment) {
        Salles salle = this.salleRepository.findByNumeroSalleAndBatiment(numeroSalle,batiment);
        if(salle == null){
            return "La salle n'a pas été trouvée";
        }
        this.salleRepository.delete(salle);
        return "La salle : " + salle.getNomSalle() + " a été supprimée";
    }

}
