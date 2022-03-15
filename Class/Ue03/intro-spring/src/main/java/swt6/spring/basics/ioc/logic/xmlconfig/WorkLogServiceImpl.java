package swt6.spring.basics.ioc.logic.xmlconfig;

import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.util.Logger;
import swt6.spring.basics.ioc.util.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class WorkLogServiceImpl implements WorkLogService {
    private Map<Long, Employee> employees = new HashMap<Long, Employee>();

    private Logger logger;

    private void init() {
        employees.put(1L, new Employee(1L, "Bill", "Gates"));
        employees.put(2L, new Employee(2L, "James", "Goslin"));
        employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
    }

    public WorkLogServiceImpl() {
        init();
    }


    @Override
    public Employee findEmployeeById(Long id) {

        var emp = employees.get(id);
        logger.log(String.format("findEmployeeById: %s", id == null ? "<null>" : emp.toString()));
        return emp;
    }

    @Override
    public List<Employee> findAllEmployees() {
        logger.log(String.format("findAllEmployees"));
        return new ArrayList<Employee>(employees.values());
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
