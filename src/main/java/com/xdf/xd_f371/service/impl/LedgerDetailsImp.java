package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.repo.LoaiXangDauRepo;
import com.xdf.xd_f371.service.LedgerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class LedgerDetailsImp implements LedgerDetailsService {
    @Autowired
    private LoaiXangDauRepo loaiXangDauRepo;

    @Override
    public QuantityByTTDTO selectQuantityNguonnx(int group_id, String loaiphieu, int tructhuoc_id, int loaixdId) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "SELECT title_id,tructhuoc.name as ttname,sum(thuc_xuat) FROM ledger_details \n" +
                "join nguonnx_title on ledger_details.export_unit_id=nguonnx_title.nguonnx_id\n" +
                "join tructhuoc on tructhuoc.id=nguonnx_title.title_id\n" +
                "where loai_phieu=? and group_id=? and nguonnx_title.title_id=? and loaixd_id=?\n" +
                "group by title_id, ttname";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, loaiphieu);
            preparedStatement.setInt(2, group_id);
            preparedStatement.setInt(3, tructhuoc_id);
            preparedStatement.setInt(4, loaixdId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int titleID = resultSet.getInt("title_id");
                String ttname = resultSet.getString("ttname");
                int sum = resultSet.getInt("sum");
                return new QuantityByTTDTO(titleID,ttname,sum);
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
    public QuantityByTTDTO selectQuantityNguonnxImport(int group_id, String loaiphieu, int tructhuoc_id, int loaixdId) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "SELECT title_id,tructhuoc.name as ttname,sum(thuc_xuat) FROM ledger_details \n" +
                "join nguonnx_title on ledger_details.import_unit_id=nguonnx_title.nguonnx_id\n" +
                "join tructhuoc on tructhuoc.id=nguonnx_title.title_id\n" +
                "where loai_phieu=? and group_id=? and nguonnx_title.title_id=? and loaixd_id=?\n" +
                "group by title_id, ttname";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, loaiphieu);
            preparedStatement.setInt(2, group_id);
            preparedStatement.setInt(3, tructhuoc_id);
            preparedStatement.setInt(4, loaixdId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int titleID = resultSet.getInt("title_id");
                String ttname = resultSet.getString("ttname");
                int sum = resultSet.getInt("sum");
                return new QuantityByTTDTO(titleID,ttname,sum);
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
