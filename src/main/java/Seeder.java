import metier.Vol;
import util.TypeVol;
import metier.Abonnement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class Seeder {
    public static void main(String [] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jadeprojectPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Set<Vol> vols = new HashSet<Vol>();
        Date dateDepart = new Date (2017, 01, 01);
        Date dateDepartVolsRegulier = new Date (2017, 01, 01);

        Date dateArrivee = new Date (2017, 01, 01);
        Date dateArriveeVolsRegulier = new Date (2017, 01, 01);

        Vol v1 = new Vol(dateDepart, dateArrivee, TypeVol.charters, "1", "AFX-508-RF", "1");
        Vol v2 = new Vol(dateDepart, dateArrivee, TypeVol.charters, "1", "AFX-508-RF", "1");

        Vol v3 = new Vol(dateDepartVolsRegulier, dateArrivee, TypeVol.regulier, "2", "AFF-508-RF", "1");
        Vol v4 = new Vol(dateDepartVolsRegulier, dateArriveeVolsRegulier, TypeVol.regulier, "2", "AFF-508-RF", "2");

        vols.add(v1);
        vols.add(v2);
        vols.add(v3);
        vols.add(v4);

        for (Vol vol:
             vols) {
            em.persist(vol);
        }

        Abonnement ab = new Abonnement("Abonnement 1", 15095.82f);
        Abonnement ab2 = new Abonnement("Abonnement 2", 18795.82f);
        Abonnement ab3 = new Abonnement("Abonnement 3", 19595.82f);

        em.persist(ab);
        em.persist(ab2);
        em.persist(ab3);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
