package swt6.orm.jpa;

import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkLogManager {

    private static void insertEmployee(Employee employee) {
        var factory = Persistence.createEntityManagerFactory("WorkLogPU");
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = factory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            em.persist(employee);

            tx.commit();
            em.close();
        } catch (Exception ex) {
            if (tx != null && tx.isActive())
                tx.rollback();

            if (em != null)
                em.close();
        }
        factory.close();
    }

    private static void listLogbookEntries() {
        try {
            var em = JpaUtil.getTransactedEntityManager();
            var employeeList = em.createQuery("select e from Employee e", Employee.class).getResultList();
            employeeList.forEach(employee -> {
                System.out.println(employee);
                if (employee.getLogBookEntries().size() > 0) {
                    employee.getLogBookEntries().forEach(entry -> {
                        System.out.println("    " + entry);
                    });
                }
            });
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
    }

    private static Employee addLogbookEntries(Employee employee, LogBookEntry... entries) {
        Employee e = null;
        try {
            var em = JpaUtil.getTransactedEntityManager();
            for (var entry : entries) {
                employee.addLogBookEntry(entry);
            }
            e = saveEntity(employee);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
        return e;
    }

    private static <T> void insertEntity(T entity) {
        try {
            var em = JpaUtil.getTransactedEntityManager();
            em.persist(entity);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
    }

    private static <T> T saveEntity(T entity) {
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

    public static void main(String[] args) {
        JpaUtil.getEntityManagerFactory();

        var entry1 = new LogBookEntry("Testen", LocalDateTime.of(2022, 3, 8, 10,30), LocalDateTime.of(2022, 3, 8, 10, 50));
        var entry2 = new LogBookEntry("Doku", LocalDateTime.of(2022, 3, 8, 10,30), LocalDateTime.of(2022, 3, 8, 10, 50));
        var entry3 = new LogBookEntry("Programmieren", LocalDateTime.of(2022, 3, 8, 10,30), LocalDateTime.of(2022, 3, 8, 10, 50));

        var addr = new Address("4020", "Linz", "Hauptstraße 1");
        var empl1 = new PermanentEmployee("Rainer", "Zufall", LocalDate.of(1999, 1, 1));
        empl1.setSalary(6.50);
        empl1.setAddress(addr);
        var empl2 = new TemporaryEmployee("Mike", "Hawk", LocalDate.of(2005, 1, 1));
        empl2.setHourlyRate(420.69);

        insertEntity(empl1);
        insertEntity(empl2);

        addLogbookEntries(empl1, entry1, entry2, entry3);

        listLogbookEntries();

        JpaUtil.closeEntityManagerFactory();
    }
}
