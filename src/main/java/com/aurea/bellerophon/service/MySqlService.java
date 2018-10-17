package com.aurea.bellerophon.service;

import com.aurea.bellerophon.service.DatabaseService;

import javax.sql.DataSource;

public class MySqlService implements DatabaseService {
    public MySqlService(DataSource dataSource) {
    }

    @Override
    public void drop(String databaseName) {

    }

    @Override
    public void create(String databaseName) {

    }
}
