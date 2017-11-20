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

    private String nomAbonnement;

    private float prixAbonnement;

    public Abonnement() {}

    public Abonnement(String nom, float prix) {
        this.nomAbonnement = nom;
        this.prixAbonnement = prix;
    }

    public String getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(String idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public String getNom() {
        return nomAbonnement;
    }

    public void setNom(String nom) {
        this.nomAbonnement = nom;
    }

    public float getPrixAbonnement() {
        return prixAbonnement;
    }

    public void setPrixAbonnement(float prix) {
        this.prixAbonnement = prix;
    }
}
