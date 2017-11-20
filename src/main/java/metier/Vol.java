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
    private String idBaseTarif;
    private String idAvion;
    private String idLieuArrivee;

    public Vol() {
    }

    public Vol(Date dateDepart, Date dateArrivee, TypeVol typeVol, String idBaseTarif, String idAvion, String idLieuArrivee) {
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.typeVol = typeVol;
        this.idBaseTarif = idBaseTarif;
        this.idAvion = idAvion;
        this.idLieuArrivee = idLieuArrivee;
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

    public String getIdBaseTarif() {
        return idBaseTarif;
    }

    public void setIdBaseTarif(String idBaseTarif) {
        this.idBaseTarif = idBaseTarif;
    }

    public String getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(String idAvion) {
        this.idAvion = idAvion;
    }

    public String getIdLieuArrivee() {
        return idLieuArrivee;
    }

    public void setIdLieuArrivee(String idLieuArrivee) {
        this.idLieuArrivee = idLieuArrivee;
    }
}
