package dao;

import metier.BaseTarif;
import metier.Lieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BaseTarifRepository extends JpaRepository<Lieu, Long> {

    @Query(value = "SELECT * FROM basetarif WHERE typeBaseTarif = :nom", nativeQuery = true)
    BaseTarif getBaseTarifParNom(@Param("nom") String nom);

}