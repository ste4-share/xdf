package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.InvReportDetail;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.InvReportDetailService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class invReportDetailImp implements InvReportDetailService {

    @Override
    public List<InvReportDetail> getAll() {
        QDatabase.getConnectionDB();
        List<InvReportDetail> result = new ArrayList<>();


        String SQL_SELECT = "Select * from inv_report_detail";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String loaixd = resultSet.getString("loaixd");
                int soluong = resultSet.getInt("soluong");
                String lv1 = resultSet.getString("title_lv1");
                String lv2 = resultSet.getString("title_lv2");
                String lv3 = resultSet.getString("title_lv3");
                String lxdlv1 = resultSet.getString("title_lxd_lv1");
                String lxdlv2 = resultSet.getString("title_lxd_lv2");
                String lxdlv3 = resultSet.getString("title_lxd_lv3");
                InvReportDetail invReport = new InvReportDetail();
                invReport.setId(id);
                invReport.setLoaixd(loaixd);
                invReport.setSoluong(soluong);
                invReport.setTitle_lv1(lv1);
                invReport.setTitle_lv2(lv2);
                invReport.setTitle_lv3(lv3);
                invReport.setTitle_lxd_lv1(lxdlv1);
                invReport.setTitle_lxd_lv2(lxdlv2);
                invReport.setTitle_lxd_lv3(lxdlv3);
                result.add(invReport);
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
    public int createNew(InvReportDetail invReportDetail) {
        String SQL_SELECT = "insert into inv_report_detail(loaixd,soluong,title_lv1,title_lv2,title_lv3,title_lxd_lv1,title_lxd_lv2,title_lxd_lv3,xd_id,quarter_id,title_id) values(?,?,?,?,?,?,?,?,?,?,?)";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, invReportDetail.getLoaixd());
            preparedStatement.setInt(2, invReportDetail.getSoluong());
            preparedStatement.setString(3, invReportDetail.getTitle_lv1());
            preparedStatement.setString(4, invReportDetail.getTitle_lv2());
            preparedStatement.setString(5, invReportDetail.getTitle_lv3());
            preparedStatement.setString(6, invReportDetail.getTitle_lxd_lv1());
            preparedStatement.setString(7, invReportDetail.getTitle_lxd_lv2());
            preparedStatement.setString(8, invReportDetail.getTitle_lxd_lv3());
            preparedStatement.setInt(9, invReportDetail.getXd_id());
            preparedStatement.setInt(10, invReportDetail.getQuarter_id());
            preparedStatement.setInt(11, invReportDetail.getTitle_id());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateNew(InvReportDetail invReportDetail) {
        String SQL_SELECT = "update inv_report_detail set soluong=? where xd_id=? and quarter_id=? and title_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, invReportDetail.getSoluong());
            preparedStatement.setInt(2, invReportDetail.getXd_id());
            preparedStatement.setInt(3, invReportDetail.getQuarter_id());
            preparedStatement.setInt(4, invReportDetail.getTitle_id());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(InvReportDetail invReportDetail) {
        String SQL_SELECT = "update inv_report_detail set soluong=? where id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, invReportDetail.getSoluong());
            preparedStatement.setInt(2, invReportDetail.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateTitleId(int pre_id, int current_id) {
        String SQL_SELECT = "update inv_report_detail set title_id=? where title_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, current_id);
            preparedStatement.setInt(2, pre_id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAll() {
        String SQL_SELECT = "begin transaction;delete from inv_report_detail;commit;";
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InvReportDetail findByIds(int xd_id, int quarter_id, int titleId) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from inv_report_detail where xd_id=? and quarter_id=? and title_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1,xd_id);
            preparedStatement.setInt(2,quarter_id);
            preparedStatement.setInt(3,titleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String loaixd = resultSet.getString("loaixd");
                int soluong = resultSet.getInt("soluong");
                String lv1 = resultSet.getString("title_lv1");
                String lv2 = resultSet.getString("title_lv2");
                String lv3 = resultSet.getString("title_lv3");
                String lxdlv1 = resultSet.getString("title_lxd_lv1");
                String lxdlv2 = resultSet.getString("title_lxd_lv2");
                String lxdlv3 = resultSet.getString("title_lxd_lv3");
                int xdId = resultSet.getInt("xd_id");
                int quarterId = resultSet.getInt("quarter_id");
                int titleId1 = resultSet.getInt("title_id");
                InvReportDetail invReport = new InvReportDetail();
                invReport.setId(id);
                invReport.setLoaixd(loaixd);
                invReport.setSoluong(soluong);
                invReport.setTitle_lv1(lv1);
                invReport.setTitle_lv2(lv2);
                invReport.setTitle_lv3(lv3);
                invReport.setTitle_lxd_lv1(lxdlv1);
                invReport.setTitle_lxd_lv2(lxdlv2);
                invReport.setTitle_lxd_lv3(lxdlv3);
                invReport.setXd_id(xdId);
                invReport.setTitle_id(titleId1);
                invReport.setQuarter_id(quarterId);
                return invReport;
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
