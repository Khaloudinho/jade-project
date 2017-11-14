package metier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Abonnement implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idAbonnement;

    private float prixAbonnement;

    public Abonnement() {}

    public Abonnement(float prixAbonnement) {
        this.prixAbonnement = prixAbonnement;
    }

    public String getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(String idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public float getPrixAbonnement() {
        return prixAbonnement;
    }

    public void setPrixAbonnement(float prixAbonnement) {
        this.prixAbonnement = prixAbonnement;
    }
}
