package impl;

import dao.AeroportRepository;
import metier.Aeroport;
import metier.Lieu;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class AeroportImpl {

    @Autowired
    private AeroportRepository aeroportRepository;

    public Aeroport getLieuParVille(String ville){
        return aeroportRepository.getAeroportParVille(ville);
    }

    public Set<Aeroport> getLieuParPays(String pays){
        return aeroportRepository.getAeroportParPays(pays);
    }
}
