package dao;

import metier.Lieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface LieuRepository extends JpaRepository<Lieu, Long> {

    @Query(value = "SELECT * FROM lieu WHERE ville = :ville", nativeQuery = true)
    Lieu getLieuParVille(@Param("ville") String ville);

    @Query(value = "SELECT * FROM lieu WHERE pays = :pays", nativeQuery = true)
    Set<Lieu> getLieuParPays(@Param("pays") String pays);

}