package metier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class BaseTarif implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idBaseTarif;

    private float prixReference;

    public BaseTarif() {}

    public BaseTarif(float prixReference) {
        this.prixReference = prixReference;
    }

    public String getIdBaseTarif() {
        return idBaseTarif;
    }

    public void setIdBaseTarif(String idBaseTarif) {
        this.idBaseTarif = idBaseTarif;
    }

    public float getPrixReference() {
        return prixReference;
    }

    public void setPrixReference(float prixReference) {
        this.prixReference = prixReference;
    }
}
