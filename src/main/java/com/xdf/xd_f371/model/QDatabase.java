package com.xdf.xd_f371.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class QDatabase {

    public static Connection conn;

    public static void getConnectionDB(){
        try {
            if (conn == null){
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345678a");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
