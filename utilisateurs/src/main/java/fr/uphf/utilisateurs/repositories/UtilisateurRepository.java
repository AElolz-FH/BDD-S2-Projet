package fr.uphf.utilisateurs.repositories;

import fr.uphf.utilisateurs.ressources.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, String>{
    Utilisateur findById(Integer id);
}
