package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.AssignTypePriceDto;
import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.AssignType;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.entity.Mucgia;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.MucgiaService;
import com.xdf.xd_f371.service.TonKhoService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MucgiaImp implements MucgiaService {

    private TonKhoService tonKhoService = new TonkhoImp();

    @Override
    public List<Mucgia> getMucgiaByIdXangDau(int id_xd, int id_quarter) {
        QDatabase.getConnectionDB();
        List<Mucgia> result = new ArrayList<>();

        String SQL_SELECT = "Select * from mucgia where quarter_id=? and item_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id_quarter);
            preparedStatement.setInt(2, id_xd);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int price = resultSet.getInt("price");
                int amount = resultSet.getInt("amount");
                int quarter_id = resultSet.getInt("quarter_id");
                int item_id = resultSet.getInt("item_id");
                String status = resultSet.getString("status");

                Mucgia mucgia = new Mucgia();
                mucgia.setId(id);
                mucgia.setPrice(price);
                mucgia.setStatus(status);
                mucgia.setAmount(amount);
                mucgia.setQuarter_id(quarter_id);
                mucgia.setItem_id(item_id);
                result.add(mucgia);
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
    public Mucgia findMucgiaByGia(int id_xd, int id_quarter, int gia, int assT) {
        QDatabase.getConnectionDB();

        String SQL_SELECT = "Select * from mucgia where quarter_id=? and item_id=? and price=? and asssign_type_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id_quarter);
            preparedStatement.setInt(2, id_xd);
            preparedStatement.setInt(3, gia);
            preparedStatement.setInt(4, assT);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int price = resultSet.getInt("price");
                int amount = resultSet.getInt("amount");
                int quarter_id = resultSet.getInt("quarter_id");
                int item_id = resultSet.getInt("item_id");
                String status = resultSet.getString("status");
                int assId = resultSet.getInt("asssign_type_id");

                Mucgia mucgia = new Mucgia();
                mucgia.setId(id);
                mucgia.setPrice(price);
                mucgia.setStatus(status);
                mucgia.setAmount(amount);
                mucgia.setQuarter_id(quarter_id);
                mucgia.setItem_id(item_id);
                mucgia.setAssign_type_id(assId);
                return mucgia;
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
    public List<Mucgia> findMucgiaBy_xd_quarter_status(int id_xd, int id_quarter, String status1) {
        QDatabase.getConnectionDB();
        List<Mucgia> result = new ArrayList<>();

        String SQL_SELECT = "Select * from mucgia where quarter_id=? and item_id=? and status=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id_quarter);
            preparedStatement.setInt(2, id_xd);
            preparedStatement.setString(3, status1);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int price = resultSet.getInt("price");
                int amount = resultSet.getInt("amount");
                int quarter_id = resultSet.getInt("quarter_id");
                int item_id = resultSet.getInt("item_id");
                String status = resultSet.getString("status");
                int assId = resultSet.getInt("asssign_type_id");

                Mucgia mucgia = new Mucgia();
                mucgia.setId(id);
                mucgia.setPrice(price);
                mucgia.setStatus(status);
                mucgia.setAmount(amount);
                mucgia.setQuarter_id(quarter_id);
                mucgia.setItem_id(item_id);
                mucgia.setAssign_type_id(assId);
                result.add(mucgia);
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
    public Mucgia createNew(Mucgia mucgia) {
        QDatabase.getConnectionDB();

        String sql = "insert into mucgia(price, amount, quarter_id, item_id, status,asssign_type_id, inventory_id) values(?,?,?,?,?,?,?);update inventory \n" +
                                "set pre_nvdx=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=2),\n"+
                                "pre_sscd=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=1),\n" +
                                "tck_nvdx=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=2),\n"  +
                                "tck_sscd=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=1)\n" +
                                "where petro_id=? and quarter_id=?;";
        try {
            QDatabase.conn.setAutoCommit(true);
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);

            statement.setInt(1, mucgia.getPrice());
            statement.setInt(2, mucgia.getAmount());
            statement.setInt(3, mucgia.getQuarter_id());
            statement.setInt(4, mucgia.getItem_id());
            statement.setString(5, mucgia.getStatus());
            statement.setInt(6, mucgia.getAssign_type_id());
            statement.setInt(7, mucgia.getInventory_id());

            statement.setInt(8, mucgia.getQuarter_id());
            statement.setInt(9, mucgia.getItem_id());
            statement.setInt(10, mucgia.getQuarter_id());
            statement.setInt(11, mucgia.getItem_id());
            statement.setInt(12, mucgia.getQuarter_id());
            statement.setInt(13, mucgia.getItem_id());
            statement.setInt(14, mucgia.getQuarter_id());
            statement.setInt(15, mucgia.getItem_id());
            statement.setInt(16, mucgia.getItem_id());
            statement.setInt(17, mucgia.getQuarter_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return mucgia;
    }

    @Override
    public Mucgia updateMucGia(Mucgia mucgia) {
        QDatabase.getConnectionDB();
        Inventory inventory = tonKhoService.findByUniqueId(mucgia.getItem_id(), mucgia.getQuarter_id());

        String sql = "begin transaction; update mucgia set amount=?, status=? where quarter_id=? and item_id=? and price=? and asssign_type_id=?;update inventory \n" +
                "set pre_nvdx=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=2),\n" +
                "pre_sscd=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=1),\n" +
                "tck_nvdx=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=2),\n" +
                "tck_sscd=(select sum(amount) from mucgia where quarter_id=? and item_id=? and asssign_type_id=1)\n" +
                "where petro_id=? and quarter_id=?;commit;";
        try {
            PreparedStatement statement = QDatabase.conn.prepareStatement(sql);
            statement.setInt(1, mucgia.getAmount());
            statement.setString(2, mucgia.getStatus());
            statement.setInt(3, mucgia.getQuarter_id());
            statement.setInt(4, mucgia.getItem_id());
            statement.setInt(5, mucgia.getPrice());
            statement.setInt(6, mucgia.getAssign_type_id());

            statement.setInt(7, mucgia.getQuarter_id());
            statement.setInt(8, mucgia.getItem_id());
            statement.setInt(9, mucgia.getQuarter_id());
            statement.setInt(10, mucgia.getItem_id());
            statement.setInt(11, mucgia.getQuarter_id());
            statement.setInt(12, mucgia.getItem_id());
            statement.setInt(13, mucgia.getQuarter_id());
            statement.setInt(14, mucgia.getItem_id());
            statement.setInt(15, mucgia.getItem_id());
            statement.setInt(16, mucgia.getQuarter_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return mucgia;
    }

    @Override
    public Mucgia findMucGiaByID(int id1, String status1) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from mucgia where id=? and status=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, id1);
            preparedStatement.setString(2, status1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int price = resultSet.getInt("price");
                int amount = resultSet.getInt("amount");
                int quarter_id = resultSet.getInt("quarter_id");
                int item_id = resultSet.getInt("item_id");
                String status = resultSet.getString("status");
                int assT = resultSet.getInt("asssign_type_id");
                int inventory_id = resultSet.getInt("inventory_id");

                Mucgia result = new Mucgia();
                result.setId(id);
                result.setPrice(price);
                result.setStatus(status);
                result.setAmount(amount);
                result.setQuarter_id(quarter_id);
                result.setItem_id(item_id);
                result.setInventory_id(inventory_id);
                result.setAssign_type_id(assT);
                return result;
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<AssignType> getAll() {
        QDatabase.getConnectionDB();
        List<AssignType> assignTypes = new ArrayList<>();

        String SQL_SELECT = "Select * from assignment_type";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                assignTypes.add(new AssignType(id, name));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return assignTypes;
    }

    @Override
    public AssignType findByName(String name) {
        QDatabase.getConnectionDB();

        String SQL_SELECT = "Select * from assignment_type where name=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name1 = resultSet.getString("name");
                return new AssignType(id, name1);
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
    public List<AssignTypePriceDto> getPriceAndQuanTityByAssId(int ass_id, int petroId, int quarter_id) {
        QDatabase.getConnectionDB();
        List<AssignTypePriceDto> result = new ArrayList<>();
        String SQL_SELECT = "Select * from mucgia where quarter_id=? and item_id=? and asssign_type_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, quarter_id);
            preparedStatement.setInt(2, petroId);
            preparedStatement.setInt(3, ass_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String quantity = String.valueOf(resultSet.getInt("amount"));
                String price = String.valueOf(resultSet.getInt("price"));
                result.add(new AssignTypePriceDto(petroId,price, quantity));
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
    public List<Mucgia> getPriceAndQuanTityByAssId2(int ass_id, int petroId, int quarterId) {
        QDatabase.getConnectionDB();
        List<Mucgia> result = new ArrayList<>();
        String SQL_SELECT = "Select * from mucgia where quarter_id=? and item_id=? and asssign_type_id=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, quarterId);
            preparedStatement.setInt(2, petroId);
            preparedStatement.setInt(3, ass_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int price = resultSet.getInt("price");
                int amount = resultSet.getInt("amount");
                int quarter_id = resultSet.getInt("quarter_id");
                int item_id = resultSet.getInt("item_id");
                String status = resultSet.getString("status");
                int assId = resultSet.getInt("asssign_type_id");

                Mucgia mucgia = new Mucgia();
                mucgia.setId(id);
                mucgia.setPrice(price);
                mucgia.setStatus(status);
                mucgia.setAmount(amount);
                mucgia.setQuarter_id(quarter_id);
                mucgia.setItem_id(item_id);
                mucgia.setAssign_type_id(assId);
                result.add(mucgia);
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
    public List<SpotDto> getAllSpots(int quart_id) {
        QDatabase.getConnectionDB();
        List<SpotDto> result = new ArrayList<>();
        String SQL_SELECT = "SELECT lxd2.id as lxd_id,lxd2.maxd, lxd2.tenxd, ast.name as nvdx,sum(amount) as nvdx_total, \n" +
                "(select assignment_type.name from assignment_type where id=1 limit 1) as sscd, \n" +
                "(select sum(amount) as total_sscd from assignment_type \n" +
                "join mucgia on assignment_type.id=mucgia.asssign_type_id \n" +
                "join loaixd2 on mucgia.item_id=loaixd2.id \n" +
                "where assignment_type.id=1 and tenxd=lxd2.tenxd limit 1) as sscd_total\n" +
                "FROM mucgia mg\n" +
                "right join loaixd2 lxd2 on mg.item_id=lxd2.id \n" +
                "join assignment_type ast on ast.id=mg.asssign_type_id\n" +
                "where ast.id=2 and mg.quarter_id=?\n" +
                "group by lxd_id, maxd, tenxd, nvdx \n" +
                "order by nvdx_total desc";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, quart_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                Integer nvdx_total = resultSet.getInt("nvdx_total");
                Integer sscd_total = resultSet.getInt("sscd_total");
                int lxd_id = resultSet.getInt("lxd_id");
                if (nvdx_total==null){
                    nvdx_total=0;
                }
                if (sscd_total==null){
                    sscd_total=0;
                }
                Integer total = sscd_total+ nvdx_total;

                SpotDto spotDto = new SpotDto();
                spotDto.setMaxd(maxd);
                spotDto.setLxd_id(lxd_id);
                spotDto.setTenxd(tenxd);
                spotDto.setNvdx_total(String.valueOf(nvdx_total));
                spotDto.setSscd_total(String.valueOf(sscd_total));
                spotDto.setTotal(String.valueOf(total));
                result.add(spotDto);
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
    public SpotDto findSpotByPetro(int quarter_id, int petroleum_id) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "SELECT lxd2.id as lxd_id,lxd2.maxd, lxd2.tenxd, ast.name as nvdx,sum(amount) as nvdx_total, \n" +
                "(select assignment_type.name from assignment_type where id=1 limit 1) as sscd, \n" +
                "(select sum(amount) as total_sscd from assignment_type \n" +
                "join mucgia on assignment_type.id=mucgia.asssign_type_id \n" +
                "join loaixd2 on mucgia.item_id=loaixd2.id \n" +
                "where assignment_type.id=1 and tenxd=lxd2.tenxd limit 1) as sscd_total\n" +
                "FROM mucgia mg\n" +
                "right join loaixd2 lxd2 on mg.item_id=lxd2.id \n" +
                "join assignment_type ast on ast.id=mg.asssign_type_id\n" +
                "where ast.id=2 and mg.quarter_id=? and lxd2.id=?\n" +
                "group by lxd_id,maxd, tenxd, nvdx \n" +
                "order by nvdx_total desc";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, quarter_id);
            preparedStatement.setInt(2, petroleum_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String maxd = resultSet.getString("maxd");
                String tenxd = resultSet.getString("tenxd");
                Integer nvdx_total = resultSet.getInt("nvdx_total");
                Integer sscd_total = resultSet.getInt("sscd_total");
                int lxd_id = resultSet.getInt("lxd_id");
                if (nvdx_total==null){
                    nvdx_total=0;
                }
                if (sscd_total==null){
                    sscd_total=0;
                }
                Integer total = sscd_total+ nvdx_total;

                SpotDto spotDto = new SpotDto();
                spotDto.setMaxd(maxd);
                spotDto.setLxd_id(lxd_id);
                spotDto.setTenxd(tenxd);
                spotDto.setNvdx_total(String.valueOf(nvdx_total));
                spotDto.setSscd_total(String.valueOf(sscd_total));
                spotDto.setTotal(String.valueOf(total));
                return spotDto;
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
