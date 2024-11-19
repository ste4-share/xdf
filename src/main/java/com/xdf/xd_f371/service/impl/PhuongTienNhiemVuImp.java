package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.PhuongTienNhiemVu;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.PhuongTienNhiemVuService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhuongTienNhiemVuImp implements PhuongTienNhiemVuService {
    @Override
    public List<PhuongTienNhiemVu> getAll() {
        return List.of();
    }

    @Override
    public PhuongTienNhiemVu createNew(PhuongTienNhiemVu phuongTienNhiemVu) {
        QDatabase.getConnectionDB();
        String sql = "insert into phuongtien_nhiemvu(phuongtien_id, nvu_id) values(?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, phuongTienNhiemVu.getPhuongtien_id());
            statement.setInt(2, phuongTienNhiemVu.getNhiemvu_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phuongTienNhiemVu;
    }

    @Override
    public PhuongTienNhiemVu update(PhuongTienNhiemVu phuongTienNhiemVu) {
        return null;
    }

    @Override
    public List<PhuongTienNhiemVu> findByPhuongtienIdAndNvu_id(int phuongtien_id, int nvu_id) {
        QDatabase.getConnectionDB();
        List<PhuongTienNhiemVu> result = new ArrayList<>();

        String SQL_SELECT = "select * from phuongtien_nhiemvu where phuongtien_id=? and nvu_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, phuongtien_id);
            preparedStatement.setInt(2, nvu_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int phuongtienId = resultSet.getInt("phuongtien_id");
                int nhiemvuID = resultSet.getInt("nvu_id");

                PhuongTienNhiemVu phuongTienNhiemVu = new PhuongTienNhiemVu();
                phuongTienNhiemVu.setId(id);
                phuongTienNhiemVu.setNhiemvu_id(nhiemvuID);
                phuongTienNhiemVu.setPhuongtien_id(phuongtien_id);
                result.add(phuongTienNhiemVu);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public PhuongTienNhiemVu delete(PhuongTienNhiemVu phuongTienNhiemVu) {
        return null;
    }
}
