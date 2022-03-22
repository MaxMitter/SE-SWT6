package swt6.spring.worklog.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoJdbc implements EmployeeDao {

    //private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//  public void setDataSource(DataSource dataSource) {
//    this.dataSource = dataSource;
//  }

    @Override
    public Optional<Employee> findById(Long aLong) {
        return Optional.empty();
    }

    private static class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            var employee = new Employee();
            employee.setId(rs.getLong(1));
            employee.setFirstName(rs.getString(2));
            employee.setLastName(rs.getString(3));
            employee.setDateOfBirth(rs.getDate(4).toLocalDate());

            return employee;
        }
    }

    @Override
    public List<Employee> findAll() {
        final String sql = "select ID, FIRSTNAME, LASTNAME, DATEOFBIRTH from EMPLOYEE";

        var employeeList = jdbcTemplate.query(sql, new EmployeeMapper());
        return employeeList;
    }

    // Version 1: Data access code without Spring
    public void insert1(final Employee e) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";
        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getFirstName());
            stmt.setString(2, e.getLastName());
            stmt.setDate(3, Date.valueOf(e.getDateOfBirth()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Version 2
    public void insert2(final Employee e) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";

        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
        });
    }

    // Version 3
    public void insert(final Employee e) throws DataAccessException {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
            return ps;
        }, keyHolder);

        e.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Employee merge(Employee entity) {
        return null;
    }
}
