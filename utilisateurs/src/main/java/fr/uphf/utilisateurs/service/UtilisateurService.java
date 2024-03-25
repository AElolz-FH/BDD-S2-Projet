package fr.uphf.utilisateurs.service;

import fr.uphf.utilisateurs.dto.create.CreateUtilisateurInputDTO;
import fr.uphf.utilisateurs.dto.create.CreateUtilisateurResponseDTO;
import fr.uphf.utilisateurs.dto.getall.getUtilisateursInputDTO;
import fr.uphf.utilisateurs.dto.getall.getUtilisateursResponseDTO;
import fr.uphf.utilisateurs.repositories.UtilisateurRepository;
import fr.uphf.utilisateurs.ressources.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
