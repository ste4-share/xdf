package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.NguonNx_tructhuoc;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.NguonNx_tructhuocService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NguonNx_tructhuocImp implements NguonNx_tructhuocService {
    @Override
    public List<TrucThuoc> findAllTrucThuocByNGuonNxID(int nguonnx_id) {
        QDatabase.getConnectionDB();
        List<TrucThuoc> result = new ArrayList<>();

        String SQL_SELECT = "SELECT tructhuoc.id as ttid,name  FROM nguonnx_tructhuoc inner join nguon_nx on nguonnx_tructhuoc.nguonnx_id=nguon_nx.id inner join tructhuoc on nguonnx_tructhuoc.tructhuoc_id=tructhuoc.id where nguonnx_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, nguonnx_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int tructhuocId = resultSet.getInt("ttid");
                String name = resultSet.getString("name");
                
                TrucThuoc nguonNxTructhuoc = new TrucThuoc();
                nguonNxTructhuoc.setId(tructhuocId);
                nguonNxTructhuoc.setName(name);
                result.add(nguonNxTructhuoc);
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
    public NguonNx_tructhuoc findNguonnx_tructhuocByNnx_lp(int nguonnx_id, int loaiPhieu) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "select * from nguonnx_tructhuoc where nguonnx_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, nguonnx_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int tructhuocId = resultSet.getInt("tructhuoc_id");
                int nguonnxId = resultSet.getInt("nguonnx_id");

                NguonNx_tructhuoc nguonNxTructhuoc = new NguonNx_tructhuoc();
                nguonNxTructhuoc.setId(id);
                nguonNxTructhuoc.setTructhuoc_id(tructhuocId);
                nguonNxTructhuoc.setNguonnx_id(nguonnxId);
                return nguonNxTructhuoc;
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
    public NguonNx_tructhuoc createNew(NguonNx_tructhuoc nguonNxTructhuoc) {
        QDatabase.getConnectionDB();
        String sql = "insert into nguonnx_tructhuoc(nguonnx_id, tructhuoc_id) values(?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, nguonNxTructhuoc.getNguonnx_id());
            statement.setInt(2, nguonNxTructhuoc.getTructhuoc_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return nguonNxTructhuoc;
    }

    @Override
    public NguonNx_tructhuoc update(NguonNx_tructhuoc nguonNxTructhuoc) {
        return null;
    }

    @Override
    public int delete(NguonNx_tructhuoc nguonNxTructhuoc) {
        QDatabase.getConnectionDB();
        String sql = "DELETE FROM nguonnx_tructhuoc WHERE id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, nguonNxTructhuoc.getId());
            return statement.executeUpdate();
            // Step 3: Execute the DELETE query
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
