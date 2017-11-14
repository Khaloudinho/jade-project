package metier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Abonne implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idAbonnee;
    private int numeroSiret;
    private String idAbonnement;

    public String getIdAbonnee() {
        return idAbonnee;
    }

    public void setIdAbonnee(String idAbonnee) {
        this.idAbonnee = idAbonnee;
    }

    public int getNumeroSiret() {
        return numeroSiret;
    }

    public void setNumeroSiret(int numeroSiret) {
        this.numeroSiret = numeroSiret;
    }

    public String getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(String idAbonnement) {
        this.idAbonnement = idAbonnement;
    }
}
