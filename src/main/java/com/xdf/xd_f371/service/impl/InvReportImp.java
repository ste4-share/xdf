package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.InvReport;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.InvReportService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvReportImp implements InvReportService {
    @Override
    public List<InvReport> getAll() {
        QDatabase.getConnectionDB();
        List<InvReport> result = new ArrayList<>();


        String SQL_SELECT = "Select * from inv_report";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int petroleumId = resultSet.getInt("petroleum_id");
                int quarterId = resultSet.getInt("quarter_id");
                int inventoryId = resultSet.getInt("inventory_id");
                int reportHeader = resultSet.getInt("report_header");
                int quantity = resultSet.getInt("quantity");
                int priceId = resultSet.getInt("price_id");
                InvReport invReport = new InvReport();
                invReport.setId(id);
                invReport.setPetroleum_id(petroleumId);
                invReport.setQuantity(quarterId);
                invReport.setInventory_id(inventoryId);
                invReport.setReport_header(reportHeader);
                invReport.setQuantity(quantity);
                invReport.setPrice_id(priceId);
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
    public List<InvReport> getAllByPetroleumId(int petroleumID) {
        QDatabase.getConnectionDB();
        List<InvReport> result = new ArrayList<>();


        String SQL_SELECT = "Select * from inv_report where petroleum_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1,petroleumID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int petroleumId = resultSet.getInt("petroleum_id");
                int quarterId = resultSet.getInt("quarter_id");
                int inventoryId = resultSet.getInt("inventory_id");
                int reportHeader = resultSet.getInt("report_header");
                int quantity = resultSet.getInt("quantity");
                int priceId = resultSet.getInt("price_id");
                InvReport invReport = new InvReport();
                invReport.setId(id);
                invReport.setPetroleum_id(petroleumId);
                invReport.setQuantity(quarterId);
                invReport.setInventory_id(inventoryId);
                invReport.setReport_header(reportHeader);
                invReport.setQuantity(quantity);
                invReport.setPrice_id(priceId);
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
    public int updateReport(InvReport invReport) {
        String SQL_SELECT = "update inv_report set inventory_id=?, quantity=?,price_id=? where petroleum_id=? and quarter_id=? and report_header=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, invReport.getInventory_id());
            preparedStatement.setInt(2, invReport.getQuantity());
            preparedStatement.setInt(3, invReport.getPrice_id());
            preparedStatement.setInt(4, invReport.getPetroleum_id());
            preparedStatement.setInt(5, invReport.getQuarter_id());
            preparedStatement.setInt(6, invReport.getReport_header());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int create(InvReport invReport) {
        String SQL_SELECT = "insert into inv_report(petroleum_id,quarter_id,inventory_id,report_header,quantity,price_id) values(?,?,?,?,?,?)";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, invReport.getPetroleum_id());
            preparedStatement.setInt(2, invReport.getQuarter_id());
            preparedStatement.setInt(3, invReport.getInventory_id());
            preparedStatement.setInt(4, invReport.getReport_header());
            preparedStatement.setInt(5, invReport.getQuantity());
            preparedStatement.setInt(6, invReport.getPrice_id());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public InvReport findByPetroleum(int petroleumId1, int qt_id, int h_id) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from inv_report where petroleum_id=? and quarter_id=? and report_header=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, petroleumId1);
            preparedStatement.setInt(2, qt_id);
            preparedStatement.setInt(3, h_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int petroleumId = resultSet.getInt("petroleum_id");
                int quarterId = resultSet.getInt("quarter_id");
                int inventoryId = resultSet.getInt("inventory_id");
                int reportHeader = resultSet.getInt("report_header");
                int quantity = resultSet.getInt("quantity");
                int priceId = resultSet.getInt("price_id");
                InvReport invReport = new InvReport();
                invReport.setId(id);
                invReport.setPetroleum_id(petroleumId);
                invReport.setQuantity(quarterId);
                invReport.setInventory_id(inventoryId);
                invReport.setReport_header(reportHeader);
                invReport.setQuantity(quantity);
                invReport.setPrice_id(priceId);
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
