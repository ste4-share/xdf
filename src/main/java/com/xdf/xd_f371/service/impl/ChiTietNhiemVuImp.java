package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.ChitietNhiemVu;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.ChiTietNhiemVuService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietNhiemVuImp implements ChiTietNhiemVuService {
    @Override
    public List<ChitietNhiemVu> getAll() {
        return List.of();
    }

    @Override
    public ChitietNhiemVu create(ChitietNhiemVu nhiemVu) {
        return null;
    }

    @Override
    public ChitietNhiemVu update(ChitietNhiemVu nhiemVu) {
        return null;
    }

    @Override
    public ChitietNhiemVu findById(int id) {
        return null;
    }

    @Override
    public List<ChitietNhiemVu> findByTenNv(int nv_id) {
        QDatabase.getConnectionDB();
        List<ChitietNhiemVu> result = new ArrayList<>();

        String SQL_SELECT = "Select * from chitiet_nhiemvu where nhiemvu_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, nv_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int nhiemvuId = resultSet.getInt("nhiemvu_id");
                String nhiemvu = resultSet.getString("nhiemvu");

                ChitietNhiemVu chitietNhiemVu = new ChitietNhiemVu();
                chitietNhiemVu.setId(id);
                chitietNhiemVu.setNhiemvu(nhiemvu);
                chitietNhiemVu.setNhiemvu_id(nhiemvuId);
                result.add(chitietNhiemVu);
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
}
