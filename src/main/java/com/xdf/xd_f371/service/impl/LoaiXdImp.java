package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.LoaiXangDau;
import com.xdf.xd_f371.entity.PetroleumType;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.LoaiXdService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class LoaiXdImp implements LoaiXdService {
    @Override
    public List<LoaiXangDau> getAll() {
        QDatabase.getConnectionDB();
        List<LoaiXangDau> result = new ArrayList<>();


        String SQL_SELECT = "select * from loaixd2 order by ut2, loaixd2.id, ut";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                String chungloai = resultSet.getString("chungloai");
                Date createtime = resultSet.getDate("timestamp");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String r_type = resultSet.getString("r_type");
                LoaiXangDau obj = new LoaiXangDau();
                obj.setId(id);
                obj.setChungloai(chungloai);
                obj.setCreatetime(createtime);
                obj.setTenxd(tenxd);
                obj.setMaxd(maxd);
                obj.setType(type);
                obj.setStatus(status);
                obj.setRtype(r_type);
                result.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> getAllChungLoai() {

        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String SQL_SELECT = "select chungloai from loaixd2 group by chungloai;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String chungloai = resultSet.getString("chungloai");
                result.add(chungloai);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public LoaiXangDau create(LoaiXangDau importDto) {
        QDatabase.getConnectionDB();
        String sql = "insert into loaixd2(maxd, tenxd, chungloai, status) values(?,?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, importDto.getMaxd());
            statement.setString(2, importDto.getTenxd());
            statement.setString(3, importDto.getChungloai());
            statement.setString(4, importDto.getStatus());
            statement.executeUpdate();
            System.out.println("Record quarter updated.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return importDto;
    }

    @Override
    public PetroleumType create(PetroleumType petroleumType) {
        QDatabase.getConnectionDB();
        String sql = "insert into petroleum_type(name, type, r_type) values(?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, petroleumType.getName());
            statement.setString(2, petroleumType.getType());
            statement.setString(3, petroleumType.getR_type());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return petroleumType;
    }

    @Override
    public LoaiXangDau update(LoaiXangDau importDto) {
        return null;
    }

    @Override
    public boolean delete_f(String so) {
        return false;
    }

    @Override
    public LoaiXangDau findLoaiXdByID(String tenxd1) {
        QDatabase.getConnectionDB();
        LoaiXangDau result = new LoaiXangDau();

        String SQL_SELECT = "select * from loaixd2 where tenxd=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, tenxd1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                String chungloai = resultSet.getString("chungloai");
                Date createtime = resultSet.getDate("timestamp");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String rType = resultSet.getString("r_type");

                result.setId(id);
                result.setChungloai(chungloai);
                result.setCreatetime(createtime);
                result.setTenxd(tenxd);
                result.setMaxd(maxd);
                result.setStatus(status);
                result.setType(type);
                result.setRtype(rType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<LoaiXangDau> findLoaiXdByChungLoai(String loai) {
        QDatabase.getConnectionDB();
        List<LoaiXangDau> result = new ArrayList<>();

        String SQL_SELECT = "select * from loaixd2 where chungloai=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, loai);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                String chungloai = resultSet.getString("chungloai");
                Date createtime = resultSet.getDate("timestamp");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String rType = resultSet.getString("r_type");


                LoaiXangDau loaiXangDau = new LoaiXangDau();
                loaiXangDau.setId(id);
                loaiXangDau.setChungloai(chungloai);
                loaiXangDau.setCreatetime(createtime);
                loaiXangDau.setTenxd(tenxd);
                loaiXangDau.setMaxd(maxd);
                loaiXangDau.setStatus(status);
                loaiXangDau.setRtype(rType);
                loaiXangDau.setType(type);
                result.add(loaiXangDau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<LoaiXangDau> findLoaiXdByType(String loai) {
        QDatabase.getConnectionDB();
        List<LoaiXangDau> result = new ArrayList<>();
        String SQL_SELECT = "select * from loaixd2 where type=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, loai);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                String chungloai = resultSet.getString("chungloai");
                Date createtime = resultSet.getDate("timestamp");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String rType = resultSet.getString("r_type");

                LoaiXangDau loaiXangDau = new LoaiXangDau();
                loaiXangDau.setId(id);
                loaiXangDau.setChungloai(chungloai);
                loaiXangDau.setCreatetime(createtime);
                loaiXangDau.setTenxd(tenxd);
                loaiXangDau.setMaxd(maxd);
                loaiXangDau.setStatus(status);
                loaiXangDau.setType(type);
                loaiXangDau.setRtype(rType);
                result.add(loaiXangDau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<LoaiXangDau> findLoaiXdByChungloai(String chungloai1) {
        QDatabase.getConnectionDB();
        List<LoaiXangDau> result = new ArrayList<>();
        String SQL_SELECT = "select * from loaixd2 where chungloai=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, chungloai1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                String chungloai = resultSet.getString("chungloai");
                Date createtime = resultSet.getDate("timestamp");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String rType = resultSet.getString("r_type");

                LoaiXangDau loaiXangDau = new LoaiXangDau();
                loaiXangDau.setId(id);
                loaiXangDau.setChungloai(chungloai);
                loaiXangDau.setCreatetime(createtime);
                loaiXangDau.setTenxd(tenxd);
                loaiXangDau.setMaxd(maxd);
                loaiXangDau.setStatus(status);
                loaiXangDau.setType(type);
                loaiXangDau.setRtype(rType);
                result.add(loaiXangDau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> getAllType() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String SQL_SELECT = "SELECT chungloai FROM loaixd2 group by chungloai";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String chungloai = resultSet.getString("chungloai");
                result.add(chungloai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<LoaiXangDau> findLoaiXdByChungLoai_PER(String loai1) {
        QDatabase.getConnectionDB();
        List<LoaiXangDau> result = new ArrayList<>();

        String SQL_SELECT = "select * from loaixd2 where chungloai LIKE ?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, "%"+loai1+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                String chungloai = resultSet.getString("chungloai");
                Date createtime = resultSet.getDate("timestamp");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String rType = resultSet.getString("r_type");

                LoaiXangDau loaiXangDau = new LoaiXangDau();
                loaiXangDau.setId(id);
                loaiXangDau.setChungloai(chungloai);
                loaiXangDau.setCreatetime(createtime);
                loaiXangDau.setTenxd(tenxd);
                loaiXangDau.setMaxd(maxd);
                loaiXangDau.setStatus(status);
                loaiXangDau.setRtype(rType);
                loaiXangDau.setType(type);
                result.add(loaiXangDau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public LoaiXangDau findLoaiXdByID_non(int id1) {
        QDatabase.getConnectionDB();

        String SQL_SELECT = "select * from loaixd2 where id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                String chungloai = resultSet.getString("chungloai");
                Date createtime = resultSet.getDate("timestamp");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String rType = resultSet.getString("r_type");

                LoaiXangDau result = new LoaiXangDau();
                result.setId(id);
                result.setChungloai(chungloai);
                result.setCreatetime(createtime);
                result.setTenxd(tenxd);
                result.setMaxd(maxd);
                result.setStatus(status);
                result.setType(type);
                result.setRtype(rType);
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Map<String, String> getChungLoaiCount() {

        QDatabase.getConnectionDB();
        Map<String, String> map = new HashMap<>();

        String SQL_SELECT = "select chungloai, count(chungloai) as count_cl from loaixd2 group by chungloai;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String chungloai = resultSet.getString("chungloai");
                String countCl = resultSet.getString("count_cl");

                map.put(chungloai, countCl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return map;
    }
}
