package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Mucgia;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.MucgiaService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MucgiaImp implements MucgiaService {

    @Override
    public Mucgia updateMucGia(Mucgia mucgia) {
        QDatabase.getConnectionDB();

//        String sql = "begin transaction; update mucgia set amount=?, status=? where quarter_id=? and item_id=? and price=? and asssign_type_id=?;update inventory \n" +
//                "set pre_nvdx=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=2),\n" +
//                "pre_sscd=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=1),\n" +
//                "tck_nvdx=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=2),\n" +
//                "tck_sscd=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=1)\n" +
//                "where petro_id=? and quarter_id=?;commit;";
//        try {
//            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
//            statement.setInt(1, mucgia.getAmount());
//            statement.setString(2, mucgia.getStatus());
//            statement.setInt(3, mucgia.getQuarter_id());
//            statement.setInt(4, mucgia.getItem_id());
//            statement.setInt(5, mucgia.getPrice());
//            statement.setInt(6, mucgia.getAssign_type_id());
//
//            statement.setInt(7, mucgia.getQuarter_id());
//            statement.setInt(8, mucgia.getItem_id());
//            statement.setInt(9, mucgia.getQuarter_id());
//            statement.setInt(10, mucgia.getItem_id());
//            statement.setInt(11, mucgia.getQuarter_id());
//            statement.setInt(12, mucgia.getItem_id());
//            statement.setInt(13, mucgia.getQuarter_id());
//            statement.setInt(14, mucgia.getItem_id());
//            statement.setInt(15, mucgia.getItem_id());
//            statement.setInt(16, mucgia.getQuarter_id());
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
        return mucgia;
    }

    @Override
    public List<SpotDto> getAllSpots(int quart_id) {
        QDatabase.getConnectionDB();
        List<SpotDto> result = new ArrayList<>();
        String SQL_SELECT = "SELECT lxd2.id as lxd_id,lxd2.maxd, lxd2.tenxd, 'NVDX',sum(amount) as nvdx_total, 'SSCD', (select sum(amount) as total_sscd from mucgia join loaixd2 on mucgia.item_id=loaixd2.id where purpose like 'SSCD' and tenxd=lxd2.tenxd limit 1) as sscd_total FROM mucgia mg right join loaixd2 lxd2 on mg.item_id=lxd2.id where purpose like 'NVDX' and mg.quarter_id=? group by lxd_id, maxd, tenxd order by nvdx_total desc";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, quart_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                Integer nvdx_total = resultSet.getInt("nvdx_total");
                Integer sscd_total = resultSet.getInt("sscd_total");
                int lxd_id = resultSet.getInt("lxd_id");
                if (nvdx_total==null){
                    nvdx_total=0;
                }
                if (sscd_total==null){
                    sscd_total=0;
                }
                Integer total = sscd_total+ nvdx_total;

                SpotDto spotDto = new SpotDto();
                spotDto.setMaxd(maxd);
                spotDto.setLxd_id(lxd_id);
                spotDto.setTenxd(tenxd);
                spotDto.setNvdx_total(String.valueOf(nvdx_total));
                spotDto.setSscd_total(String.valueOf(sscd_total));
                spotDto.setTotal(String.valueOf(total));
                result.add(spotDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
}
