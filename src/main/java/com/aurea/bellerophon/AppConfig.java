package com.aurea.bellerophon;

import com.aurea.bellerophon.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    private static final String BELLEROPHON = "bellerophon.";

    @Autowired
    protected Environment env;

    @Bean
    public Map<String, DatabaseService> databaseServiceMap() {
        HashMap<String, DatabaseService> databaseServiceMap = new HashMap<>();
        for (int i = 1;; i++) {
            String dbId = env.getProperty(BELLEROPHON + "db." + i);
            if (dbId == null) {
                break;
            }

            String prefix = BELLEROPHON + dbId + ".";

            DataSource dataSource = DataSourceBuilder
                    .create()
                    .username(env.getProperty(prefix + "username"))
                    .password(env.getProperty(prefix + "password"))
                    .url(env.getProperty(prefix + "url"))
                    .driverClassName(env.getProperty(prefix + "driver"))
                    .build();

            databaseServiceMap.put(dbId, DbType.valueOf(env.getProperty(prefix + "type")).newDatabaseService(dataSource));
        }

        return databaseServiceMap;
    }
}