import metier.*;
import util.TypeVol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Seeder {
    public static void main(String [] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jadeprojectPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Set<Lieu> lieux = new HashSet<>();
        Lieu l1 = new Lieu("Conakry", "Guinee");
        Lieu l2 = new Lieu("Dakar", "Senegal");
        Lieu l3 = new Lieu("Banjul", "Gambie");
        Lieu l4 = new Lieu("Abidjan", "Cote d'ivoire");
        Lieu l5 = new Lieu("Douala", "Cameroun");
        Lieu l6 = new Lieu("Gaborone", "Bostwana");

        lieux.add(l1);
        lieux.add(l2);
        lieux.add(l3);
        lieux.add(l4);
        lieux.add(l5);
        lieux.add(l6);

        for (Lieu lieu : lieux) {
            em.persist(lieu);
        }

        Set<Avion> avions = new HashSet<>();
        Avion av1 = new Avion("AF9-151-PKL", 40, 1500, 3);
        Avion av2 = new Avion("GF6-051-PPP", 40, 1500, 3);
        Avion av3 = new Avion("FR4-401-ZKK", 40, 1500, 3);
        Avion av4 = new Avion("GB2-398-WYR", 40, 1500, 3);

        avions.add(av1);
        avions.add(av2);
        avions.add(av3);
        avions.add(av4);

        for (Avion avion : avions){
            em.persist(avion);
        }

        Date dateDepart = Date.valueOf("2017-01-01");
        Date dateDepartVolsReguliers = Date.valueOf("2017-03-11");
        Date dateArrivee = Date.valueOf("2017-05-16");
        Date dateArriveeVolsReguliers = Date.valueOf("2017-06-17");

        Set<Aeroport> aeroports = new HashSet<>();
        Aeroport a1 = new Aeroport();
        a1.setNomAeroport("Aéroport international de Conakry"); // Guinee
        a1.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a1.setLieu(l1);

        Aeroport a2 = new Aeroport();
        a2.setNomAeroport("Aéroport international Léopold-Sédar-Senghor"); // Senegal
        a2.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a2.setLieu(l2);

        Aeroport a3 = new Aeroport();
        a3.setNomAeroport("Aéroport international de Banjul - Yundum"); // Gambie
        a3.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a3.setLieu(l3);

        Aeroport a4 = new Aeroport();
        a4.setNomAeroport("Aéroport international Félix-Houphouët-Boigny"); // Cote ivoire
        a4.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a4.setLieu(l4);

        Aeroport a5 = new Aeroport();
        a5.setNomAeroport("Aéroport international de Douala"); // Cameroun
        a5.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a5.setLieu(l5);

        Aeroport a6 = new Aeroport();
        a6.setNomAeroport("Aéroport international de Gaborone"); // Bostwana
        a6.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a6.setLieu(l6);

        aeroports.add(a1);
        aeroports.add(a2);
        aeroports.add(a3);
        aeroports.add(a4);
        aeroports.add(a5);
        aeroports.add(a6);

        for (Aeroport aeroport : aeroports) {
            em.persist(aeroport);
        }

        Set<BaseTarif> baseTarifs = new HashSet<>();
        BaseTarif bt = new BaseTarif(TypeVol.Charter, 1652);
        BaseTarif bt2 = new BaseTarif(TypeVol.Regulier, 1992);

        baseTarifs.add(bt);
        baseTarifs.add(bt2);

        for (BaseTarif baseTarif : baseTarifs){
            em.persist(baseTarif);
        }

        Set<Vol> vols = new HashSet<>();
        Vol v1 = new Vol(dateDepart, dateArrivee, TypeVol.Charter, bt, av1, l1, 1000);
        Vol v2 = new Vol(dateDepart, dateArrivee, TypeVol.Charter, bt, av2, l3, 1000);
        Vol v3 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, bt2, av3, l4, 1000);
        Vol v4 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, bt2, av4, l5, 1000);

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

        System.out.println("***********1***********");
        List<String> result1 = em.createNamedQuery("query1").getResultList();
        for (String r : result1) {
            System.out.println(r);
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
