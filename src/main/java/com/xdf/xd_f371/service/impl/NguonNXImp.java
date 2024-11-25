package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.GroupTitle;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NguonnxTitle;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.NguonNXService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NguonNXImp implements NguonNXService {

    @Override
    public List<NguonNx> getAllAndNguonnx() {
        QDatabase.getConnectionDB();
        List<NguonNx> result = new ArrayList<>();
        String SQL_SELECT = "select nguonnx_id, ten, tructhuoc.name as ttname,string_agg(code, ', '),createtime,tructhuoc.id as ttid from nguon_nx \n" +
                "join nguonnx_title on nguon_nx.id=nguonnx_title.nguonnx_id\n" +
                "join tructhuoc on tructhuoc.id=title_id join category on category.tructhuoc_id=tructhuoc.id\n" +
                "group by nguonnx_id, ten, ttname,createtime,ttid";
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("nguonnx_id");
                String ten = resultSet.getString("ten");
                NguonNx obj = new NguonNx();
                obj.setId(id);
                obj.setTen(ten);
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
    public List<NguonNx> getAllUnless(String ten_1) {
        QDatabase.getConnectionDB();
        List<NguonNx> result = new ArrayList<>();


        String SQL_SELECT = "Select * from nguon_nx where ten <> ?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1,ten_1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String ten = resultSet.getString("ten");
                NguonNx obj = new NguonNx();
                obj.setId(id);
                obj.setTen(ten);
                result.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public List<NguonNx> findNguonnxTructhuocF() {
        QDatabase.getConnectionDB();
        List<NguonNx> result = new ArrayList<>();
        String SQL_SELECT = "SELECT * FROM public.nguon_nx join donvi_tructhuoc on nguon_nx.id=donvi_tructhuoc.dvi_tructhuoc_id";

        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ten = resultSet.getString("ten");
                result.add(new NguonNx(id, ten));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<GroupTitle> getAllGroup() {
        QDatabase.getConnectionDB();
        List<GroupTitle> result = new ArrayList<>();


        String SQL_SELECT = "SELECT * FROM group_title";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String groupName = resultSet.getString("group_name");
                String groupCode = resultSet.getString("group_code");
                result.add(new GroupTitle(id, groupName, groupCode));
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
    public List<NguonnxTitle> getAllNnxTitles(int groupId) {
        QDatabase.getConnectionDB();
        List<NguonnxTitle> result = new ArrayList<>();
        String SQL_SELECT = "SELECT * FROM nguonnx_title where group_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int nguonnxId = resultSet.getInt("nguonnx_id");
                int titleId = resultSet.getInt("title_id");
                int groupId1 = resultSet.getInt("group_id");

                result.add(new NguonnxTitle(id, nguonnxId, titleId, groupId1));
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
    public int updateNguonnxTitle(NguonnxTitle nguonnxTitle) {
        QDatabase.getConnectionDB();
        String sql = "begin transaction;update nguonnx_title set title_id=? where nguonnx_id=? and group_id=?;commit;";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, nguonnxTitle.getTitle_id());
            statement.setInt(2, nguonnxTitle.getNguonnx_id());
            statement.setInt(3, nguonnxTitle.getGroup_id());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
