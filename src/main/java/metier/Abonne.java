package metier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class Abonne implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idAbonnee;
    private int numeroSiret;

    @OneToOne
    private Abonnement abonnement;

    public Abonne() {}

    public Abonne(int numeroSiret, Abonnement abonnement) {
        this.numeroSiret = numeroSiret;
        this.abonnement = abonnement;
    }

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

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }
}
