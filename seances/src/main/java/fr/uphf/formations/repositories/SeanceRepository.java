package fr.uphf.formations.repositories;

import fr.uphf.formations.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SeanceRepository extends JpaRepository<Seance, Integer> {
    public Seance findSeanceByDateAndAndBatimentAndNumeroSalleAndNomFormation(LocalDateTime date, String batiment, Integer numeroSalle, String nomFormation);
}
