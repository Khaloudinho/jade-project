package messages.association;

import java.sql.Date;

public class VolAssociation {

    private String idVol;

    private String aeroport;

    private String pays;

    private Date dateArrivee;

    private int capaciteLibre;

    private int prix;

    public VolAssociation(String idVol, String aeroport, String pays, Date dateArrivee, int capaciteLibre, int prix) {
        this.idVol = idVol;
        this.aeroport = aeroport;
        this.pays = pays;
        this.dateArrivee = dateArrivee;
        this.capaciteLibre = capaciteLibre;
        this.prix = prix;
    }

    public String getIdVol() {
        return idVol;
    }

    public void setIdVol(String idVol) {
        this.idVol = idVol;
    }

    public String getAeroport() {
        return aeroport;
    }

    public void setAeroport(String aeroport) {
        this.aeroport = aeroport;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public int getCapaciteLibre() {
        return capaciteLibre;
    }

    public void setCapaciteLibre(int capaciteLibre) {
        this.capaciteLibre = capaciteLibre;
    }

    public long getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
