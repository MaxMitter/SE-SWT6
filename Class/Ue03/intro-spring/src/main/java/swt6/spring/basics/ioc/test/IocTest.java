package swt6.spring.basics.ioc.test;

import swt6.spring.basics.ioc.logic.factorybased.WorkLogServiceImpl;
import swt6.util.PrintUtil;

public class IocTest {

  private static void testSimple() {
    WorkLogServiceImpl workLogService = new WorkLogServiceImpl();

    workLogService.findEmployeeById(1L);
    workLogService.findEmployeeById(99L);
    workLogService.findAllEmployees();
  }

  public static void main(String[] args) {
    PrintUtil.printTitle("testSimple", 60);
    testSimple();

    PrintUtil.printSeparator(60);
  }
}
