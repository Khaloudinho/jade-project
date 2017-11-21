package metier;

import org.hibernate.annotations.GenericGenerator;
import util.TypeVol;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class BaseTarif implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idBaseTarif;

    @Enumerated(EnumType.STRING)
    private TypeVol typeBaseTarif;

    private float prixReference;

    public BaseTarif() {}

    public BaseTarif(TypeVol type, float prix) {
        this.typeBaseTarif = type;
        this.prixReference = prix;
    }

    public String getIdBaseTarif() {
        return idBaseTarif;
    }

    public void setIdBaseTarif(String idBaseTarif) {
        this.idBaseTarif = idBaseTarif;
    }

    public TypeVol getTypeBaseTarif() {
        return typeBaseTarif;
    }

    public void setTypeBaseTarif(TypeVol typeBaseTarif) {
        this.typeBaseTarif = typeBaseTarif;
    }

    public float getPrixReference() {
        return prixReference;
    }

    public void setPrixReference(float prixReference) {
        this.prixReference = prixReference;
    }
}
