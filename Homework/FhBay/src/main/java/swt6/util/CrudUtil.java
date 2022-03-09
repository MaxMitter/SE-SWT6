package swt6.util;

public class CrudUtil {
    public static <T> void insertEntity(T entity) {
        try {
            var em = JpaUtil.getTransactedEntityManager();
            em.persist(entity);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
    }

    public static <T> T saveEntity(T entity) {
        T e = null;
        try {
            var em = JpaUtil.getTransactedEntityManager();
            e = em.merge(entity);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
        return e;
    }
}
