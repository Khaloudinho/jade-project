package metier;

import org.hibernate.annotations.GenericGenerator;
import util.TypeVol;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Vol.getVolsCorrespondantsALaDemande",
                query = "SELECT v.aeroportArrivee.nomAeroport, v.aeroportArrivee.lieu.pays, v.dateArrivee, v.avion.capaciteLibre, v.prixVol, v.idVol " +
                        "FROM Vol v " +
                        "WHERE v.dateDepart = :date " +
                        "AND v.aeroportArrivee.lieu.pays = :pays " +
                        "AND v.avion.capaciteLibre >= :capaciteLibre " +
                        "AND v.typeVol = :typeVol "),

        @NamedQuery(
                name = "Vol.calculerLesPrixDesVols",
                query = "SELECT v.id, v.avion.consommationCarburant, v.aeroportArrivee.heuresVolDepuisParis, v.aeroportArrivee.taxeAeroport, v.aeroportArrivee.lieu.ville FROM Vol v ")
})

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
    private Avion avion;

    @OneToOne
    private Aeroport aeroportArrivee;

    public Vol() {
    }

    public Vol(Date dateDepart, Date dateArrivee, TypeVol typeVol, Avion avion, Aeroport aeroportArrivee) {
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.typeVol = typeVol;
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
