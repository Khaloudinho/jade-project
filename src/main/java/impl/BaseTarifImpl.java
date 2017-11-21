package impl;

import dao.BaseTarifRepository;
import metier.BaseTarif;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTarifImpl {

    @Autowired
    private BaseTarifRepository baseTarifRepository;

    public BaseTarif getBaseTarifParNom(String nom){
        return baseTarifRepository.getBaseTarifParNom(nom);
    }
}
