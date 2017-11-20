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
    private int consommationCarburant;
    private int capaciteLibre;

    public Avion() {}

    public Avion(String immatriculation, int capaciteSoute, int autonomie, int consommationCarburant, int capaciteLibre) {
        this.immatriculation = immatriculation;
        this.capaciteSoute = capaciteSoute;
        this.autonomie = autonomie;
        this.consommationCarburant = consommationCarburant;
        this.capaciteLibre = capaciteLibre;
    }

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

    public int getConsommationCarburant() {
        return consommationCarburant;
    }

    public void setConsommationCarburant(int consomationCarburant) {
        this.consommationCarburant = consomationCarburant;
    }

    public int getCapaciteLibre() {
        return capaciteLibre;
    }

    public void setCapaciteLibre(int capaciteLibre) {
        this.capaciteLibre = capaciteLibre;
    }
}
