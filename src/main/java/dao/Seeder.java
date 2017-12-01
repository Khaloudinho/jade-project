package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.VolAssociation;
import metier.*;
import util.TypeVol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.*;

public class Seeder {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jadeprojectPU");
    private static EntityManager em = emf.createEntityManager();
    private static ObjectMapper mapper = new ObjectMapper();

    /*public static void getVolsParTypeVol(TypeVol typeVol) {

        String query = "Vol.getVolsParType";
        List<Object[]> vols = daoGetVolsParType(typeVol, query);
        showFlightsByTypeResults(vols);

        for (Object[] o : vols) {
            int prix = Integer.parseInt(o[2].toString());
            long dateCourante = System.currentTimeMillis();
            long dateDepart = Date.valueOf(o[1].toString()).getTime();
            if (prix > prix * 100 / 120 && dateDepart > dateCourante) {
                Vol vol = (Vol)getVolParId(o[0].toString());
                vol.setPrixVol(prix * .9);
            }
        }
    }

    public static Object getVolParId(String idVol) {
        String query = "Vol.getVolParId";
        Object vol = daoGetVolParId(idVol, query);
        return vol;
    }*/

    public static ArrayList<VolAssociation> getVols(TypeVol typeVol, String date, String pays, int capaciteLibre) throws JsonProcessingException {

        String query = "Vol.getVolsCorrespondantsALaDemande";
        ArrayList<VolAssociation> volsPourLesAssociation;
        List<Object[]> volsCorrespondantsALaDemande;

        volsCorrespondantsALaDemande = daoGetVols(typeVol, query, date, pays, capaciteLibre);
        showFlightsResults(volsCorrespondantsALaDemande);
        volsPourLesAssociation = formatVol(volsCorrespondantsALaDemande);

        //Object to JSON in String
        String volsReguliersInJSON = mapper.writeValueAsString(volsPourLesAssociation);
        System.out.println("vols : " + volsReguliersInJSON);

        return volsPourLesAssociation;
    }

    /*public static List<Object[]> daoGetVolsParType(TypeVol typeVol, String query){
        Query queryVolsParType = em.createNamedQuery(query, Object[].class);
        queryVolsParType.setParameter("typeVol", typeVol);
        return queryVolsParType.getResultList();
    }

    public static List<Object[]> daoGetVolParId(String idVol, String query) {
        Query queryVolParId = em.createNamedQuery(query, Object.class);
        queryVolParId.setParameter("id", idVol);
        return queryVolParId.getResultList();
    }*/

    public static List<Object[]> daoGetVols(TypeVol typeVol, String query, String date, String pays, int capaciteLibre){
        Query queryVolsCorrespondantsALaDemande = em.createNamedQuery(query, Object[].class);
        queryVolsCorrespondantsALaDemande.setParameter("date", Date.valueOf(date));
        queryVolsCorrespondantsALaDemande.setParameter("pays", pays);
        queryVolsCorrespondantsALaDemande.setParameter("capaciteLibre", capaciteLibre);
        queryVolsCorrespondantsALaDemande.setParameter("typeVol", typeVol);
        return queryVolsCorrespondantsALaDemande.getResultList();
    }

    public static void showFlightsByTypeResults(List<Object[]> vols){
        for (Object[] o : vols){
            System.out.println("============== VOL CORRESPONDANT ==============");
            System.out.println("Id vol : " + o[0].toString());
            System.out.println("Date départ : " + o[1].toString());
            System.out.println("Prix : " + o[2].toString());
            System.out.println("========================================================");
        }
    }

    public static void updateCapaciteVol(String uuid, Integer capacitePrise){
        em.getTransaction().begin();
        Query queryUpdateCapaciteVol = em.createNamedQuery("Vol.updateCapaciteLibreVol");
        queryUpdateCapaciteVol.setParameter("idVol",uuid);
        queryUpdateCapaciteVol.setParameter("capacitePrise", capacitePrise);
        queryUpdateCapaciteVol.executeUpdate();
        em.getTransaction().commit();
    }

    public static void showFlightsResults(List<Object[]> volsChartersCorrespondantsALaDemande){
        for (Object[] o : volsChartersCorrespondantsALaDemande){
            System.out.println("============== VOL CORRESPONDANT ==============");
            System.out.println("Aéroport : " + o[0].toString());
            System.out.println("Pays : " + o[1].toString());
            System.out.println("Date arrivée : " + o[2].toString());
            System.out.println("Capacité libre : " + o[3].toString());
            System.out.println("Prix : " + o[4].toString());
            System.out.println("IdVol : " + o[5].toString());
            System.out.println("========================================================");
        }
    }

    public static ArrayList<VolAssociation> formatVol(List<Object[]> volsCorrespondantsALaDemande){
        ArrayList<VolAssociation> volsPourLesAssociation = new ArrayList<>();
        for (Object[] o : volsCorrespondantsALaDemande){
            volsPourLesAssociation.add(
                    new VolAssociation(
                            o[5].toString(),
                            o[0].toString(),
                            o[1].toString(),
                            Date.valueOf(o[2].toString()),
                            Integer.parseInt(o[3].toString()),
                            Integer.parseInt(o[4].toString().substring(0, o[4].toString().indexOf("."))),
                            TypeVol.Charter
                    )
            );
        }
        return volsPourLesAssociation;
    }

    public static Map<String, Integer> calculerPrixVols(){

        final int prixKeroseneParHeure = 1140;

        System.out.println("Requête : Vol.calculerLesPrixDesVols");
        Query queryCalculerLesPrixDesVols = em.createNamedQuery("Vol.calculerLesPrixDesVols", Object[].class);
        List<Object[]> paramPourCalculerLesPrixDesVols = queryCalculerLesPrixDesVols.getResultList();
        Map<String, Integer> tousLesPrixCoutant = new HashMap<>();
        for (Object[] o : paramPourCalculerLesPrixDesVols) {
            String idVol = o[0].toString();
            int consommationCarburant = Integer.valueOf(o[1].toString());
            int heuresVolDepuisParis = Integer.valueOf(o[2].toString());
            int taxeAeroport = Integer.valueOf(o[3].toString());
            int prixCoutant = consommationCarburant * heuresVolDepuisParis * prixKeroseneParHeure + taxeAeroport;
            tousLesPrixCoutant.put(idVol, prixCoutant);
        }

        return tousLesPrixCoutant;
    }

    public static void main(String [] args) throws JsonProcessingException {

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
        Vol v1 = new Vol(dateDepart, dateArrivee, TypeVol.Charter, av1, a1, 40);
        Vol v2 = new Vol(dateDepart, dateArrivee, TypeVol.Charter, av2, a2, 40);
        Vol v3 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, av3, a3, 40);
        Vol v4 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, av4, a4, 40);
        Vol v5 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, av2, a5, 40);
        Vol v6 = new Vol(dateDepartVolsReguliers, dateArriveeVolsReguliers, TypeVol.Regulier, av1, a6, 40);
        Vol v7 = new Vol(dateDepart, dateArriveeVolsReguliers, TypeVol.Regulier, av3, a1, 40);

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

        final Map<String, Integer> prixVols = calculerPrixVols();

        for (Vol vol : vols) {
            vol.setPrixCoutant(prixVols.get(vol.getIdVol()));
            vol.setPrixDeVente(prixVols.get(vol.getIdVol()) * 1.2);
        }

        //getVolsParTypeVol(TypeVol.Charter);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
