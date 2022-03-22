package swt6.spring.worklog.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DbScriptRunner;
import swt6.util.JpaUtil;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

public class DbTest {

    private static void createSchema(DataSource ds, String ddlScript) {
        try {
            DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
            InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
            if (is == null)
                throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
            scriptRunner.runScript(new InputStreamReader(is));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void testJdbc() {

        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext(
                             "swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

            printTitle("create schema", 60, '-');
            createSchema(factory.getBean("dataSource", DataSource.class),
                    "swt6/spring/worklog/test/CreateWorklogDbSchema.sql");

            //
            // get reference to implementation of EmployeeDao
            //
            EmployeeDao emplDao = factory.getBean("employeeDaoJDBC", EmployeeDao.class);

            printTitle("insert employee", 60, '-');
            Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
            emplDao.insert(empl1);
            System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

//      printTitle("update employee", 60, '-');
//      empl1.setFirstName("Jaquira");
//      empl1 = emplDao.merge(empl1);
//      System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

            printTitle("find employee", 60, '-');
            Optional<Employee> empl = emplDao.findById(1L);
            System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));
            empl = emplDao.findById(100L);
            System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));

            printTitle("find all employees", 60, '-');
            emplDao.findAll().forEach(System.out::println);
        }
    }

    @SuppressWarnings("unused")
    private static void testJpa() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            var fact = factory.getBean(EntityManagerFactory.class);
            var emplDao = factory.getBean("employeeDaoJpa", EmployeeDao.class);
            //JpaUtil.beginTransaction(fact);

            printTitle("insert employee", 60, '-');
            JpaUtil.executeInTransaction(fact, () -> {
                Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
                emplDao.insert(empl1);
                System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
            });

            printTitle("find employee by id", 60, '-');
            JpaUtil.executeInTransaction(fact, () -> {
                Optional<Employee> empl = emplDao.findById(1L);
                System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));
                empl = emplDao.findById(100L);
                System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));
            });

            printTitle("find all employees", 60, '-');
            JpaUtil.executeInTransaction(fact, () -> {
                emplDao.findAll().forEach(System.out::println);
            });

            //JpaUtil.commitTransaction(fact);
        }
    }

    @SuppressWarnings("unused")
    private static void testSpringData() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            var emFactory = factory.getBean(EntityManagerFactory.class);

            printTitle("insert employees", 60, '-');
            JpaUtil.executeInTransaction(emFactory, () -> {
                EmployeeRepository employeeRepo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);
                Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
                Employee empl2 = new Employee("Franz", "Oberbauer", LocalDate.of(2000, 10, 26));
                empl1 = employeeRepo.save(empl1);
                empl2 = employeeRepo.save(empl2);
            });

            printTitle("test queries", 60, '-');
            JpaUtil.executeInTransaction(emFactory, () -> {
                EmployeeRepository employeeRepo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);
                var empl = employeeRepo.findByLastName("Oberbauer");
                System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));

                printSeparator(60);

                var empl2 = employeeRepo.findByLastNameContaining("bauer");
                for (var employee : empl2) {
                    System.out.println(employee);
                }

                printSeparator(60);

                var empls = employeeRepo.findOlderThan(LocalDate.of(2000, 1, 1));
                for (var employee : empls) {
                    System.out.println(employee);
                }
            });
        }
    }

    public static void main(String[] args) {

        //printSeparator(60);
        //printTitle("testJDBC", 60);
        //printSeparator(60);
        //testJdbc();

        //printSeparator(60);
        //printTitle("testJpa", 60);
        //printSeparator(60);
        //testJpa();

        printSeparator(60);
        printTitle("testSpringData", 60);
        printSeparator(60);
        testSpringData();
    }
}
