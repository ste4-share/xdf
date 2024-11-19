package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.TructhuocLoaiphieuDTO;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.TrucThuocService;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrucThuocImp implements TrucThuocService {
    @Override
    public List<TrucThuoc> getAll() {
        QDatabase.getConnectionDB();
        List<TrucThuoc> result = new ArrayList<>();
        String sql = "select * from tructhuoc";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");


                TrucThuoc trucThuoc = new TrucThuoc();
                trucThuoc.setId(id);
                trucThuoc.setName(name);
                trucThuoc.setType(type);

                result.add(trucThuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<TrucThuoc> getAllByType(String t) {
//        QDatabase.getConnectionDB();
//        List<TrucThuoc> result = new ArrayList<>();
//        String sql = "select * from tructhuoc where loai_phieu=?";
//        try {
//            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
//            statement.setString(1,t);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String type = resultSet.getString("type");
//
//
//                TrucThuoc trucThuoc = new TrucThuoc();
//                trucThuoc.setId(id);
//                trucThuoc.setName(name);
//                trucThuoc.setType(type);
//
//                result.add(trucThuoc);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
        return new ArrayList<>();
    }

    @Override
    public TrucThuoc createNew(TrucThuoc trucThuoc) {
        QDatabase.getConnectionDB();
        String sql = "insert into tructhuoc(name, type) values(?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, trucThuoc.getName());
            statement.setString(2, trucThuoc.getType());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return trucThuoc;
    }

    @Override
    public TrucThuoc udpate_n(TrucThuoc trucThuoc) {
        return null;
    }

    @Override
    public TrucThuoc delById(TrucThuoc trucThuoc) {
        return null;
    }

    @Override
    public TrucThuoc findById(int id) {
        QDatabase.getConnectionDB();
        String sql = "select * from tructhuoc where id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, id);
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

    @Override
    public List<TrucThuoc> findByName(String name) {
        QDatabase.getConnectionDB();
        List<TrucThuoc> result = new ArrayList<>();
        String sql = "select * from tructhuoc where name=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name2 = resultSet.getString("name");
                String type = resultSet.getString("type");


                TrucThuoc trucThuoc = new TrucThuoc();
                trucThuoc.setId(id);
                trucThuoc.setName(name2);
                trucThuoc.setType(type);

                result.add(trucThuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<TrucThuoc> findByType(String name) {
        QDatabase.getConnectionDB();
        List<TrucThuoc> result = new ArrayList<>();
        String sql = "select * from tructhuoc where type=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name2 = resultSet.getString("name");
                String type = resultSet.getString("type");


                TrucThuoc trucThuoc = new TrucThuoc();
                trucThuoc.setId(id);
                trucThuoc.setName(name2);
                trucThuoc.setType(type);
                result.add(trucThuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<TructhuocLoaiphieuDTO> getAllTTLP() {
        QDatabase.getConnectionDB();
        List<TructhuocLoaiphieuDTO> result = new ArrayList<>();
        String sql = "SELECT tructhuoc_loaiphieu.id, tructhuoc_id, loaiphieu_id, loai_phieu.type, tructhuoc.name FROM tructhuoc_loaiphieu join loai_phieu on tructhuoc_loaiphieu.loaiphieu_id=loai_phieu.id join tructhuoc on tructhuoc_loaiphieu.tructhuoc_id=tructhuoc.id;";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int tructhuoc_id = resultSet.getInt("tructhuoc_id");
                int loaiphieu_id = resultSet.getInt("loaiphieu_id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");


                TructhuocLoaiphieuDTO trucThuoc = new TructhuocLoaiphieuDTO();
                trucThuoc.setId(id);
                trucThuoc.setLoaiphieu_id(loaiphieu_id);
                trucThuoc.setTructhuoc_id(tructhuoc_id);
                trucThuoc.setName(name);
                trucThuoc.setType(type);

                result.add(trucThuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
