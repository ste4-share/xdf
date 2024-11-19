package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.entity.Ledger;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.LedgerService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LedgerImp implements LedgerService {
    @Override
    public List<Ledger> getAll() {
        List<Ledger> result = new ArrayList<>();
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from ledgers";
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quarter_id = resultSet.getInt("quarter_id");
                int bill_id = resultSet.getInt("bill_id");
                int amount = resultSet.getInt("amount");
                int bill_type_id = resultSet.getInt("bill_type_id");
                String status = resultSet.getString("status");
                Date from_date = resultSet.getDate("from_date");
                Date end_date = resultSet.getDate("end_date");

                Ledger ledger = new Ledger();
                ledger.setId(id);
                ledger.setQuarter_id(quarter_id);
                ledger.setBill_id(bill_id);
                ledger.setBillType_id(bill_type_id);
                ledger.setAmount(amount);
                ledger.setStatus(status);
                ledger.setEnd_date(end_date);
                ledger.setFrom_date(from_date);
                result.add(ledger);
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
    public Ledger findById(int id1) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from ledgers where id=?";
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1,id1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quarter_id = resultSet.getInt("quarter_id");
                int bill_id = resultSet.getInt("bill_id");
                int amount = resultSet.getInt("amount");
                int bill_type_id = resultSet.getInt("bill_type_id");
                String status = resultSet.getString("status");
                Date from_date = resultSet.getDate("from_date");
                Date end_date = resultSet.getDate("end_date");

                Ledger ledger = new Ledger();
                ledger.setId(id);
                ledger.setQuarter_id(quarter_id);
                ledger.setBill_id(bill_id);
                ledger.setAmount(amount);
                ledger.setBillType_id(bill_type_id);
                ledger.setStatus(status);
                ledger.setEnd_date(end_date);
                ledger.setFrom_date(from_date);
                return ledger;
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
    public Ledger findByBillId(int quarterId, int billId) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from ledgers where quarter_id=? and bill_id=?";
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1,quarterId);
            preparedStatement.setInt(2,billId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quarter_id = resultSet.getInt("quarter_id");
                int bill_id = resultSet.getInt("bill_id");
                int amount = resultSet.getInt("amount");
                int bill_type_id = resultSet.getInt("bill_type_id");
                String status = resultSet.getString("status");
                Date from_date = resultSet.getDate("from_date");
                Date end_date = resultSet.getDate("end_date");

                Ledger ledger = new Ledger();
                ledger.setId(id);
                ledger.setQuarter_id(quarter_id);
                ledger.setBill_id(bill_id);
                ledger.setAmount(amount);
                ledger.setBillType_id(bill_type_id);
                ledger.setStatus(status);
                ledger.setEnd_date(end_date);
                ledger.setFrom_date(from_date);
                return ledger;
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
    public List<Ledger> findByQuarterId(int quarterId) {
        List<Ledger> result = new ArrayList<>();
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from ledgers where quarter_id=?";
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, quarterId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quarter_id = resultSet.getInt("quarter_id");
                int bill_id = resultSet.getInt("bill_id");
                int amount = resultSet.getInt("amount");
                int bill_type_id = resultSet.getInt("bill_type_id");
                String status = resultSet.getString("status");
                Date from_date = resultSet.getDate("from_date");
                Date end_date = resultSet.getDate("end_date");

                Ledger ledger = new Ledger();
                ledger.setId(id);
                ledger.setQuarter_id(quarter_id);
                ledger.setBill_id(bill_id);
                ledger.setAmount(amount);
                ledger.setBillType_id(bill_type_id);
                ledger.setStatus(status);
                ledger.setEnd_date(end_date);
                ledger.setFrom_date(from_date);
                result.add(ledger);
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
    public List<Ledger> findByPresentTime(Date preTime) {
        return List.of();
    }

    @Override
    public Ledger createNew(Ledger ledger) {
        QDatabase.getConnectionDB();
        String sql = "insert into ledgers(quarter_id, bill_id, amount, from_date, end_date, status,bill_type_id) values(?,?,?,?,?,?,?) on conflict do nothing";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, ledger.getQuarter_id());
            statement.setInt(2, ledger.getBill_id());
            statement.setInt(3, ledger.getAmount());
            statement.setDate(4, ledger.getFrom_date());
            statement.setDate(5, ledger.getEnd_date());
            statement.setString(6, ledger.getStatus());
            statement.setInt(7, ledger.getBillType_id());

            statement.executeUpdate();
            return ledger;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ledger updateNew(Ledger ledger) {
        QDatabase.getConnectionDB();
        String sql = "update ledgers set quarter_id=?, bill_id=?, amount=?, from_date=?, end_date=?,bill_type_id=? status=? where id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, ledger.getQuarter_id());
            statement.setInt(2, ledger.getBill_id());
            statement.setInt(3, ledger.getAmount());
            statement.setDate(4, ledger.getFrom_date());
            statement.setDate(5, ledger.getEnd_date());
            statement.setInt(6, ledger.getBillType_id());
            statement.setString(7, ledger.getStatus());
            statement.setInt(8, ledger.getId());
            statement.executeUpdate();
            return ledger;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete_f(Ledger ledger) {
        QDatabase.getConnectionDB();
        String sql = "update ledgers set status=? where id=?";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setString(1, ledger.getStatus());
            statement.setInt(2, ledger.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
