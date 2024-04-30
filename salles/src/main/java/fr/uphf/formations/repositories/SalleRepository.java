package fr.uphf.formations.repositories;

import fr.uphf.formations.entities.Salles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalleRepository extends JpaRepository<Salles, Integer> {
    public Salles findByNumeroSalle(Integer numeroSalle);
    //@Query("SELECT s FROM SALLES WHERE NUMERO_SALLE = :numeroSalle and BATIMENT = 'test2'")
    public Salles findByNumeroSalleAndBatiment(Integer numeroSalle, String batiment);
}
