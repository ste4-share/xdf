package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.Tcn;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.TcnService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TcnImp implements TcnService {

    @Override
    public List<Tcn> getAll() {
        QDatabase.getConnectionDB();
        List<Tcn> result = new ArrayList<>();


        String SQL_SELECT = "SELECT * FROM tcn order by concert;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int concert = resultSet.getInt("concert");
                String status = resultSet.getString("status");
                int loaiphieuId = resultSet.getInt("loaiphieu_id");
                Tcn obj = new Tcn();
                obj.setId(id);
                obj.setName(name);
                obj.setConcert(concert);
                obj.setStatus(status);
                obj.setLoaiphieu_id(loaiphieuId);
                result.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Tcn> getAllByName(String norx) {
        QDatabase.getConnectionDB();
        List<Tcn> result = new ArrayList<>();


        String SQL_SELECT = "SELECT * FROM tcn where name like ?;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1,"%"+norx+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int concert = resultSet.getInt("concert");
                String status = resultSet.getString("status");
                int loaiphieuId = resultSet.getInt("loaiphieu_id");
                Tcn obj = new Tcn();
                obj.setId(id);
                obj.setName(name);
                obj.setConcert(concert);
                obj.setStatus(status);
                obj.setLoaiphieu_id(loaiphieuId);
                result.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Tcn> getAllByBillTypeId(int billId) {
        QDatabase.getConnectionDB();
        List<Tcn> result = new ArrayList<>();


        String SQL_SELECT = "SELECT * FROM tcn where loaiphieu_id=?;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1,billId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int concert = resultSet.getInt("concert");
                String status = resultSet.getString("status");
                int loaiphieuId = resultSet.getInt("loaiphieu_id");
                Tcn obj = new Tcn();
                obj.setId(id);
                obj.setName(name);
                obj.setConcert(concert);
                obj.setStatus(status);
                obj.setLoaiphieu_id(loaiphieuId);
                result.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Tcn findByID(int id) {
        return null;
    }

    @Override
    public Tcn findByName(String sear_name) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "select * from tcn where name=?";
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, sear_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int concert = resultSet.getInt("concert");
                String status = resultSet.getString("status");
                int loaiphieuId = resultSet.getInt("loaiphieu_id");
                return new Tcn(id,loaiphieuId, name, concert, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Tcn create(Tcn tcn) {
        QDatabase.getConnectionDB();
        String sql = "insert into tcn(name, concert, status, loaiphieu_id) values(?,?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, tcn.getName());
            statement.setInt(2, tcn.getConcert());
            statement.setString(3, tcn.getStatus());
            statement.setInt(4, tcn.getLoaiphieu_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return tcn;
    }

    @Override
    public Tcn update(Tcn tcn) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
