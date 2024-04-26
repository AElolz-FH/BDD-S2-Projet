package fr.uphf.formations.repository;

import fr.uphf.formations.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeanceRepository extends JpaRepository<Seance,String> {

}
