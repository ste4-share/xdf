package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.ChitieuDTO;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.PhuongTienService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PhuongTienImp implements PhuongTienService {


    @Override
    public int createNewNorm(DinhMuc dinhMucDto) {
        QDatabase.getConnectionDB();
        String sql = "insert into dinhmuc(dm_tk_gio, dm_md_gio, dm_xm_km, dm_xm_gio, phuongtien_id, quarter_id) values(?,?,?,?,?,?) on conflict (phuongtien_id,quarter_id) do update set dm_tk_gio=?, dm_md_gio=?, dm_xm_km=?,dm_xm_gio=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, dinhMucDto.getDm_tk_gio());
            statement.setInt(2, dinhMucDto.getDm_md_gio());
            statement.setInt(3, dinhMucDto.getDm_xm_km());
            statement.setInt(4, dinhMucDto.getDm_xm_gio());
            statement.setInt(5, dinhMucDto.getPhuongtien_id());
            statement.setInt(6, dinhMucDto.getQuarter_id());
            statement.setInt(7, dinhMucDto.getDm_tk_gio());
            statement.setInt(8, dinhMucDto.getDm_md_gio());
            statement.setInt(9, dinhMucDto.getDm_xm_km());
            statement.setInt(10, dinhMucDto.getDm_xm_gio());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<NguonNx> getIdNguonnx() {
        QDatabase.getConnectionDB();
        List<NguonNx> result = new ArrayList<>();
        String sql = "select * from nguon_nx where id in (SELECT nguonnx_id from phuongtien group by nguonnx_id)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ten = resultSet.getString("ten");
                String createtime = resultSet.getString("createtime");
                NguonNx obj = new NguonNx();
                obj.setId(id);
                obj.setCreatetime(createtime);
                obj.setTen(ten);
                result.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> getTypeName() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String sql = "SELECT loai_phuongtien.type FROM loai_phuongtien group by loai_phuongtien.type";

        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int createNew(PhuongTien phuongTien) {
        QDatabase.getConnectionDB();
        String sql = "insert into phuongtien(name, quantity, status,nguonnx_id,loaiphuongtien_id) values(?,?,?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, phuongTien.getName());
            statement.setInt(2, phuongTien.getQuantity());
            statement.setString(3, phuongTien.getStatus());
            statement.setInt(4, phuongTien.getNguonnx_id());
            statement.setInt(5, phuongTien.getLoaiphuongtien_id());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public PhuongTien findPhuongTienById(int id1) {
        QDatabase.getConnectionDB();
        PhuongTien phuongTien = new PhuongTien();
        String sql = "select * from phuongtien where id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, id1);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                int nguonnxId = resultSet.getInt("nguonnx_id");
                int loaiphuongtienId = resultSet.getInt("loaiphuongtien_id");
                String status = resultSet.getString("status");


                phuongTien.setId(id);
                phuongTien.setName(name);
                phuongTien.setNguonnx_id(nguonnxId);
                phuongTien.setQuantity(quantity);
                phuongTien.setStatus(status);
                phuongTien.setLoaiphuongtien_id(loaiphuongtienId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return phuongTien;
    }

    @Override
    public List<PhuongTien> findPhuongTienByType(String type1) {
        QDatabase.getConnectionDB();
        List<PhuongTien> result = new ArrayList<>();
        String sql = "select * from phuongtien \n" +
                "join loai_phuongtien on phuongtien.loaiphuongtien_id=loai_phuongtien.id\n" +
                "join dinhmuc on phuongtien.id=dinhmuc.phuongtien_id\n" +
                "where loai_phuongtien.type=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1,  type1 );
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                int nguonnxId = resultSet.getInt("nguonnx_id");
                int loaiphuongtienId = resultSet.getInt("loaiphuongtien_id");
                String status = resultSet.getString("status");

                PhuongTien phuongTien = new PhuongTien();
                phuongTien.setId(id);
                phuongTien.setName(name);
                phuongTien.setNguonnx_id(nguonnxId);
                phuongTien.setQuantity(quantity);
                phuongTien.setStatus(status);
                phuongTien.setLoaiphuongtien_id(loaiphuongtienId);
                result.add(phuongTien);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
}
