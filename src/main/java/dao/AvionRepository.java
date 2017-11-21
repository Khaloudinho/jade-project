package dao;

import metier.Avion;
import metier.Lieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AvionRepository extends JpaRepository<Lieu, Long> {

    @Query(value = "SELECT * FROM avion WHERE immatriculation = :immatriculation", nativeQuery = true)
    Avion getAvionParImmatriculation(@Param("immatriculation") String immatriculation);

}