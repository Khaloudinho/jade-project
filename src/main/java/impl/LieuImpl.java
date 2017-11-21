package impl;

import dao.LieuRepository;
import metier.Lieu;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class LieuImpl {

    @Autowired
    private LieuRepository lieuRepository;

    public Lieu getLieuParVille(String ville){
        return lieuRepository.getLieuParVille(ville);
    }

    public Set<Lieu> getLieuParPays(String pays){
        return lieuRepository.getLieuParPays(pays);
    }
}
