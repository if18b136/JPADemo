package at.technikumwien;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewsPU");
        EntityManager em = emf.createEntityManager();

        // Add a new category
        em.getTransaction().begin(); // returns current transaction object

        Category category = new Category("Politik");
        em.persist(category);

        em.getTransaction().commit();   // needed so we get the new category

        em.close();
    }
}
