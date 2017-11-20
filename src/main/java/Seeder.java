import metier.Abonnement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Seeder {
    public static void main(String [] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jadeprojectPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

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
