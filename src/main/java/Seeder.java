import metier.Aeroport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Seeder {
    public static void main(String [] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tpersistancePU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();


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

        

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
