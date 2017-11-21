package impl;

import dao.AvionRepository;
import metier.Avion;
import org.springframework.beans.factory.annotation.Autowired;

public class AvionImpl {

    @Autowired
    private AvionRepository avionRepository;

    public Avion getAvionParImmatriculation(String immatriculation){
        return avionRepository.getAvionParImmatriculation(immatriculation);
    }
}
