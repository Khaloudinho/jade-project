package metier;

import org.hibernate.annotations.GenericGenerator;
import util.TypeVol;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Vol implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idVol;
    private Date dateDepart;
    private Date dateArrivee;

    @Enumerated(EnumType.STRING)
    private TypeVol typeVol;

    @OneToOne
    private BaseTarif baseTarif;

    @OneToOne
    private Avion avion;

    @OneToOne
    private Lieu lieuArrivee;

    public Vol() {
    }

public Vol(Date dateDepart, Date dateArrivee, TypeVol typeVol, BaseTarif baseTarif, Avion avion, Lieu lieuArrivee) {
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.typeVol = typeVol;
        this.baseTarif = baseTarif;
        this.avion = avion;
        this.lieuArrivee = lieuArrivee;
    }

    public String getIdVol() {
        return idVol;
    }

    public void setIdVol(String idVol) {
        this.idVol = idVol;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public TypeVol getTypeVol() {
        return typeVol;
    }

    public void setTypeVol(TypeVol typeVol) {
        this.typeVol = typeVol;
    }

    public BaseTarif getBaseTarif() {
        return baseTarif;
    }

    public void setBaseTarif(BaseTarif baseTarif) {
        this.baseTarif = baseTarif;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Lieu getLieuArrivee() {
        return lieuArrivee;
    }

    public void setLieuArrivee(Lieu lieuArrivee) {
        this.lieuArrivee = lieuArrivee;
    }
}
