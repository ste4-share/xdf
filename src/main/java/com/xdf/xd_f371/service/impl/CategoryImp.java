package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.CategoryService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryImp implements CategoryService {
    @Override
    public List<String> getAllTypeTitle() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();


        String SQL_SELECT = "select type_title from category group by type_title";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("type_title"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
    @Override
    public List<String> getAllCode() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();


        String SQL_SELECT = "select code from category group by code";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("code"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> getAll_Header1() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String SQL_SELECT = "select header_lv1 from category group by header_lv1";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("header_lv1"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> getAll_Header2() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String SQL_SELECT = "select header_lv2 from category group by header_lv2";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("header_lv2"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> getAll_Header3() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String SQL_SELECT = "select header_lv3 from category group by header_lv3";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("header_lv3"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
}
