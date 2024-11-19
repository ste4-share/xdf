package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.TructhuocLoaiphieu;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.TructhuocLoaiphieuService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Tructhuoc_LoaiphieuImp implements TructhuocLoaiphieuService {

    @Override
    public TructhuocLoaiphieu findByTructhuocId(int tt_id) {
        QDatabase.getConnectionDB();

        String SQL_SELECT = "select * from tructhuoc_loaiphieu where tructhuoc_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tt_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ttId = resultSet.getInt("tructhuoc_id");
                int lpId = resultSet.getInt("loaiphieu_id");

                TructhuocLoaiphieu tructhuocLoaiphieu = new TructhuocLoaiphieu(id, ttId, lpId);
                return tructhuocLoaiphieu;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public TructhuocLoaiphieu findByTTLPId(int tt_id, int lp_id) {
        QDatabase.getConnectionDB();

        String SQL_SELECT = "select * from tructhuoc_loaiphieu where tructhuoc_id=? and loaiphieu_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tt_id);
            preparedStatement.setInt(2, lp_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ttId = resultSet.getInt("tructhuoc_id");
                int lpId = resultSet.getInt("loaiphieu_id");

                return new TructhuocLoaiphieu(id, ttId, lpId);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
}
