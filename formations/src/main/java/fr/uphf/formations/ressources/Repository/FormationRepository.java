package fr.uphf.formations.ressources.Repository;


import fr.uphf.formations.ressources.Entity.Formations;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FormationRepository {
    public void findById(int id);

    Formations save(Formations formation);

    public List<Formations> findAll();
}
