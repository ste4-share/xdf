package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.TrucThuocService;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TrucThuocImp implements TrucThuocService {
    @Override
    public TrucThuoc findByNguonnx(int nguonnxId, int groupId) {
        QDatabase.getConnectionDB();
            String sql = "select * from tructhuoc where tructhuoc.id in (select title_id from nguonnx_title where group_id=? and nguonnx_id=?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, groupId);
            statement.setInt(2, nguonnxId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                String name2 = resultSet.getString("name");
                String type = resultSet.getString("type");
                TrucThuoc trucThuoc = new TrucThuoc();
                trucThuoc.setId(id1);
                trucThuoc.setName(name2);
                trucThuoc.setType(type);
                return trucThuoc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
}
