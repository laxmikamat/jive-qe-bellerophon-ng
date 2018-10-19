package com.aurea.bellerophon;

import com.aurea.bellerophon.service.DatabaseService;
import com.aurea.bellerophon.service.MySqlService;
import com.aurea.bellerophon.service.OracleService;
import com.aurea.bellerophon.service.PostgresService;

import javax.sql.DataSource;

public enum DbType {
    POSTGRES {
        @Override
        DatabaseService newDatabaseService(DataSource dataSource) {
            return new PostgresService(dataSource);
        }
    },
    ORACLE {
        @Override
        DatabaseService newDatabaseService(DataSource dataSource) {
            return new OracleService(dataSource);
        }
    },
    MYSQL {
        @Override
        DatabaseService newDatabaseService(DataSource dataSource) {
            return new MySqlService(dataSource);
        }
    };

    abstract DatabaseService newDatabaseService(DataSource dataSource);
}