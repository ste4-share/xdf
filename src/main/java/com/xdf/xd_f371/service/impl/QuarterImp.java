package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.QuarterService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuarterImp implements QuarterService {
    @Override
    public List<Quarter> findByYear(String year) {
        return List.of();
    }

    @Override
    public List<Quarter> getAll() {
        QDatabase.getConnectionDB();
        List<Quarter> ls = new ArrayList<>();


        String SQL_SELECT = "Select * from quarter";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Date start_date = resultSet.getDate("start_date");
                Date end_date = resultSet.getDate("end_date");
                String year = resultSet.getString("year");
                String status = resultSet.getString("status");
                String convey = resultSet.getString("convey");

                Quarter result = new Quarter();
                result.setId(id);
                result.setName(name);
                result.setStart_date(start_date.toLocalDate());
                result.setEnd_date(end_date.toLocalDate());
                result.setYear(year);
                result.setStatus(status);
                result.setConvey(convey);
                ls.add(result);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    public Quarter findById(int id1) {
        QDatabase.getConnectionDB();
        Quarter result = new Quarter();


        String SQL_SELECT = "Select * from quarter where id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Date start_date = resultSet.getDate("start_date");
                Date end_date = resultSet.getDate("end_date");
                String year = resultSet.getString("year");
                String status = resultSet.getString("status");
                String convey = resultSet.getString("convey");


                result.setId(id);
                result.setName(name);
                result.setStart_date(start_date.toLocalDate());
                result.setEnd_date(end_date.toLocalDate());
                result.setYear(year);
                result.setStatus(status);
                result.setConvey(convey);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Quarter create(Quarter quarter) {
        QDatabase.getConnectionDB();
        String sql = "insert into quarter(name, start_date, end_date, year, status, convey) values(?,?,?,?,?,?)";
        try {

            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, quarter.getName());
            statement.setDate(2, Date.valueOf(quarter.getStart_date()));
            statement.setDate(3, Date.valueOf(quarter.getEnd_date()));
            statement.setString(4, quarter.getYear());
            statement.setString(5, quarter.getStatus());
            statement.setString(6, quarter.getConvey());
            statement.executeUpdate();
            System.out.println("Record quarter updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quarter;
    }

    @Override
    public Quarter findByDatetime(LocalDate date1) {
        QDatabase.getConnectionDB();
        Quarter result = new Quarter();


        String SQL_SELECT = "select * from quarter where ? between start_date and end_date";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setDate(1, Date.valueOf(date1));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Date start_date = resultSet.getDate("start_date");
                Date end_date = resultSet.getDate("end_date");
                String year = resultSet.getString("year");
                String status = resultSet.getString("status");
                String convey = resultSet.getString("convey");


                result.setId(id);
                result.setName(name);
                result.setStart_date(start_date.toLocalDate());
                result.setEnd_date(end_date.toLocalDate());
                result.setYear(year);
                result.setStatus(status);
                result.setConvey(convey);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Quarter findByName(String name1) {
        QDatabase.getConnectionDB();
        Quarter result = new Quarter();


        String SQL_SELECT = "Select * from quarter where name=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, name1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Date start_date = resultSet.getDate("start_date");
                Date end_date = resultSet.getDate("end_date");
                String year = resultSet.getString("year");
                String status = resultSet.getString("status");
                String convey = resultSet.getString("convey");


                result.setId(id);
                result.setName(name);
                result.setStart_date(start_date.toLocalDate());
                result.setEnd_date(end_date.toLocalDate());
                result.setYear(year);
                result.setStatus(status);
                result.setConvey(convey);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
