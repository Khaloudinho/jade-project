package metier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Avion implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idAvion;
    private String immatriculation;
    private int capaciteSoute;
    private int autonomie;
    private int consomationCarburant;
    private int capaciteLibre;

    public String getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(String idAvion) {
        this.idAvion = idAvion;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public int getCapaciteSoute() {
        return capaciteSoute;
    }

    public void setCapaciteSoute(int capaciteSoute) {
        this.capaciteSoute = capaciteSoute;
    }

    public int getAutonomie() {
        return autonomie;
    }

    public void setAutonomie(int autonomie) {
        this.autonomie = autonomie;
    }

    public int getConsomationCarburant() {
        return consomationCarburant;
    }

    public void setConsomationCarburant(int consomationCarburant) {
        this.consomationCarburant = consomationCarburant;
    }

    public int getCapaciteLibre() {
        return capaciteLibre;
    }

    public void setCapaciteLibre(int capaciteLibre) {
        this.capaciteLibre = capaciteLibre;
    }
}
