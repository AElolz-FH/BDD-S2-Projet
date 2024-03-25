package fr.uphf.utilisateurs.service;

import fr.uphf.utilisateurs.repositories.UtilisateurRepository;
import fr.uphf.utilisateurs.ressources.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur getUtilisateur(String idUtilisateur){
        return this.utilisateurRepository.findById(idUtilisateur).orElseThrow();
    }
}
