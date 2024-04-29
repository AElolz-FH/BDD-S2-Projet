package fr.uphf.formations.repository;


import fr.uphf.formations.entities.Formations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationRepository extends JpaRepository<Formations,String> {
    public Optional<Formations> findById(String id);
    List<Formations> findByFormateur_IdUtilisateur(Integer formateurId);

    public List<Formations> findAll();

    Formations findByLibelle(String nomFormation);
}
