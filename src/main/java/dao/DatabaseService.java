package dao;

import messages.DemandeVols;
import messages.VolAssociation;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TypeVol;

import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    public static void saveCollectionInDB(Collection collection) {
        Session session = HibernateSessionProvider.getSessionFactory().openSession();
        session.beginTransaction();

        for (Object o : collection) {
            session.save(o);
        }

        session.getTransaction().commit();
        session.close();
    }

    public static void saveObjectInDB(Object o) {
        Session session = HibernateSessionProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
    }

  public static ArrayList<VolAssociation> getVols(TypeVol typeVol, String date, String pays, int capaciteLibre) {

    ArrayList<VolAssociation> volsPourLesAssociation = new ArrayList<VolAssociation>();

    try{
      String query = "Vol.getVolsCorrespondantsALaDemande";
      List<Object[]> volsCorrespondantsALaDemande;

      volsCorrespondantsALaDemande = daoGetVols(typeVol, query, date, pays, capaciteLibre);
      showFlightsResults(volsCorrespondantsALaDemande);
      volsPourLesAssociation = formatVol(volsCorrespondantsALaDemande);
    }catch (Exception e){
      e.printStackTrace();
    }

    return volsPourLesAssociation;

  }

  public static List<Object[]> daoGetVols(TypeVol typeVol, String query, String date, String pays, int capaciteLibre) {
    Session session = HibernateSessionProvider.getSessionFactory().openSession();

    Query queryVolsReguliersCorrespondantsALaDemande = session.createNamedQuery(query, Object[].class);
    String associationDatePattern = "MMM dd, yyyy HH:mm:ss a";
    java.util.Date dateVol = null;
    //java.util.Date dateVol = new java.util.Date();
    try {
      dateVol = new SimpleDateFormat(associationDatePattern).parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    System.out.println("DATE : "+dateVol.toString());
    queryVolsReguliersCorrespondantsALaDemande.setParameter("date", new java.sql.Date(dateVol.getTime()));
    queryVolsReguliersCorrespondantsALaDemande.setParameter("pays", pays);
    queryVolsReguliersCorrespondantsALaDemande.setParameter("capaciteLibre", capaciteLibre);
    queryVolsReguliersCorrespondantsALaDemande.setParameter("typeVol", typeVol);

    List<Object[]> resultat = queryVolsReguliersCorrespondantsALaDemande.getResultList();
    session.close();

    return resultat;
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

    //On aurait pu passer par les objets..
    public static void updateCapaciteVol(String uuid, Integer capacitePrise) {
        Session session = HibernateSessionProvider.getSessionFactory().openSession();
        session.beginTransaction();

        Query queryUpdateCapaciteVol = session.createNamedQuery("Vol.updateCapaciteLibreVol");
        queryUpdateCapaciteVol.setParameter("idVol", uuid);
        queryUpdateCapaciteVol.setParameter("capacitePrise", capacitePrise);
        queryUpdateCapaciteVol.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    public static void showFlightsResults(List<Object[]> volsChartersCorrespondantsALaDemande) {
        for (Object[] o : volsChartersCorrespondantsALaDemande) {
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

    public static ArrayList<VolAssociation> formatVol(List<Object[]> volsCorrespondantsALaDemande) {
        ArrayList<VolAssociation> volsPourLesAssociation = new ArrayList<>();
        for (Object[] o : volsCorrespondantsALaDemande) {
            volsPourLesAssociation.add(
                    new VolAssociation(
                            o[5].toString(),
                            o[0].toString(),
                            o[1].toString(),
                            java.sql.Date.valueOf(o[2].toString()),
                            Integer.parseInt(o[3].toString()),
                            Integer.parseInt(o[4].toString().substring(0, o[4].toString().indexOf("."))),
                            TypeVol.Charter
                    )
            );
        }
        return volsPourLesAssociation;
    }

    public static Map<String, Integer> calculerPrixVols() {
        Session session = HibernateSessionProvider.getSessionFactory().openSession();

        final int prixKeroseneParHeure = 1140;

        logger.info("Requête : Vol.calculerLesPrixDesVols");

        Query queryCalculerLesPrixDesVols = session.createNamedQuery("Vol.calculerLesPrixDesVols", Object[].class);
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

        session.close();

        return tousLesPrixCoutant;
    }

    public static ArrayList<VolAssociation> getVolsAssociation(DemandeVols demandeVols) {
        ArrayList<VolAssociation> volAssociations = new ArrayList<VolAssociation>();
        try {
            volAssociations = getVols(TypeVol.Charter, demandeVols.getDate().toString(), demandeVols.getPays(), demandeVols.getVolume());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return volAssociations;
    }
}
