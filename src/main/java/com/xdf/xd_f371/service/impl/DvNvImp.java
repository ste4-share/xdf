package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.DviNvu;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.DvNvService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DvNvImp implements DvNvService {
    @Override
    public List<DviNvu> getAll() {
        QDatabase.getConnectionDB();
        List<DviNvu> result = new ArrayList<>();


        String SQL_SELECT = "Select * from dvi_nv";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int dv_id = resultSet.getInt("dv_id");
                int nv_id = resultSet.getInt("nv_id");
                String createtime = resultSet.getString("createtime");
                DviNvu dviNvu = new DviNvu();
                dviNvu.setId(id);
                dviNvu.setDv_id(dv_id);
                dviNvu.setNv_id(nv_id);
                dviNvu.setCreatetime(createtime);
                result.add(dviNvu);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public DviNvu create(DviNvu dviNvu) {
        return null;
    }

    @Override
    public DviNvu update(DviNvu dviNvu) {
        return null;
    }
}
