package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.ChitieuDTO;
import com.xdf.xd_f371.dto.NormDto;
import com.xdf.xd_f371.dto.StatusActive;
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
    public List<StatusActive> getAllStatus() {
        QDatabase.getConnectionDB();
        List<StatusActive> result = new ArrayList<>();
        String sql = "select * from activated_active";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String statusNAme = resultSet.getString("status_name");

                result.add(new StatusActive(id, statusNAme));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public StatusActive findStatusByName(String status) {
        QDatabase.getConnectionDB();
        String sql = "select * from activated_active where status_name=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String statusNAme = resultSet.getString("status_name");
                return new StatusActive(id, statusNAme);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<PhuongTien> getAll() {
        QDatabase.getConnectionDB();
        List<PhuongTien> result = new ArrayList<>();
        String sql = "select * from phuongtien";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
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
                phuongTien.setQuantity(quantity);
                phuongTien.setNguonnx_id(nguonnxId);
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

    @Override
    public List<NormDto> getAllPt(String typeName) {
        QDatabase.getConnectionDB();
        List<NormDto> result = new ArrayList<>();
        String sql = "SELECT phuongtien.id,phuongtien.name, loai_phuongtien.type_name, quantity,phuongtien.timestamp,loaiphuongtien_id,status,dm_tk_gio,dm_md_gio,dm_xm_gio,dm_xm_km  FROM public.phuongtien\n" +
                "join loai_phuongtien on phuongtien.loaiphuongtien_id=loai_phuongtien.id\n" +
                "join dinhmuc on dinhmuc.phuongtien_id=phuongtien.id\n" +
                "where loai_phuongtien.type=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, typeName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type_name");
                int quantity = resultSet.getInt("quantity");
                String timestamp = resultSet.getString("timestamp");
                int loaiphuongtienId = resultSet.getInt("loaiphuongtien_id");
                String status = resultSet.getString("status");
                int dm_tk_gio = resultSet.getInt("dm_tk_gio");
                int dm_md_gio = resultSet.getInt("dm_md_gio");
                int dm_xm_gio = resultSet.getInt("dm_xm_gio");
                int dm_xm_km = resultSet.getInt("dm_xm_km");

                result.add(new NormDto(id, name, type, quantity,dm_md_gio,dm_tk_gio,dm_xm_gio,dm_xm_km,timestamp,loaiphuongtienId,status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<LoaiPhuongTien> getLoaiPt(String typeName) {
        QDatabase.getConnectionDB();
        List<LoaiPhuongTien> result = new ArrayList<>();
        String sql = "SELECT * FROM loai_phuongtien";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String typeName1 = resultSet.getString("type_name");

                result.add(new LoaiPhuongTien(type, typeName1, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<LoaiPhuongTien> getAllLoaiPt() {
        QDatabase.getConnectionDB();
        List<LoaiPhuongTien> result = new ArrayList<>();
        String sql = "SELECT * FROM loai_phuongtien";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String typeName1 = resultSet.getString("type_name");

                result.add(new LoaiPhuongTien(type, typeName1, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ChitieuDTO getChitieuDtoById(int pt_id, int quarterId) {
        QDatabase.getConnectionDB();

        String sql = "SELECT * FROM hanmuc where pt_id=? and quarter_id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1,pt_id);
            statement.setInt(2, quarterId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quarter_id = resultSet.getInt("quarter_id");
                int phuongtien_id = resultSet.getInt("pt_id");
                String hanmucMd = resultSet.getString("hanmuc_md");
                int hanmucKm = resultSet.getInt("hanmuc_km");
                String hanmucTk = resultSet.getString("hanmuc_tk");
                int soluong = resultSet.getInt("soluong");

                return new ChitieuDTO(id, quarter_id, phuongtien_id, hanmucMd, hanmucKm, hanmucTk,soluong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int createNewChiTieu(ChitieuDTO chitieuDTO) {
        QDatabase.getConnectionDB();
        String sql = "insert into hanmuc(quarter_id, pt_id, hanmuc_md, hanmuc_km, hanmuc_tk, soluong) values(?,?,?,?,?,?) on conflict (pt_id,quarter_id) do update set hanmuc_md=?, hanmuc_km=?, hanmuc_tk=?,soluong=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, chitieuDTO.getQuarter_id());
            statement.setString(2, String.valueOf(chitieuDTO.getPhuongtien_id()));
            statement.setString(3, chitieuDTO.getHanmuc_md());
            statement.setInt(4, chitieuDTO.getHanmuc_km());
            statement.setString(5, chitieuDTO.getHanmuc_tk());
            statement.setInt(6, chitieuDTO.getSoluong());
            statement.setString(7, chitieuDTO.getHanmuc_md());
            statement.setInt(8, chitieuDTO.getHanmuc_km());
            statement.setString(9, chitieuDTO.getHanmuc_tk());
            statement.setInt(10, chitieuDTO.getSoluong());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateChiTieu(int lxd_id, int quarterId) {
        return 0;
    }

    @Override
    public int createNewNorm(Norm normDto) {
        QDatabase.getConnectionDB();
        String sql = "insert into dinhmuc(dm_tk_gio, dm_md_gio, dm_xm_km, dm_xm_gio, phuongtien_id, quarter_id) values(?,?,?,?,?,?) on conflict (phuongtien_id,quarter_id) do update set dm_tk_gio=?, dm_md_gio=?, dm_xm_km=?,dm_xm_gio=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, normDto.getDm_tk_gio());
            statement.setInt(2, normDto.getDm_md_gio());
            statement.setInt(3, normDto.getDm_xm_km());
            statement.setInt(4, normDto.getDm_xm_gio());
            statement.setInt(5, normDto.getPhuongtien_id());
            statement.setInt(6, normDto.getQuarter_id());
            statement.setInt(7, normDto.getDm_tk_gio());
            statement.setInt(8, normDto.getDm_md_gio());
            statement.setInt(9, normDto.getDm_xm_km());
            statement.setInt(10, normDto.getDm_xm_gio());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateNewNorm(Norm norm) {
        QDatabase.getConnectionDB();
        String sql = "update dinhmuc set dm_tk_gio=?, dm_md_gio=?, dm_xm_km=?,dm_xm_gio=? where phuongtien_id=? and quarter_id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, norm.getDm_tk_gio());
            statement.setInt(2, norm.getDm_md_gio());
            statement.setInt(3, norm.getDm_xm_km());
            statement.setInt(4, norm.getDm_xm_gio());
            statement.setInt(5, norm.getPhuongtien_id());
            statement.setInt(6, norm.getQuarter_id());

            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoaiPhuongTien findPtById(int id) {
        QDatabase.getConnectionDB();
        String sql = "SELECT * FROM loai_phuongtien where id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String typeName1 = resultSet.getString("type_name");

                return new LoaiPhuongTien(type, typeName1, id1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
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
    public int updateNew(PhuongTien phuongTien) {
        QDatabase.getConnectionDB();
        String sql = "update phuongtien set name=?, quantity=?, status=?,loaiphuongtien_id=? where id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, phuongTien.getName());
            statement.setInt(2, phuongTien.getQuantity());
            statement.setString(3, phuongTien.getStatus());
            statement.setInt(4, phuongTien.getLoaiphuongtien_id());
            statement.setInt(5, phuongTien.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createNewPt(NormDto normDto) {
        return 0;
    }

    @Override
    public PhuongTien udpateObj(PhuongTien phuongTien) {
        QDatabase.getConnectionDB();
        String sql = "update phuongtien set name=?,quantity=?,status=?,nguonnx_id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, phuongTien.getName());
            statement.setInt(2, phuongTien.getQuantity());
            statement.setString(3, phuongTien.getStatus());
            statement.setInt(4, phuongTien.getNguonnx_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return phuongTien;
    }

    @Override
    public int createPt(PhuongTien phuongTien) {
        return 0;
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
                int dm_tk = resultSet.getInt("dm_tk_gio");
                int dm_md = resultSet.getInt("dm_md_gio");
                int dm_xm_km = resultSet.getInt("dm_xm_km");
                int dm_xm_gio = resultSet.getInt("dm_xm_gio");
                int quantity = resultSet.getInt("quantity");
                int nguonnxId = resultSet.getInt("nguonnx_id");
                int loaiphuongtienId = resultSet.getInt("loaiphuongtien_id");
                String status = resultSet.getString("status");

                PhuongTien phuongTien = new PhuongTien();
                phuongTien.setId(id);
                phuongTien.setName(name);
                phuongTien.setDm_tk(dm_tk);
                phuongTien.setDm_md(dm_md);
                phuongTien.setDm_xm_km(dm_xm_km);
                phuongTien.setDm_xm_gio(dm_xm_gio);
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

    @Override
    public List<NormDto> findListPhuongTienByType(int lpt_id) {
        return List.of();
    }

    @Override
    public Integer findNnxByPt(String pt) {
        QDatabase.getConnectionDB();
        String sql = "SELECT nguonnx_id FROM phuongtien where type id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, pt);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("nguonnx_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
}
