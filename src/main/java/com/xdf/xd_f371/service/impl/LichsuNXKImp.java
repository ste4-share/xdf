package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.LichsuXNK;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.LichsuNXKService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LichsuNXKImp implements LichsuNXKService {
    @Override
    public List<LichsuXNK> getAll() {
        QDatabase.getConnectionDB();
        List<LichsuXNK> result = new ArrayList<>();

        String SQL_SELECT = "Select * from lichsuxnk order by timestamp DESC";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String tenxd = resultSet.getString("ten_xd");
                String mucgia = resultSet.getString("mucgia");
                String loai_phieu = resultSet.getString("loai_phieu");
                int tontruoc = resultSet.getInt("tontruoc");
                int soluong = resultSet.getInt("soluong");
                int tonsau = resultSet.getInt("tonsau");
                String createTime = resultSet.getString("createtime");

                LichsuXNK obj = new LichsuXNK();
                obj.setId(id);
                obj.setLoai_phieu(loai_phieu);
                obj.setTen_xd(tenxd);
                obj.setCreateTime(createTime);
                obj.setMucgia(mucgia);
                obj.setTonsau(tonsau);
                obj.setTontruoc(tontruoc);
                obj.setSoluong(soluong);
                result.add(obj);
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
    public LichsuXNK createNew(LichsuXNK lichsuXNK) {
        QDatabase.getConnectionDB();
        String sql = "insert into lichsuxnk(ten_xd, loai_phieu, tontruoc, soluong, tonsau,createtime, mucgia) values(?,?,?,?,?,?,?)";
        try {

            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, lichsuXNK.getTen_xd());
            statement.setString(2, lichsuXNK.getLoai_phieu());
            statement.setInt(3, lichsuXNK.getTontruoc());
            statement.setInt(4, lichsuXNK.getSoluong());
            statement.setInt(5, lichsuXNK.getTonsau());
            statement.setString(6, lichsuXNK.getCreateTime());
            statement.setString(7, lichsuXNK.getMucgia());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return lichsuXNK;
    }
}
