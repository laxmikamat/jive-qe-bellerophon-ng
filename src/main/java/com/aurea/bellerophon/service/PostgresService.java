package com.aurea.bellerophon.service;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class PostgresService implements DatabaseService {

    private JdbcTemplate jdbc;

    public PostgresService(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void drop(String databaseName) {
        jdbc.execute("DROP database " + databaseName);
    }

    @Override
    public void create(String databaseName) {
        jdbc.execute("CREATE database " + databaseName + " OWNER postgres");
    }

    @Override
    public List<String> list() {
        return jdbc.query("SELECT datname FROM pg_database where datname like '%jive%'", (rs, rowNum) -> rs.getString("datname"));
    }
}
