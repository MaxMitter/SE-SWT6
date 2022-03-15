package swt6.spring.basics.ioc.logic.factorybased;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.util.Logger;
import swt6.spring.basics.ioc.util.LoggerFactory;

public class WorkLogServiceImpl {
    private Map<Long, Employee> employees = new HashMap<Long, Employee>();

    private Logger logger;

    private void initLogger() {
        Properties props = new Properties();

        try {
            ClassLoader cl = this.getClass().getClassLoader();
            props.load(cl.getResourceAsStream(
                    "swt6/spring/basics/ioc/logic/factorybased/worklog.properties"));
            String loggerType = props.getProperty("loggerType", "console");
            logger = LoggerFactory.getLogger(loggerType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {

        employees.put(1L, new Employee(1L, "Bill", "Gates"));
        employees.put(2L, new Employee(2L, "James", "Goslin"));
        employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
    }

    public WorkLogServiceImpl() {
        initLogger();
        init();
    }


    public Employee findEmployeeById(Long id) {

        var emp = employees.get(id);
        logger.log(String.format("findEmployeeById: %s", id == null ? "<null>" : emp.toString()));
        return emp;
    }

    public List<Employee> findAllEmployees() {
        logger.log(String.format("findAllEmployees"));
        return new ArrayList<Employee>(employees.values());
    }
}
