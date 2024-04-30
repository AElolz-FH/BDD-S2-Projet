package fr.uphf.formations.repositories;

import fr.uphf.formations.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Integer> {
    List<Seance> findByFormationId(Integer formationId);

    List<Seance> findBySalles_NumeroSalleAndSalles_Batiment(Integer numeroSalle, String batiment);
    //public Seance findSeanceByDateAndAndBatimentAndNumeroSalleAndNomFormation(LocalDateTime date, String batiment, Integer numeroSalle, String nomFormation);
}
