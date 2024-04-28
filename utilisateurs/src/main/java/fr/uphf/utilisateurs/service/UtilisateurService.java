package fr.uphf.utilisateurs.service;

import fr.uphf.utilisateurs.dto.create.CreateUtilisateurInputDTO;
import fr.uphf.utilisateurs.dto.create.CreateUtilisateurResponseDTO;
import fr.uphf.utilisateurs.dto.getall.getUtilisateursResponseDTO;
import fr.uphf.utilisateurs.dto.putUtilsateurDTO.putUtilisateurDTOInput;
import fr.uphf.utilisateurs.dto.putUtilsateurDTO.putUtilisateurDTOOutput;
import fr.uphf.utilisateurs.repositories.UtilisateurRepository;
import fr.uphf.utilisateurs.ressources.Utilisateur;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public String deleteUser(String nom, String prenom) {
        Utilisateur user = this.utilisateurRepository.findByNomAndPrenom(nom, prenom);
        if (user == null) {
            return "Aucun utilisateur trouvé";
        }
        this.utilisateurRepository.delete(user);
        // Envoyer un message à RabbitMQ avec l'ID de l'utilisateur supprimé
        rabbitTemplate.convertAndSend("userExchange", "user.deleted", user.getId());
        return "Utilisateur supprimé";
    }
    public List<getUtilisateursResponseDTO> getUtilisateur(){
        List<Utilisateur> users = this.utilisateurRepository.findAll();
        if(users.isEmpty()){
            return (List<getUtilisateursResponseDTO>) new ArrayList<getUtilisateursResponseDTO>().get(0).builder().message("Aucun utilisateur trouvé").build();
        }
        List<getUtilisateursResponseDTO> listReponse = new ArrayList<>();
        for (Utilisateur user : users) {
            listReponse.add(getUtilisateursResponseDTO.builder()
                    .id(user.getId())
                    .nom(user.getNom())
                    .prenom(user.getPrenom())
                    .email(user.getEmail())
                    .Formateur(user.isFormateur())
                            .message("Utilisateurs trouvés")
                    .build());
        }
        return listReponse;
    }


    public getUtilisateursResponseDTO getOneUserById(Integer id) {
        Utilisateur user = this.utilisateurRepository.findById(id);
        if(user == null){
            return getUtilisateursResponseDTO.builder().message("Aucun utilisateur trouvé").build();
        }
        return getUtilisateursResponseDTO.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .Formateur(user.isFormateur())
                .message("Utilisateur trouvé")
                .build();
    }

    public CreateUtilisateurResponseDTO createUtilisateur(CreateUtilisateurInputDTO createUtilisateurInputDTO) {
        List<Utilisateur> users = this.utilisateurRepository.findAll();
        Utilisateur user = Utilisateur.builder()
                .nom(createUtilisateurInputDTO.getNom())
                .prenom(createUtilisateurInputDTO.getPrenom())
                .email(createUtilisateurInputDTO.getEmail())
                .build();
        if(users.stream().anyMatch(utilisateur -> utilisateur.getNom().equals(user.getNom()) && utilisateur.getPrenom().equals(user.getPrenom()))){
            return CreateUtilisateurResponseDTO.builder().message("Utilisateur déjà existant").build();
        }
        if(users.stream().anyMatch(utilisateur -> utilisateur.getEmail().equals(user.getEmail()))){
            return CreateUtilisateurResponseDTO.builder().message("Email déjà existant").build();
        }
        this.utilisateurRepository.save(user);
        return CreateUtilisateurResponseDTO.builder()
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .message("Utilisateur créé")
                .build();
    }

    @Transactional
    public putUtilisateurDTOOutput modifyUtilisateur(Integer id, putUtilisateurDTOInput putUtilisateurDTOInput) {
        Utilisateur user = this.utilisateurRepository.findById(putUtilisateurDTOInput.getId());
        if(user == null){
            return putUtilisateurDTOOutput.builder().message("Aucun utilisateur trouvé").build();
        }
        user.setFormateur(putUtilisateurDTOInput.isFormateur());
        this.utilisateurRepository.save(user);
        return putUtilisateurDTOOutput.builder()
                .id(user.getId())
                .Formateur(user.isFormateur())
                .message("Utilisateur modifié")
                .build();
    }


}
