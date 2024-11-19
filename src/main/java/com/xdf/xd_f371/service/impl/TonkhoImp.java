package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.TonKho;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.TonKhoService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TonkhoImp implements TonKhoService {


    @Override
    public List<TonKho> getAll() {
        QDatabase.getConnectionDB();
        List<TonKho> result = new ArrayList<>();


        String SQL_SELECT = "Select * from tonkho";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String loaiXd = resultSet.getString("loai_xd");
                int soluong = resultSet.getInt("soluong");
                int mucgia = resultSet.getInt("mucgia");
                String createtime = resultSet.getString("createtime");
                String status = resultSet.getString("status");
                int quarterId = resultSet.getInt("quarter_id");
                int loaixdId = resultSet.getInt("loaixd_id");
                int mucgiaId = resultSet.getInt("mucgia_id");
                TonKho obj = new TonKho();
                obj.setId(id);
                obj.setLoai_xd(loaiXd);
                obj.setCreatetime(createtime);
                obj.setSoluong(soluong);
                obj.setStatus(status);
                obj.setMucgia(mucgia);
                obj.setQuarter_id(quarterId);
                obj.setLoaixd_id(loaixdId);
                obj.setMucgia_id(mucgiaId);
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
    public TonKho create(TonKho tonKho) {
        QDatabase.getConnectionDB();
        String sql = "insert into tonkho(loai_xd, soluong,mucgia,createtime,status,quarter_id, loaixd_id, mucgia_id) values (?,?, ?, ?,?,?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, tonKho.getLoai_xd());
            statement.setInt(2, tonKho.getSoluong());
            statement.setInt(3, tonKho.getMucgia());
            statement.setString(4, tonKho.getCreatetime());
            statement.setString(5, tonKho.getStatus());
            statement.setInt(6, tonKho.getQuarter_id());
            statement.setInt(7, tonKho.getLoaixd_id());
            statement.setInt(8, tonKho.getMucgia_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return tonKho;
    }

    @Override
    public TonKho update(TonKho tonKho) {
        QDatabase.getConnectionDB();
        String sql = "update tonkho set soluong=? where mucgia_id=? and loaixd_id=? and quarter_id=?";
        try {

            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, tonKho.getSoluong());
            statement.setInt(2, tonKho.getMucgia_id());
            statement.setInt(3, tonKho.getLoaixd_id());
            statement.setInt(4, tonKho.getQuarter_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return tonKho;
    }

    @Override
    public void delete(TonKho tonKho) {

    }

    @Override
    public List<TonKho> findByLoaiXD(int loaixd1_id, int mucgia1_id) {
        QDatabase.getConnectionDB();
        List<TonKho> result = new ArrayList<>();


        String SQL_SELECT = "Select * from tonkho where loaixd_id=? and mucgia_id=? and soluong>=0";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, loaixd1_id);
            preparedStatement.setInt(2, mucgia1_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String loaiXd = resultSet.getString("loai_xd");
                int soluong = resultSet.getInt("soluong");
                int mucgia = resultSet.getInt("mucgia");
                String createtime = resultSet.getString("createtime");
                String status = resultSet.getString("status");
                int quarterId = resultSet.getInt("quarter_id");
                int loaixdId = resultSet.getInt("loaixd_id");
                int mucgiaId = resultSet.getInt("mucgia_id");
                TonKho obj = new TonKho();
                obj.setId(id);
                obj.setLoai_xd(loaiXd);
                obj.setCreatetime(createtime);
                obj.setSoluong(soluong);
                obj.setStatus(status);
                obj.setMucgia(mucgia);
                obj.setQuarter_id(quarterId);
                obj.setLoaixd_id(loaixdId);
                obj.setMucgia_id(mucgiaId);
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
    public TonKho findBy3Id(int quarter_id, int loaixd_id, int mucgia_id) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from tonkho where loaixd_id=? and mucgia_id=? and quarter_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, loaixd_id);
            preparedStatement.setInt(2, mucgia_id);
            preparedStatement.setInt(3, quarter_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String loaiXd = resultSet.getString("loai_xd");
                int soluong = resultSet.getInt("soluong");
                int mucgia1 = resultSet.getInt("mucgia");
                String createtime = resultSet.getString("createtime");
                String status = resultSet.getString("status");
                int quarterId = resultSet.getInt("quarter_id");
                int loaixdId = resultSet.getInt("loaixd_id");
                int mucgiaId = resultSet.getInt("mucgia_id");
                TonKho obj = new TonKho();
                obj.setId(id);
                obj.setLoai_xd(loaiXd);
                obj.setCreatetime(createtime);
                obj.setSoluong(soluong);
                obj.setStatus(status);
                obj.setMucgia(mucgia1);
                obj.setQuarter_id(quarterId);
                obj.setLoaixd_id(loaixdId);
                obj.setMucgia_id(mucgiaId);
                return obj;
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
    public List<Inventory> getAllInventory(int quarter_id) {
        QDatabase.getConnectionDB();
        List<Inventory> result = new ArrayList<>();


        String SQL_SELECT = "Select * from inventory where quarter_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, quarter_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int petro_id = resultSet.getInt("petro_id");
                int quarterId = resultSet.getInt("quarter_id");
                int tdk_nvdx = resultSet.getInt("tdk_nvdx");
                int tdk_sscd = resultSet.getInt("tdk_sscd");
                int tcK_nvdx = resultSet.getInt("tcK_nvdx");
                int tck_sscd = resultSet.getInt("tck_sscd");
                int pre_nvdx = resultSet.getInt("pre_nvdx");
                int pre_sscd = resultSet.getInt("pre_sscd");
                int import_total = resultSet.getInt("import_total");
                int export_total = resultSet.getInt("export_total");
                int total = resultSet.getInt("total");
                String status1 = resultSet.getString("status");
                Inventory inventory = new Inventory(id,petro_id, quarterId, tdk_nvdx, tdk_sscd, tcK_nvdx, tck_sscd, total, status1,import_total, export_total,pre_nvdx,pre_sscd);
                result.add(inventory);
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
    public List<Inventory> getAllInventoryWithJoin(int quarter_id) {
        QDatabase.getConnectionDB();
        List<Inventory> result = new ArrayList<>();


        String SQL_SELECT = "select * from inventory \n" +
                "left join loaixd2 on inventory.petro_id=loaixd2.id\n" +
                "where inventory.quarter_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, quarter_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int petro_id = resultSet.getInt("petro_id");
                int quarterId = resultSet.getInt("quarter_id");
                int tdk_nvdx = resultSet.getInt("tdk_nvdx");
                int tdk_sscd = resultSet.getInt("tdk_sscd");
                int tcK_nvdx = resultSet.getInt("tcK_nvdx");
                int tck_sscd = resultSet.getInt("tck_sscd");
                int pre_nvdx = resultSet.getInt("pre_nvdx");
                int pre_sscd = resultSet.getInt("pre_sscd");
                int import_total = resultSet.getInt("import_total");
                int export_total = resultSet.getInt("export_total");
                int total = resultSet.getInt("total");
                String status1 = resultSet.getString("status");

                Inventory inventory = new Inventory(id,petro_id, quarterId, tdk_nvdx, tdk_sscd, tcK_nvdx, tck_sscd, total, status1  , import_total, export_total, pre_nvdx, pre_sscd);
                result.add(inventory);
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
    public Inventory createNew(Inventory tonKho) {
        QDatabase.getConnectionDB();
        String sql = "insert into inventory(tdk_sscd, tdk_nvdx,total,status,tck_sscd,quarter_id, tck_nvdx, petro_id, import_total, export_total,pre_nvdx, pre_sscd) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, tonKho.getTdk_sscd());
            statement.setInt(2, tonKho.getTdk_nvdx());
            statement.setInt(3, tonKho.getTotal());
            statement.setString(4, tonKho.getStatus());
            statement.setInt(5, tonKho.getTck_sscd());
            statement.setInt(6, tonKho.getQuarter_id());
            statement.setInt(7, tonKho.getTcK_nvdx());
            statement.setInt(8, tonKho.getPetro_id());
            statement.setInt(9, tonKho.getImport_total());
            statement.setInt(10, tonKho.getExport_total());
            statement.setInt(11, tonKho.getPre_nvdx());
            statement.setInt(12, tonKho.getPre_sscd());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return tonKho;
    }

    @Override
    public Inventory updateNew(Inventory tonKho) {
        QDatabase.getConnectionDB();
        String sql = "update inventory set tdk_sscd=?, tdk_nvdx=?,total=?,status=?,tck_sscd=?, tck_nvdx=?, import_total=?, export_total=? where petro_id=? and quarter_id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setLong(1, tonKho.getTdk_sscd());
            statement.setLong(2, tonKho.getTdk_nvdx());
            statement.setLong(3, tonKho.getTotal());
            statement.setString(4, tonKho.getStatus());
            statement.setLong(5, tonKho.getTck_sscd());
            statement.setLong(6, tonKho.getTcK_nvdx());
            statement.setInt(7, tonKho.getImport_total());
            statement.setInt(8, tonKho.getExport_total());
            statement.setInt(9, tonKho.getPetro_id());
            statement.setInt(10, tonKho.getQuarter_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return tonKho;
    }

    @Override
    public Inventory findByUniqueId(int petroleum_id, int quarter_id) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from inventory where petro_id=? and quarter_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1,petroleum_id);
            preparedStatement.setInt(2,quarter_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int petro_id = resultSet.getInt("petro_id");
                int quarterId = resultSet.getInt("quarter_id");
                int tdk_nvdx = resultSet.getInt("tdk_nvdx");
                int tdk_sscd = resultSet.getInt("tdk_sscd");
                int tcK_nvdx = resultSet.getInt("tck_nvdx");
                int tck_sscd = resultSet.getInt("tck_sscd");
                int pre_nvdx = resultSet.getInt("pre_nvdx");
                int pre_sscd = resultSet.getInt("pre_sscd");
                int import_total = resultSet.getInt("import_total");
                int export_total = resultSet.getInt("export_total");
                int total = resultSet.getInt("total");
                String status1 = resultSet.getString("status");
                return new Inventory(id,petro_id, quarterId, tdk_nvdx, tdk_sscd, tcK_nvdx, tck_sscd, total, status1, import_total, export_total,pre_nvdx, pre_sscd);
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
