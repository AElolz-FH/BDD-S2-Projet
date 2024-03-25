package fr.uphf.formations.ressources.Service;
import fr.uphf.formations.ressources.DTO.FormationDTO;
import fr.uphf.formations.ressources.DTO.FormationDTOResponse;
import fr.uphf.formations.ressources.Entity.Formations;
import fr.uphf.formations.ressources.Repository.FormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationService {
    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<FormationDTO> getAllFormations() {
        return formationRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public FormationDTOResponse createFormation(FormationDTO formationDTO) {
        Formations formation = Formations.builder()
                .libelle(formationDTO.getLibelle())
                .description(formationDTO.getDescription())
                .build();

        Formations savedFormation = formationRepository.save(formation);

        return mapEntityToDTOResponse(savedFormation);
    }

    private FormationDTO mapEntityToDTO(Formations formationEntity) {
        return FormationDTO.builder()
                .idFormation(formationEntity.getId())
                .libelle(formationEntity.getLibelle())
                .description(formationEntity.getDescription())
                .build();
    }

    private FormationDTOResponse mapEntityToDTOResponse(Formations formationEntity) {
        return FormationDTOResponse.builder()
                .idFormation(formationEntity.getId())
                .libelle(formationEntity.getLibelle())
                .description(formationEntity.getDescription())
                .build();
    }
}
