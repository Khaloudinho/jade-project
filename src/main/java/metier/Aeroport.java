package metier;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class Aeroport implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idAeroport;
    private String nomAeroport;
    private int taxeAeroport;
    private int heuresVolDepuisParis;

    @OneToOne
    private Lieu lieu;

    public Aeroport() {
    }

    public Aeroport(String nomAeroport, int taxeAeroport, int heuresVolDepuisParis, Lieu lieu) {
        this.nomAeroport = nomAeroport;
        this.taxeAeroport = taxeAeroport;
        this.heuresVolDepuisParis = heuresVolDepuisParis;
        this.lieu = lieu;
    }

    public String getIdAeroport() {
        return idAeroport;
    }

    public void setIdAeroport(String idAeroport) {
        this.idAeroport = idAeroport;
    }

    public String getNomAeroport() {
        return nomAeroport;
    }

    public void setNomAeroport(String nomAeroport) {
        this.nomAeroport = nomAeroport;
    }

    public int getTaxeAeroport() {
        return taxeAeroport;
    }

    public void setTaxeAeroport(int taxeAeroport) {
        this.taxeAeroport = taxeAeroport;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public int getHeuresVolDepuisParis() {
        return heuresVolDepuisParis;
    }

    public void setHeuresVolDepuisParis(int heuresVolDepuisParis) {
        this.heuresVolDepuisParis = heuresVolDepuisParis;
    }
}
