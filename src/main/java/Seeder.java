import metier.*;
import util.TypeVol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
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
        Lieu l4 = new Lieu("Abidjan", "Cote d'Ivoire");
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
        a1.setHeuresVolDepuisParis(8);
        a1.setLieu(l1);

        Aeroport a2 = new Aeroport();
        a2.setNomAeroport("Aéroport international Léopold-Sédar-Senghor"); // Senegal
        a2.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a2.setHeuresVolDepuisParis(6);
        a2.setLieu(l2);

        Aeroport a3 = new Aeroport();
        a3.setNomAeroport("Aéroport international de Banjul - Yundum"); // Gambie
        a3.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a3.setHeuresVolDepuisParis(8);
        a3.setLieu(l3);

        Aeroport a4 = new Aeroport();
        a4.setNomAeroport("Aéroport international Félix-Houphouët-Boigny"); // Cote ivoire
        a4.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a4.setHeuresVolDepuisParis(7);
        a4.setLieu(l4);

        Aeroport a5 = new Aeroport();
        a5.setNomAeroport("Aéroport international de Douala"); // Cameroun
        a5.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a5.setHeuresVolDepuisParis(7);
        a5.setLieu(l5);

        Aeroport a6 = new Aeroport();
        a6.setNomAeroport("Aéroport international de Gaborone"); // Bostwana
        a6.setTaxeAeroport((int)(Math.random() * 1000 + 1500));
        a6.setHeuresVolDepuisParis(10);
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

        Set<Vol> vols = new HashSet<>();
        Vol v1 = new Vol(dateDepart, dateArrivee, TypeVol.Charter, av1, a1);
        Vol v2 = new Vol(dateDepart, dateArrivee, TypeVol.Charter, av2, a2);
        Vol v3 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, av3, a3);
        Vol v4 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, av4, a4);
        Vol v5 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, av2, a5);
        Vol v6 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, av1, a6);
        Vol v7 = new Vol(dateDepart, dateArriveeVolsReguliers, TypeVol.Regulier, av3, a1);

        vols.add(v1);
        vols.add(v2);
        vols.add(v3);
        vols.add(v4);
        vols.add(v5);
        vols.add(v6);
        vols.add(v7);

        for (Vol vol : vols) {
            em.persist(vol);
        }

        Set<Abonnement> abonnements = new HashSet<>();
        Abonnement ab = new Abonnement("Abonnement 1", 15095.82);
        Abonnement ab2 = new Abonnement("Abonnement 2", 18795.82);
        Abonnement ab3 = new Abonnement("Abonnement 3", 19595.82);

        abonnements.add(ab);
        abonnements.add(ab2);
        abonnements.add(ab3);

        for (Abonnement abo : abonnements) {
            em.persist(abo);
        }

        final int prixKeroseneParHeure = 1140;

        System.out.println("************ Price calculus below ****************");
        String stringQuery1 = "SELECT a.consommationCarburant, ae.heuresVolDepuisParis, ae.taxeAeroport, l.ville " +
                                "FROM Vol v JOIN v.aeroportArrivee ae JOIN v.avion a JOIN ae.lieu l ";

        Query query1 = em.createQuery(stringQuery1);
        List<Object[]> paramsForPrice = query1.getResultList();
        List<Integer> tousLesPrix = new ArrayList<>();

        for (Object[] o : paramsForPrice) {
            int prix = Integer.valueOf(o[0].toString()) * Integer.valueOf(o[1].toString()) * prixKeroseneParHeure + Integer.valueOf(o[2].toString());
            System.out.println("Prix [Paris-" + o[3].toString() + "] : " + prix + " €");
            tousLesPrix.add(prix);
        }

        v1.setPrixVol(tousLesPrix.get(0));
        v2.setPrixVol(tousLesPrix.get(1));
        v3.setPrixVol(tousLesPrix.get(2));
        v4.setPrixVol(tousLesPrix.get(3));
        v5.setPrixVol(tousLesPrix.get(4));
        v6.setPrixVol(tousLesPrix.get(5));
        v7.setPrixVol(tousLesPrix.get(6));

        System.out.println("Requête : Vol.getVolsCorrespondantsALaDemande");
        Query queryVolsCorrespondantsALaDemande = em.createNamedQuery("Vol.getVolsCorrespondantsALaDemande", Object[].class);
        queryVolsCorrespondantsALaDemande.setParameter("date", Date.valueOf("2017-01-01"));
        queryVolsCorrespondantsALaDemande.setParameter("pays", "Guinee");
        queryVolsCorrespondantsALaDemande.setParameter("capaciteLibre", 10);

        List<Object[]> volsCorrespondantsALaDemande = queryVolsCorrespondantsALaDemande.getResultList();
        for (Object[] o : volsCorrespondantsALaDemande){
            System.out.println("============== VOL CORRESPONDANT ==============");
            System.out.println("Aéroport : " + o[0].toString());
            System.out.println("Pays : " + o[1].toString());
            System.out.println("Date départ : " + o[2].toString());
            System.out.println("Capacité libre : " + o[3].toString());
            System.out.println("Prix : " + o[4].toString());
            System.out.println("===============================================");
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
