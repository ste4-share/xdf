package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.model.LoaiGB;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.LedgerDetailsService;
import com.xdf.xd_f371.service.LoaiXdService;
import org.postgresql.util.PGInterval;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LedgerDetailsImp implements LedgerDetailsService {
    private LoaiXdService loaiXdService = new LoaiXdImp();
    @Override
    public List<LedgerDetails> getAll() {
        QDatabase.getConnectionDB();
        List<LedgerDetails> result = new ArrayList<>();

        String SQL_SELECT = "Select * from ledger_details";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String dvi = resultSet.getString("dvi");
                String ngay = resultSet.getString("ngay");
                String maxd = resultSet.getString("ma_xd");
                String tenxd = resultSet.getString("ten_xd");
                String chungloai = resultSet.getString("chung_loai");
                String loai_phieu = resultSet.getString("loai_phieu");
                String so = resultSet.getString("so");
                String theo_lenh_so = resultSet.getString("theo_lenh_so");
                String nhiem_vu = resultSet.getString("nhiem_vu");
                String nguoi_nhan_hang = resultSet.getString("nguoi_nhan_hang");
                String soXe = resultSet.getString("so_xe");
                String chat_luong = resultSet.getString("chat_luong");
                int phaiXuat = resultSet.getInt("phai_xuat");
                int nhiet_do_tt = resultSet.getInt("nhiet_do_tt");
                int tyTrong = resultSet.getInt("ty_trong");
                int heSoVcf = resultSet.getInt("he_so_vcf");
                int thucXuat = resultSet.getInt("thuc_xuat");
                int donGia = resultSet.getInt("don_gia");
                Long thanhTien = resultSet.getLong("thanh_tien");
                int so_km = resultSet.getInt("so_km");
                int soGio = resultSet.getInt("so_gio");
                String dvvc = resultSet.getString("dvvc");
                int loaixd_id = resultSet.getInt("loaixd_id");
                String denngay = resultSet.getString("denngay");
                int nvu_tcn_id = resultSet.getInt("nvu_tcn_id");
                int nvu_tructhuoc = resultSet.getInt("nvu_tructhuoc");
                int quarter_id = resultSet.getInt("quarter_id");
                int phuongtien_id = resultSet.getInt("phuongtien_id");
                int nhiemvu_id = resultSet.getInt("nhiemvu_id");
                int phuongtien_nvu_id = resultSet.getInt("phuongtien_nvu_id");
                int so_phut = resultSet.getInt("so_phut");
                int tonkhotong_id = resultSet.getInt("tonkhotong_id");
                int tonkho_id = resultSet.getInt("tonkho_id");
                int import_unit_id = resultSet.getInt("import_unit_id");
                int export_unit_id = resultSet.getInt("export_unit_id");
                String loaigiobay = resultSet.getString("loaigiobay");
                int thucXuatTk = resultSet.getInt("thuc_xuat_tk");
                int soluong = resultSet.getInt("so_luong");

                LedgerDetails obj = new LedgerDetails();
                obj.setId(id);
                obj.setDvi(dvi);
                obj.setDvvc(dvvc);
                obj.setNgay(ngay);
                obj.setTen_xd(tenxd);
                obj.setMa_xd(maxd);
                obj.setChung_loai(chungloai);
                obj.setLoai_phieu(loai_phieu);
                obj.setSo(so);
                obj.setTheo_lenh_so(theo_lenh_so);
                obj.setNhiem_vu(nhiem_vu);
                obj.setNguoi_nhan_hang(nguoi_nhan_hang);
                obj.setSo_xe(soXe);
                obj.setChat_luong(chat_luong);
                obj.setPhai_xuat(phaiXuat);
                obj.setNhiet_do_tt(nhiet_do_tt);
                obj.setTy_trong(tyTrong);
                obj.setHe_so_vcf(heSoVcf);
                obj.setThuc_xuat(thucXuat);
                obj.setDon_gia(donGia);
                obj.setThanh_tien(thanhTien);
                obj.setSo_gio(soGio);
                obj.setSo_km(so_km);
                obj.setDvvc(dvvc);

                obj.setXd(loaiXdService.findLoaiXdByID_non(loaixd_id));
                obj.setDenngay(denngay);
                obj.setNvu_tructhuoc(nvu_tructhuoc);
                obj.setNvu_tcn_id(nvu_tcn_id);
                obj.setQuarter_id(quarter_id);
                obj.setPhuongtien_id(phuongtien_id);
                obj.setNhiemvu_id(nhiemvu_id);
                obj.setPhuongtien_nvu_id(phuongtien_nvu_id);
                obj.setSo_phut(so_phut);
                obj.setTonkho_id(tonkho_id);
                obj.setTonkhotong_id(tonkhotong_id);
                obj.setImport_unit_id(import_unit_id);
                obj.setExport_unit_id(export_unit_id);
                obj.setLoaigiobay(loaigiobay);
                obj.setThuc_xuat_tk(thucXuatTk);
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
    public LedgerDetails create(LedgerDetails ledgerDetails) {
        QDatabase.getConnectionDB();
        String sql = "INSERT INTO ledger_details (dvi, ngay, ma_xd, ten_xd, chung_loai, loai_phieu, so, theo_lenh_so, nhiem_vu, nguoi_nhan_hang, " +
                "so_xe, chat_luong, phai_xuat, nhiet_do_tt, ty_trong, he_so_vcf, thuc_xuat, don_gia, thanh_tien, so_km, so_gio, dvvc," +
                "loaixd_id, nguonnx_id, nguonnx_dvvc_id, denngay," +
                "nvu_tcn_id,nvu_tructhuoc,quarter_id,phuongtien_id,nhiemvu_id,phuongtien_nvu_id,so_phut,tonkhotong_id,tonkho_id,ledger_id, tcn_id,import_unit_id,export_unit_id,loaigiobay,thuc_xuat_tk,nhiemvu_hanmuc_id,so_luong,dur_text_md2,dur_text_tk2) " +
                "VALUES (?, ?,?, ?, ?,?, ?, ?, ?, ?,?,?,?,?, ?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(2, ledgerDetails.getNgay());
            statement.setString(3, ledgerDetails.getMa_xd());
            statement.setString(4, ledgerDetails.getTen_xd());
            statement.setString(5, ledgerDetails.getChung_loai());
            statement.setString(6, ledgerDetails.getLoai_phieu());
            statement.setString(7, ledgerDetails.getSo());
            statement.setString(8, ledgerDetails.getTheo_lenh_so());
            statement.setString(9, ledgerDetails.getNhiem_vu());
            statement.setString(10, ledgerDetails.getNguoi_nhan_hang());
            statement.setString(11, ledgerDetails.getSo_xe());
            statement.setString(12, ledgerDetails.getChat_luong());
            statement.setInt(13, ledgerDetails.getPhai_xuat());
            statement.setDouble(14, ledgerDetails.getNhiet_do_tt());
            statement.setDouble(15, ledgerDetails.getTy_trong());
            statement.setDouble(16, ledgerDetails.getHe_so_vcf());
            statement.setDouble(17, ledgerDetails.getThuc_xuat());
            statement.setDouble(18, ledgerDetails.getDon_gia());
            statement.setDouble(19, ledgerDetails.getThanh_tien());
            statement.setInt(20, ledgerDetails.getSo_km());
            statement.setInt(21, ledgerDetails.getSo_gio());
            if (ledgerDetails.getDvn_obj()==null){
                statement.setString(1, null);
                statement.setInt(24, 0);
            }else{
                statement.setString(1, ledgerDetails.getDvi());
                statement.setInt(24, ledgerDetails.getDvn_obj().getId());
            }
            statement.setString(22, ledgerDetails.getDvvc());
            statement.setInt(23, ledgerDetails.getXd().getId());
            statement.setInt(25, ledgerDetails.getDvvc_obj().getId());
            statement.setString(26, ledgerDetails.getDenngay());
            statement.setInt(27, ledgerDetails.getNvu_tcn_id());
            statement.setInt(28, ledgerDetails.getNvu_tructhuoc());
            statement.setInt(29, ledgerDetails.getQuarter_id());
            statement.setInt(30, ledgerDetails.getPhuongtien_id());
            statement.setInt(31, ledgerDetails.getNhiemvu_id());
            statement.setInt(32, ledgerDetails.getPhuongtien_nvu_id());
            statement.setInt(33, ledgerDetails.getSo_phut());
            statement.setInt(34, ledgerDetails.getTonkhotong_id());
            statement.setInt(35, ledgerDetails.getTonkho_id());
            statement.setInt(36, ledgerDetails.getLedger_id());
            statement.setInt(37, ledgerDetails.getTcn_id());
            statement.setInt(38, ledgerDetails.getImport_unit_id());
            statement.setInt(39, ledgerDetails.getExport_unit_id());
            statement.setString(40, ledgerDetails.getLoaigiobay());
            statement.setInt(41, ledgerDetails.getThuc_xuat_tk());
            statement.setInt(42, ledgerDetails.getNhiemvu_hanmuc_id());
            statement.setInt(43, ledgerDetails.getSoluong());
            statement.setObject(44, ledgerDetails.getDur_text_md2());
            statement.setObject(45, ledgerDetails.getDur_text_tk2());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ledgerDetails;
    }

    @Override
    public LedgerDetails update(LedgerDetails ledgerDetails) {
        return null;
    }
    @Override
    public boolean delete_f(String so) {
        return false;
    }
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
    public List<LedgerDetails> getChiTietSoCai(String so1) {
        QDatabase.getConnectionDB();
        List<LedgerDetails> result = new ArrayList<>();

        String SQL_SELECT = "select * from ledger_details where so=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, so1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String dvi = resultSet.getString("dvi");
                String ngay = resultSet.getString("ngay");
                String maxd = resultSet.getString("ma_xd");
                String tenxd = resultSet.getString("ten_xd");
                String chungloai = resultSet.getString("chung_loai");
                String loai_phieu = resultSet.getString("loai_phieu");
                String so = resultSet.getString("so");
                String theo_lenh_so = resultSet.getString("theo_lenh_so");
                String nhiem_vu = resultSet.getString("nhiem_vu");
                String nguoi_nhan_hang = resultSet.getString("nguoi_nhan_hang");
                String soXe = resultSet.getString("so_xe");
                String chat_luong = resultSet.getString("chat_luong");
                int phaiXuat = resultSet.getInt("phai_xuat");
                int nhiet_do_tt = resultSet.getInt("nhiet_do_tt");
                int tyTrong = resultSet.getInt("ty_trong");
                int heSoVcf = resultSet.getInt("he_so_vcf");
                int thucXuat = resultSet.getInt("thuc_xuat");
                int donGia = resultSet.getInt("don_gia");
                Long thanhTien = resultSet.getLong("thanh_tien");
                int so_km = resultSet.getInt("so_km");
                int soGio = resultSet.getInt("so_gio");
                String dvvc = resultSet.getString("dvvc");
                int loaixd_id = resultSet.getInt("loaixd_id");
                String denngay = resultSet.getString("denngay");
                int nvu_tcn_id = resultSet.getInt("nvu_tcn_id");
                int nvu_tructhuoc = resultSet.getInt("nvu_tructhuoc");
                int quarter_id = resultSet.getInt("quarter_id");
                int phuongtien_id = resultSet.getInt("phuongtien_id");
                int nhiemvu_id = resultSet.getInt("nhiemvu_id");
                int phuongtien_nvu_id = resultSet.getInt("phuongtien_nvu_id");
                int so_phut = resultSet.getInt("so_phut");
                int tonkhotong_id = resultSet.getInt("tonkhotong_id");
                int tonkho_id = resultSet.getInt("tonkho_id");
                int import_unit_id = resultSet.getInt("import_unit_id");
                int export_unit_id = resultSet.getInt("export_unit_id");
                String loaigiobay = resultSet.getString("loaigiobay");
                int thucXuatTk = resultSet.getInt("thuc_xuat_tk");
                PGInterval durTextMD2 = (PGInterval) resultSet.getObject("dur_text_md2");
                PGInterval durTextTK2 = (PGInterval) resultSet.getObject("dur_text_tk2");
                int soluong = resultSet.getInt("so_luong");

                LedgerDetails obj = new LedgerDetails();
                obj.setId(id);
                obj.setDvi(dvi);
                obj.setDvvc(dvvc);
                obj.setNgay(ngay);
                obj.setTen_xd(tenxd);
                obj.setMa_xd(maxd);
                obj.setChung_loai(chungloai);
                obj.setLoai_phieu(loai_phieu);
                obj.setSo(so);
                obj.setTheo_lenh_so(theo_lenh_so);
                obj.setNhiem_vu(nhiem_vu);
                obj.setNguoi_nhan_hang(nguoi_nhan_hang);
                obj.setSo_xe(soXe);
                obj.setChat_luong(chat_luong);
                obj.setPhai_xuat(phaiXuat);
                obj.setNhiet_do_tt(nhiet_do_tt);
                obj.setTy_trong(tyTrong);
                obj.setHe_so_vcf(heSoVcf);
                obj.setThuc_xuat(thucXuat);
                obj.setDon_gia(donGia);
                obj.setThanh_tien(thanhTien);
                obj.setSo_gio(soGio);
                obj.setSo_km(so_km);
                obj.setDvvc(dvvc);
                obj.setXd(loaiXdService.findLoaiXdByID_non(loaixd_id));
                obj.setDenngay(denngay);
                obj.setNvu_tructhuoc(nvu_tructhuoc);
                obj.setNvu_tcn_id(nvu_tcn_id);
                obj.setQuarter_id(quarter_id);
                obj.setPhuongtien_id(phuongtien_id);
                obj.setNhiemvu_id(nhiemvu_id);
                obj.setPhuongtien_nvu_id(phuongtien_nvu_id);
                obj.setSo_phut(so_phut);
                obj.setTonkho_id(tonkho_id);
                obj.setTonkhotong_id(tonkhotong_id);
                obj.setImport_unit_id(import_unit_id);
                obj.setExport_unit_id(export_unit_id);
                obj.setLoaigiobay(loaigiobay);
                obj.setThuc_xuat_tk(thucXuatTk);
                obj.setDur_text_md2(durTextMD2);
                obj.setDur_text_tk2(durTextTK2);
                obj.setSoluong(soluong);
                result.add(obj);
            }
        } catch (SQLException e) {
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

    @Override
    public GioBay getSumofWorkingTime(int pt_id, int quarter_id,String lgb) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "SELECT sum(so_gio) as h_ground,sum(so_phut) as m_ground \n" +
                "FROM public.ledger_details\n" +
                "WHERE loai_phieu='XUAT' and phuongtien_id=? and quarter_id=? and loaigiobay=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, pt_id);
            preparedStatement.setInt(2, quarter_id);
            preparedStatement.setString(3, lgb);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int h_ground = resultSet.getInt("h_ground");
                int m_ground = resultSet.getInt("m_ground");
                return new GioBay(h_ground, m_ground);
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
    public Map<String, Integer> getSumofconsumption(int pt_id, int quarterId) {
        Map<String, Integer> map = new HashMap<>();
        QDatabase.getConnectionDB();
        String SQL_SELECT = "SELECT sum(thuc_xuat) as sumoftk,(SELECT sum(thuc_xuat) FROM public.ledger_details WHERE loai_phieu='XUAT' and phuongtien_id=? and quarter_id=? and loaigiobay=?)\n" +
                "FROM public.ledger_details\n" +
                "WHERE loai_phieu='XUAT' and phuongtien_id=? and quarter_id=? and loaigiobay=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, pt_id);
            preparedStatement.setInt(2, quarterId);
            preparedStatement.setString(3, LoaiGB.MD.getName());
            preparedStatement.setInt(4, pt_id);
            preparedStatement.setInt(5, quarterId);
            preparedStatement.setString(6, LoaiGB.TK.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int sumoftk = resultSet.getInt("sumoftk");
                int sumofmd = resultSet.getInt("sum");
                map.put(LoaiGB.TK.getName(), sumoftk);
                map.put(LoaiGB.MD.getName(), sumofmd);
                return map;
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
