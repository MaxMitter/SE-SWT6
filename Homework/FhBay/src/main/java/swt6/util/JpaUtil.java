package swt6.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory emFactory;
    private static ThreadLocal<EntityManager> emThread = new ThreadLocal<>();

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emFactory == null)
            emFactory = Persistence.createEntityManagerFactory("WorkLogPU");

        return emFactory;
    }

    public static synchronized EntityManager getEntityManager() {
        if (emFactory == null)
            getEntityManagerFactory();

        if (emThread.get() == null)
            emThread.set(emFactory.createEntityManager());

        return emThread.get();
    }

    public static synchronized void closeEntityManager() {
        if (emThread != null) {
            emThread.get().close();
            emThread.set(null);
        }
    }

    public static synchronized EntityManager getTransactedEntityManager() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (!tx.isActive())
            tx.begin();

        return em;
    }

    public static synchronized void commit() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (tx.isActive())
            tx.commit();

        closeEntityManager();
    }

    public static synchronized void rollback() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (tx.isActive())
            tx.rollback();

        closeEntityManager();
    }

    public static synchronized void closeEntityManagerFactory() {
        if (emFactory != null)
            emFactory.close();
    }

    public static synchronized void resetTableForTests(String tableName) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("delete from " + tableName);
        query.executeUpdate();
        JpaUtil.commit();
    }

    public static synchronized void resetDatabase() {
        resetTableForTests("Bid");
        resetTableForTests("Product");
        resetTableForTests("Customer");
        resetTableForTests("PaymentMethod");
    }
}