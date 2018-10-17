package com.aurea.bellerophon.service;

import com.aurea.bellerophon.service.DatabaseService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class OracleService implements DatabaseService {

    private JdbcTemplate jdbc;

    public OracleService(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void drop(String databaseName) {
        jdbc.execute("DROP USER " + databaseName);
    }

    @Override
    public void create(String databaseName) {
        jdbc.execute(String.format(
                "CREATE USER %s IDENTIFIED BY jiveadmin;\n" +
                "GRANT CONNECT TO %s;\n" +
                "GRANT CONNECT, RESOURCE, DBA TO %s;\n" +
                "GRANT CREATE SESSION GRANT ANY PRIVILEGE TO %s;", databaseName));
    }
}
