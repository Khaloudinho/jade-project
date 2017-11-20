import metier.Abonne;
import metier.Aeroport;
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

        Set<Vol> vols = new HashSet<>();
        Date dateDepart = new Date(2017, 01, 01);
        Date dateDepartVolsRegulier = new Date(2017, 01, 01);

        Aeroport a1 = new Aeroport();
        a1.setNomAeroport("Aéroport international de Conakry");
        a1.setTaxeAeroport((int)(Math.random() * 1000 + 150));

        Aeroport a2 = new Aeroport();
        a2.setNomAeroport("Aéroport international Léopold-Sédar-Senghor");
        a2.setTaxeAeroport((int)(Math.random() * 1000 + 150));

        Aeroport a3 = new Aeroport();
        a3.setNomAeroport("Aéroport international de Banjul - Yundum");
        a3.setTaxeAeroport((int)(Math.random() * 1000 + 150));

        Aeroport a4 = new Aeroport();
        a4.setNomAeroport("Aéroport international Félix-Houphouët-Boigny ");
        a4.setTaxeAeroport((int)(Math.random() * 1000 + 150));

        Aeroport a5 = new Aeroport();
        a5.setNomAeroport("Aéroport international de Douala");
        a5.setTaxeAeroport((int)(Math.random() * 1000 + 150));

        Aeroport a6 = new Aeroport();
        a6.setNomAeroport("Aéroport international de Gaborone");
        a6.setTaxeAeroport((int)(Math.random() * 1000 + 150));


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

        for (Vol vol : vols) {
            em.persist(vol);
        }

        Set<Abonnement> abonnements = new HashSet<>();
        Abonnement ab = new Abonnement("Abonnement 1", 15095.82f);
        Abonnement ab2 = new Abonnement("Abonnement 2", 18795.82f);
        Abonnement ab3 = new Abonnement("Abonnement 3", 19595.82f);

        abonnements.add(ab);
        abonnements.add(ab2);
        abonnements.add(ab3);

        for (Abonnement abo : abonnements) {
            em.persist(abo);
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
