package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.DviNvu;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.DvNvService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DvNvImp implements DvNvService {
    @Override
    public List<DviNvu> getAll() {
        QDatabase.getConnectionDB();
        List<DviNvu> result = new ArrayList<>();


        String SQL_SELECT = "Select * from dvi_nv";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int dv_id = resultSet.getInt("dv_id");
                int nv_id = resultSet.getInt("nv_id");
                String createtime = resultSet.getString("createtime");
                DviNvu dviNvu = new DviNvu();
                dviNvu.setId(id);
                dviNvu.setDv_id(dv_id);
                dviNvu.setNv_id(nv_id);
                dviNvu.setCreatetime(createtime);
                result.add(dviNvu);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<NhiemVuDto> getAllDv_nv() {
        QDatabase.getConnectionDB();
        List<NhiemVuDto> result = new ArrayList<>();


        String SQL_SELECT = "select dvi_nv.id, dv_id,nv_id,ten,loai,loai_tt,ten_nv,chitiet from dvi_nv inner join nguon_nx on nguon_nx.id=dvi_nv.dv_id inner join chi_tiet_nhiemvu on dvi_nv.nv_id=chi_tiet_nhiemvu.id;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("dvi_nv.id");
                int dv_id = resultSet.getInt("dv_id");
                int nv_id = resultSet.getInt("nv_id");
                String ten = resultSet.getString("ten");
                String loai = resultSet.getString("loai");
                String loai_tt = resultSet.getString("loai_tt");
                String ten_nv = resultSet.getString("ten_nv");
                String chitiet = resultSet.getString("chitiet");
                NhiemVuDto nhiemVuDto = new NhiemVuDto();
                nhiemVuDto.setId(id);
                nhiemVuDto.setNv_id(nv_id);
                nhiemVuDto.setDv_id(dv_id);
                nhiemVuDto.setTen_dvi(ten);
                nhiemVuDto.setTen_nv(ten_nv);
                nhiemVuDto.setLoai(loai);
                nhiemVuDto.setLoai_tt(loai_tt);
                nhiemVuDto.setChitiet(chitiet);

                result.add(nhiemVuDto);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<NhiemVuDto> getDv_nvByIdDv(int id1) {
        QDatabase.getConnectionDB();
        List<NhiemVuDto> result = new ArrayList<>();


        String SQL_SELECT = "select dvi_nv.id, dv_id,nv_id,ten,loai,loai_tt,ten_nv,chitiet from dvi_nv inner join nguon_nx on nguon_nx.id=dvi_nv.dv_id inner join chi_tiet_nhiemvu on dvi_nv.nv_id=chi_tiet_nhiemvu.id where nguon_nx.id=?;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1 , id1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("dvi_nv.id");
                int dv_id = resultSet.getInt("dv_id");
                int nv_id = resultSet.getInt("nv_id");
                String ten = resultSet.getString("ten");
                String loai = resultSet.getString("loai");
                String loai_tt = resultSet.getString("loai_tt");
                String ten_nv = resultSet.getString("ten_nv");
                String chitiet = resultSet.getString("chitiet");
                NhiemVuDto nhiemVuDto = new NhiemVuDto();
                nhiemVuDto.setId(id);
                nhiemVuDto.setNv_id(nv_id);
                nhiemVuDto.setDv_id(dv_id);
                nhiemVuDto.setTen_dvi(ten);
                nhiemVuDto.setTen_nv(ten_nv);
                nhiemVuDto.setLoai(loai);
                nhiemVuDto.setLoai_tt(loai_tt);
                nhiemVuDto.setChitiet(chitiet);

                result.add(nhiemVuDto);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public DviNvu create(DviNvu dviNvu) {
        return null;
    }

    @Override
    public DviNvu update(DviNvu dviNvu) {
        return null;
    }

    @Override
    public List<DviNvu> findByDvId(int id1) {
        QDatabase.getConnectionDB();
        List<DviNvu> result = new ArrayList<>();

        String SQL_SELECT = "select * from dvi_nv where dv_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int dv_id = resultSet.getInt("dv_id");
                int nv_id = resultSet.getInt("nv_id");
                String createtime = resultSet.getString("createtime");
                DviNvu dviNvu = new DviNvu();
                dviNvu.setId(id);
                dviNvu.setDv_id(dv_id);
                dviNvu.setNv_id(nv_id);
                dviNvu.setCreatetime(createtime);
                result.add(dviNvu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
