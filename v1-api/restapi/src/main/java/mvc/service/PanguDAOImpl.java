package mvc.service;

import mvc.domain.PanguModel;
import mvc.interfaces.PanguDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 01/04/15.
 */
public class PanguDAOImpl implements PanguDAO {
    @Autowired
    DataSource dataSource;

    @Override
    public List<PanguModel> getAllModels() {
        String sql = "SELECT * FROM pangu_model";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(PanguModel.class));
    }

    public PanguModel findModelById(int id) {
        String sql = "SELECT * FROM pangu_model WHERE id = ? ";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return (PanguModel) jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(PanguModel.class));
    }

    public PanguModel findModelByName(String name) {
        String sql = "SELECT * FROM pangu_model WHERE name = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return (PanguModel) jdbcTemplate.queryForObject(sql, new Object[]{name}, new BeanPropertyRowMapper(PanguModel.class));
    }

    public void addModel(PanguModel panguModel) {
        String sql = "INSERT INTO pangu_model (id, name, discoveredBy, discoveryDate, size, approximateMass, orbitalPeriod, description, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, panguModel);
    }

    public void updateModel(PanguModel panguModel) {
        String sql = "UPDATE pangu_model SET id = ? WHERE id=?, name=?, discoveredBy=?, discoveryDate=?, size=?, approximateMass=?, orbitalPeriod=?, description=?, comments=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, panguModel);
    }

    public void deleteModel(int id) {
        String sql = "DELETE FROM pangu_model WHERE id = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, id);
    }
}
