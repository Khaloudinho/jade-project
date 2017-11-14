package metier;

import org.hibernate.annotations.GenericGenerator;
import util.EtatReservation;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idReservation;

    private Date dateReservation;

    private Date dateLimite;

    @Enumerated(EnumType.STRING)
    private EtatReservation etatReservation;

    public Reservation() {}

    public Reservation(Date dateReservation, Date dateLimite, EtatReservation etatReservation) {
        this.dateReservation = dateReservation;
        this.dateLimite = dateLimite;
        this.etatReservation = etatReservation;
    }

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Date getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(Date dateLimite) {
        this.dateLimite = dateLimite;
    }

    public EtatReservation getEtatReservation() {
        return etatReservation;
    }

    public void setEtatReservation(EtatReservation etatReservation) {
        this.etatReservation = etatReservation;
    }
}
