package metier;

import org.hibernate.annotations.GenericGenerator;
import util.TypeVol;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@NamedQuery(
                // Trouver les vols pour un pays donné à une date donnée
                name = "Vol.getVolsCorrespondantsALaDemande",
                query = "SELECT v.id " +
                        "FROM Vol v " +
                        "WHERE v.dateDepart = :date " +
                        "AND v.aeroportArrivee.lieu.pays = :pays " +
                        "AND v.avion.capaciteLibre >= :capaciteLibre "
)

public class Vol implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idVol;
    private Date dateDepart;
    private Date dateArrivee;
    private double prixVol;

    @Enumerated(EnumType.STRING)
    private TypeVol typeVol;

    @OneToOne
    private BaseTarif baseTarif;

    @OneToOne
    private Avion avion;

    @OneToOne
    private Aeroport aeroportArrivee;

    public Vol() {
    }

    public Vol(Date dateDepart, Date dateArrivee, TypeVol typeVol, BaseTarif baseTarif, Avion avion, Aeroport aeroportArrivee) {
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.typeVol = typeVol;
        this.baseTarif = baseTarif;
        this.avion = avion;
        this.aeroportArrivee = aeroportArrivee;
        this.prixVol = 0;
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

    public Aeroport getAeroportArrivee() {
        return aeroportArrivee;
    }

    public void setAeroportArrivee(Aeroport aeroportArrivee) {
        this.aeroportArrivee = aeroportArrivee;
    }

    public double getPrixVol() {
        return prixVol;
    }

    public void setPrixVol(double prixVol) {
        this.prixVol = prixVol;
    }
}
