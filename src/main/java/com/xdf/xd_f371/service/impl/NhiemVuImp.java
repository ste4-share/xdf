package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.HanmucNhiemvu;
import com.xdf.xd_f371.entity.NhiemVu;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.NhiemVuService;
import org.postgresql.util.PGInterval;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class NhiemVuImp implements NhiemVuService {
    @Override
    public List<NhiemVu> getAll() {
        QDatabase.getConnectionDB();
        List<NhiemVu> result = new ArrayList<>();

        String SQL_SELECT = "Select * from nhiemvu";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String ten_nv = resultSet.getString("ten_nv");
                String status = resultSet.getString("status");
                NhiemVu nhiemVu = new NhiemVu();
                nhiemVu.setId(id);
                nhiemVu.setTen_nv(ten_nv);
                nhiemVu.setStatus(status);
                result.add(nhiemVu);
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
    public List<CtnvDto> getAllNvByType(int loainv_id) {
        QDatabase.getConnectionDB();
        List<CtnvDto> result = new ArrayList<>();
        String SQL_SELECT = "SELECT nhiemvu.id as nvid, ten_nv, nhiemvu FROM public.nhiemvu \n" +
                "left join chitiet_nhiemvu on nhiemvu.id=chitiet_nhiemvu.nhiemvu_id \n" +
                "where assignment_type_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, loainv_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("nvid");
                String ten_nv = resultSet.getString("ten_nv");
                String chitiet = resultSet.getString("nhiemvu");

                result.add(new CtnvDto(id, ten_nv, chitiet));
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
    public List<KhoiDto> getAllKhoi() {
        QDatabase.getConnectionDB();
        List<KhoiDto> result = new ArrayList<>();

        String SQL_SELECT = "Select * from team";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String ten_nv = resultSet.getString("name");
                String code = resultSet.getString("team_code");

                result.add(new KhoiDto(id,ten_nv, code));
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
    public KhoiDto findKhoiById(int id1) {
        QDatabase.getConnectionDB();

        String SQL_SELECT = "Select * from team where id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String ten_nv = resultSet.getString("name");
                String code = resultSet.getString("team_code");

                return new KhoiDto(id,ten_nv, code);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<NhiemVuDto> getAllNVDTO(int khoi) {
        QDatabase.getConnectionDB();
        List<NhiemVuDto> result = new ArrayList<>();
        String SQL_SELECT = "SELECT nhiemvu.id as nvid,team_id,loai_nhiemvu.id as lnv_id,chitiet_nhiemvu.id as ctnv_id, ten_nv, nhiemvu, assignment_type_name FROM public.nhiemvu \n" +
                "left join chitiet_nhiemvu on nhiemvu.id=chitiet_nhiemvu.nhiemvu_id\n" +
                "left join loai_nhiemvu on nhiemvu.assignment_type_id=loai_nhiemvu.id where team_id=?";

        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, khoi);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int nvid = resultSet.getInt("nvid");
                int teamID = resultSet.getInt("team_id");
                int lnvId = resultSet.getInt("lnv_id");
                int ctnvId = resultSet.getInt("ctnv_id");
                String ten_nv = resultSet.getString("ten_nv");
                String nhiemvu = resultSet.getString("nhiemvu");
                String assignment_type_name = resultSet.getString("assignment_type_name");
                NhiemVuDto nhiemVu = new NhiemVuDto();
                nhiemVu.setNv_id(nvid);
                nhiemVu.setTen_nv(ten_nv);
                nhiemVu.setTeam_id(teamID);
                nhiemVu.setLnv_id(lnvId);
                nhiemVu.setCtnv_id(ctnvId);
                nhiemVu.setChitiet(nhiemvu);
                nhiemVu.setTen_loai_nv(assignment_type_name);
                result.add(nhiemVu);
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
    public NhiemVu create(NhiemVu nhiemVu) {
        return null;
    }

    @Override
    public int createHanmucNhiemVu(HanmucNhiemvu hanmucNhiemvu) {
        QDatabase.getConnectionDB();
        String sql = "insert into hanmuc_nhiemvu(quarter_id, unit_id, nhiemvu_id, ct_tk, ct_md, consumpt) values(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, hanmucNhiemvu.getQuarter_id());
            statement.setInt(2, hanmucNhiemvu.getUnit_id());
            statement.setInt(3, hanmucNhiemvu.getNhiemvu_id());
            statement.setObject(4, hanmucNhiemvu.getCt_tk());
            statement.setObject(5, hanmucNhiemvu.getCt_md());
            statement.setInt(6, hanmucNhiemvu.getConsumpt());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public HanmucNhiemvu findHanmucNhiemVu(int quarter_id, int unit_id, int nhiemvu_id) {
        return null;
    }

    @Override
    public HanmucNhiemvu getHanmucNhiemvu(int unit_id, int nhiemvu_id, int quarter_id) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "select * from hanmuc_nhiemvu where unit_id=? and nhiemvu_id=? and quarter_id=?";

        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, unit_id);
            preparedStatement.setInt(2, nhiemvu_id);
            preparedStatement.setInt(3, quarter_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int quarterId = resultSet.getInt("quarter_id");
                int unitId = resultSet.getInt("unit_id");
                int nhiemvuId = resultSet.getInt("nhiemvu_id");
                PGInterval ct_tk = (PGInterval) resultSet.getObject("ct_tk");
                PGInterval ct_md = (PGInterval) resultSet.getObject("ct_md");
                int consumpt = resultSet.getInt("consumpt");

                return new HanmucNhiemvu(id,quarterId, unitId, nhiemvuId, ct_tk.getHours() +":" +ct_tk.getMinutes(),ct_md.getHours() + ":"+ct_md.getMinutes(),consumpt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int create(NhiemVuReport nhiemVuReport) {
        QDatabase.getConnectionDB();
        String sql = "insert into nhiemvu_reporter(title_1, title_2, title_3, title_4, soluong, nhiemvu_id,phuongtien_id,ten_nv_1,ten_nv_2,ten_nv_3) values(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, nhiemVuReport.getTitle_1());
            statement.setString(2, nhiemVuReport.getTitle_2());
            statement.setString(3, nhiemVuReport.getTitle_3());
            statement.setString(4, nhiemVuReport.getTitle_4());
            statement.setString(5, nhiemVuReport.getSoluong());
            statement.setInt(6, nhiemVuReport.getNhiemvu_id());
            statement.setInt(7, nhiemVuReport.getPhuongtien_id());
            statement.setString(8, nhiemVuReport.getTen_nv_1());
            statement.setString(9, nhiemVuReport.getTen_nv_2());
            statement.setString(10, nhiemVuReport.getTen_nv_3());

            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public NhiemVu update(NhiemVu nhiemVu) {
        return null;
    }

    @Override
    public NhiemVu findCtnvByID(String chiTietNhiemVu) {
        return null;
    }

    @Override
    public NhiemVu findById(int id1) {
        QDatabase.getConnectionDB();
        NhiemVu result = new NhiemVu();

        String SQL_SELECT = "select * from nhiemvu where id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ten_nv = resultSet.getString("ten_nv");
                String status = resultSet.getString("status");

                result.setId(id);
                result.setTen_nv(ten_nv);
                result.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public NhiemVu findByTenNv(String tennv, String ct) {
        QDatabase.getConnectionDB();
        NhiemVu result = new NhiemVu();

        String SQL_SELECT = "select * from nhiemvu where ten_nv=? and chitiet=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, tennv);
            preparedStatement.setString(2, ct);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ten_nv = resultSet.getString("ten_nv");
                String status = resultSet.getString("status");

                result.setId(id);
                result.setTen_nv(ten_nv);
                result.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<ChiTietNhiemVuDTO> getNvAndCtnv() {
        QDatabase.getConnectionDB();
        List<ChiTietNhiemVuDTO> result = new ArrayList<>();


        String SQL_SELECT = "select nhiemvu.id as nvid,ten_nv, nhiemvu, chitiet_nhiemvu.id as ctnvid from nhiemvu left join chitiet_nhiemvu on chitiet_nhiemvu.nhiemvu_id = nhiemvu.id;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("nvid");
                String ten_nv = resultSet.getString("ten_nv");
                String nhiemVu = resultSet.getString("nhiemvu");
                int ctnv_id = resultSet.getInt("ctnvid");
                ChiTietNhiemVuDTO chiTietNhiemVuDTO = new ChiTietNhiemVuDTO();
                chiTietNhiemVuDTO.setId(id);
                chiTietNhiemVuDTO.setNhiemvu(ten_nv);
                chiTietNhiemVuDTO.setChiTietNhiemVu(nhiemVu);
                chiTietNhiemVuDTO.setCtnv_id(ctnv_id);
                result.add(chiTietNhiemVuDTO);
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
    public List<ChiTietNhiemVuDTO> getAllCtnvByType(int type) {

        QDatabase.getConnectionDB();
        List<ChiTietNhiemVuDTO> result = new ArrayList<>();


        String SQL_SELECT = "select nhiemvu.id as nvid,ten_nv, nhiemvu, chitiet_nhiemvu.id as ctnvid from nhiemvu join chitiet_nhiemvu on chitiet_nhiemvu.nhiemvu_id = nhiemvu.id where assignment_type_id=?;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("nvid");
                String ten_nv = resultSet.getString("ten_nv");
                String nhiemVu = resultSet.getString("nhiemvu");
                int ctnv_id = resultSet.getInt("ctnvid");
                ChiTietNhiemVuDTO chiTietNhiemVuDTO = new ChiTietNhiemVuDTO();
                chiTietNhiemVuDTO.setId(id);
                chiTietNhiemVuDTO.setNhiemvu(ten_nv);
                chiTietNhiemVuDTO.setChiTietNhiemVu(nhiemVu);
                chiTietNhiemVuDTO.setCtnv_id(ctnv_id);
                result.add(chiTietNhiemVuDTO);
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
