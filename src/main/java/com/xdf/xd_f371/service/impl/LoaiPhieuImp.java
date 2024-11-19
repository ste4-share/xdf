package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.LoaiPhieu;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.LoaiPhieuService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiPhieuImp implements LoaiPhieuService {
    @Override
    public List<LoaiPhieu> getAll() {
        List<LoaiPhieu> ls = new ArrayList<>();
        QDatabase.getConnectionDB();


        String SQL_SELECT = "Select * from loai_phieu";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                LoaiPhieu loaiPhieu = new LoaiPhieu(id, type);
                ls.add(loaiPhieu);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    public LoaiPhieu findLoaiPhieuByID(int id1) {
        QDatabase.getConnectionDB();
        LoaiPhieu loaiPhieu = new LoaiPhieu();
        String SQL_SELECT = "Select * from loai_phieu where id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                LoaiPhieu loaiPhieu1 = new LoaiPhieu(id, type);
                return loaiPhieu1;
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loaiPhieu;
    }

    @Override
    public LoaiPhieu findLoaiPhieuByType(String type1) {
        QDatabase.getConnectionDB();
        LoaiPhieu loaiPhieu = new LoaiPhieu();
        String SQL_SELECT = "Select * from loai_phieu where type=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, type1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                return new LoaiPhieu(id, type);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loaiPhieu;
    }
}
