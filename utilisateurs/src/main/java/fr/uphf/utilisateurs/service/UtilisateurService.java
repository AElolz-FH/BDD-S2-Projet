package fr.uphf.utilisateurs.service;

import fr.uphf.utilisateurs.dto.create.CreateUtilisateurInputDTO;
import fr.uphf.utilisateurs.dto.create.CreateUtilisateurResponseDTO;
import fr.uphf.utilisateurs.dto.getall.getUtilisateursResponseDTO;
import fr.uphf.utilisateurs.dto.putUtilsateurDTO.putUtilisateurDTOInput;
import fr.uphf.utilisateurs.dto.putUtilsateurDTO.putUtilisateurDTOOutput;
import fr.uphf.utilisateurs.repositories.UtilisateurRepository;
import fr.uphf.utilisateurs.ressources.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    public List<getUtilisateursResponseDTO> getUtilisateur(){
        List<Utilisateur> users = this.utilisateurRepository.findAll();
        if(users.isEmpty()){
            throw new RuntimeException("Aucun utilisateur trouvé");
        }
        List<getUtilisateursResponseDTO> listReponse = new ArrayList<>();
        for (Utilisateur user : users) {
            listReponse.add(getUtilisateursResponseDTO.builder()
                    .nom(user.getNom())
                    .prenom(user.getPrenom())
                    .email(user.getEmail())
                    .build());
        }
        return listReponse;
    }

    public getUtilisateursResponseDTO getOneUserById(Integer id) {
        Utilisateur user = this.utilisateurRepository.findById(id);
        if(user == null){
            throw new RuntimeException("Aucun utilisateur trouvé");
        }
        return getUtilisateursResponseDTO.builder()
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .build();
    }

    public CreateUtilisateurResponseDTO createUtilisateur(CreateUtilisateurInputDTO createUtilisateurInputDTO) {
        Utilisateur user = Utilisateur.builder()
                .nom(createUtilisateurInputDTO.getNom())
                .prenom(createUtilisateurInputDTO.getPrenom())
                .email(createUtilisateurInputDTO.getEmail())
                .build();
        this.utilisateurRepository.save(user);
        return CreateUtilisateurResponseDTO.builder()
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .build();
    }

    @Transactional
    public putUtilisateurDTOOutput modifyUtilisateur(Integer id, putUtilisateurDTOInput putUtilisateurDTOInput) {
        Utilisateur user = this.utilisateurRepository.findById(putUtilisateurDTOInput.getId());
        if(user == null){ throw new RuntimeException("Aucun utilisateur trouvé"); }
        user.setFormateur(putUtilisateurDTOInput.isFormateur());
        this.utilisateurRepository.save(user);
        return putUtilisateurDTOOutput.builder()
                .id(user.getId())
                .Formateur(user.isFormateur())
                .build();
    }
}
