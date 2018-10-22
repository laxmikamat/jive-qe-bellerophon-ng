package com.aurea.bellerophon.service;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class MySqlService implements DatabaseService {

    private JdbcTemplate jdbc;

    public MySqlService(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void drop(String databaseName) {
        jdbc.execute("DROP database " + databaseName);
    }

    @Override
    public void create(String databaseName) {
        jdbc.execute("CREATE database " + databaseName);
    }

    @Override
    public List<String> list() {
        return jdbc.query("SHOW DATABASES", (rs, rowNum) -> rs.getString("database"));
    }
}
