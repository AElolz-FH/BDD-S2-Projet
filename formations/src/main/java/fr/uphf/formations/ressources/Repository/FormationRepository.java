package fr.uphf.formations.ressources.Repository;


import fr.uphf.formations.ressources.Entity.Formations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationRepository extends JpaRepository<Formations,String> {
    public Optional<Formations> findById(String id);

    public List<Formations> findAll();
}
