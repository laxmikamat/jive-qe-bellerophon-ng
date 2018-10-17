package com.aurea.bellerophon.service;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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
}
