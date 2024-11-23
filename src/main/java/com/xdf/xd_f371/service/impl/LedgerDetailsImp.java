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
import java.util.ArrayList;
import java.util.List;
@Service
public class LedgerDetailsImp implements LedgerDetailsService {
    @Autowired
    private LoaiXangDauRepo loaiXangDauRepo;
    @Override
    public List<TTPhieuDto> getTTPhieu() {
        QDatabase.getConnectionDB();
        List<TTPhieuDto> result = new ArrayList<>();
        String SQL_SELECT = "select so,ngay, loai_phieu, dvi,dvvc,nhiem_vu,max(timestamp) as mx, string_agg(ten_xd, ', '),count(loai_phieu), SUM(so_luong*don_gia::BIGINT) from ledger_details group by so,ngay, loai_phieu, dvi, dvvc, nhiem_vu order by mx DESC;";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String so = resultSet.getString("so");
                String ngaytao = resultSet.getString("ngay");
                String loai_phieu = resultSet.getString("loai_phieu");
                String dvi = resultSet.getString("dvi");
                String dvvc = resultSet.getString("dvvc");
                String nhiem_vu = resultSet.getString("nhiem_vu");
                String hanghoa = resultSet.getString("string_agg");
                Long tong = resultSet.getLong("sum");
                TTPhieuDto obj = new TTPhieuDto();
                obj.setDvn(dvi);
                obj.setDvvc(dvvc);
                obj.setHang_hoa(hanghoa);
                obj.setLoai_phieu(loai_phieu);
                obj.setSo(so);
                obj.setTcn(nhiem_vu);
                obj.setNgaytao(ngaytao);
                obj.setTong(tong);
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
    public List<TTPhieuDto> getTTPhieu_ByLoaiPhieu(String loaiPhieu) {
        QDatabase.getConnectionDB();
        List<TTPhieuDto> result = new ArrayList<>();
        String SQL_SELECT = "select so,ngay, loai_phieu, dvi,dvvc,nhiem_vu,max(timestamp) as mx, string_agg(ten_xd, ', '),count(loai_phieu), SUM(so_luong*don_gia::BIGINT) from ledger_details where loai_phieu=? group by so,ngay, loai_phieu, dvi, dvvc, nhiem_vu order by mx DESC;";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, loaiPhieu);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String so = resultSet.getString("so");
                String ngaytao = resultSet.getString("ngay");
                String loai_phieu = resultSet.getString("loai_phieu");
                String dvi = resultSet.getString("dvi");
                String dvvc = resultSet.getString("dvvc");
                String nhiem_vu = resultSet.getString("nhiem_vu");
                String hanghoa = resultSet.getString("string_agg");
                Long tong = resultSet.getLong("sum");
                TTPhieuDto obj = new TTPhieuDto();
                obj.setDvn(dvi);
                obj.setDvvc(dvvc);
                obj.setHang_hoa(hanghoa);
                obj.setLoai_phieu(loai_phieu);
                obj.setSo(so);
                obj.setTcn(nhiem_vu);
                obj.setNgaytao(ngaytao);
                obj.setTong(tong);
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
