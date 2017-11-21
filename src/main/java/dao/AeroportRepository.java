package dao;

import metier.Aeroport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AeroportRepository extends JpaRepository<Aeroport, Long> {

    @Query(value = "SELECT * FROM aeroport a, lieu l WHERE a.lieu_idLieu = l.idLieu AND l.ville = :ville", nativeQuery = true)
    Aeroport getAeroportParVille(@Param("ville") String ville);

    @Query(value = "SELECT * FROM aeroport a, lieu l WHERE a.lieu_idLieu = l.idLieu AND l.pays = :pays", nativeQuery = true)
    Set<Aeroport> getAeroportParPays(@Param("pays") String pays);

}