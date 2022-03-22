package swt6.spring.worklog.api;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.dto.EmployeeDto;
import swt6.spring.worklog.logic.WorkLogService;

import java.util.List;

@RestController
@RequestMapping(value = "worklog", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    @Autowired
    private WorkLogService workLog;

    @Autowired
    private ModelMapper mapper;

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping(value = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        logger.info("hello() invoked");
        return "Hello from Spring Boot REST Controller";
    }

    @GetMapping("employees")
    @Operation(summary = "get all employees",
               description = "returns a list of all employees")
    public List<Employee> getEmployees() {
        logger.info("getEmployees() invoked");
        var list = workLog.findAllEmployees();
        var type = (new TypeToken<List<EmployeeDto>>() {}).getType();

        return mapper.map(list, type);
    }
}
