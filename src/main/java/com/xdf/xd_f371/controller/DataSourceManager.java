package com.xdf.xd_f371.controller;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceManager {
    @Autowired
    private DataSource dataSource;

    public void configureDataSource(String url, String username, String password) {
        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            hikariDataSource.setJdbcUrl(url);
            hikariDataSource.setUsername(username);
            hikariDataSource.setPassword(password);
        } else {
            throw new IllegalStateException("DataSource is not an instance of HikariDataSource.");
        }
    }
}
