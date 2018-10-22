package com.aurea.bellerophon.service;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class OracleService implements DatabaseService {

    private JdbcTemplate jdbc;

    public OracleService(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void drop(String databaseName) {
        jdbc.execute("DROP USER " + databaseName + " CASCADE");
    }

    @Override
    public void create(String databaseName) {
        jdbc.execute("CREATE USER " + databaseName + " IDENTIFIED BY jiveadmin");
        jdbc.execute("GRANT CONNECT TO " + databaseName);
        jdbc.execute("GRANT CONNECT, RESOURCE, DBA TO " + databaseName);
        jdbc.execute("GRANT UNLIMITED TABLESPACE TO " + databaseName);
    }

    @Override
    public List<String> list() {
        return jdbc.query("SELECT username FROM dba_users WHERE username like '%JIVE%'", (rs, rowNum) -> rs.getString("username"));
    }
}
