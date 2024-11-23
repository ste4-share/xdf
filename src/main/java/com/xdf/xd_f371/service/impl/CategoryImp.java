package com.xdf.xd_f371.service.impl;

import com.xdf.xd_f371.dto.AssignmentCategory;
import com.xdf.xd_f371.dto.TitleDto;
import com.xdf.xd_f371.entity.Category;
import com.xdf.xd_f371.model.QDatabase;
import com.xdf.xd_f371.service.CategoryService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryImp implements CategoryService {
    @Override
    public List<Category> getAll() {
        QDatabase.getConnectionDB();
        List<Category> result = new ArrayList<>();


        String SQL_SELECT = "Select * from category";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String headerLv1 = resultSet.getString("header_lv1");
                String headerLv2 = resultSet.getString("header_lv2");
                String headerLv3 = resultSet.getString("header_lv3");
                String typeTitle = resultSet.getString("type_title");
                int tructhuoc_id = resultSet.getInt("tructhuoc_id");
                String code = resultSet.getString("code");
                Category category = new Category();
                category.setId(id);
                category.setHeader_lv1(headerLv1);
                category.setHeader_lv2(headerLv2);
                category.setHeader_lv3(headerLv3);
                category.setType_title(typeTitle);
                category.setTructhuoc_id(tructhuoc_id);
                category.setCode(code);
                result.add(category);
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
    public List<AssignmentCategory> getAllAssCategory() {
        QDatabase.getConnectionDB();
        List<AssignmentCategory> result = new ArrayList<>();
        String SQL_SELECT = "Select * from category_assignment";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String title_1 = resultSet.getString("title_1");
                String title_2 = resultSet.getString("title_2");
                String title_3 = resultSet.getString("title_3");
                String title_4 = resultSet.getString("title_4");
                String code = resultSet.getString("code");
                AssignmentCategory category = new AssignmentCategory();
                category.setId(id);
                category.setTitle1(title_1);
                category.setTitle2(title_2);
                category.setTitle3(title_3);
                category.setTitle4(title_4);
                category.setCode(code);
                result.add(category);
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
    public List<String> getAllTypeTitle() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();


        String SQL_SELECT = "select type_title from category group by type_title";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("type_title"));
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
    public List<String> getAllCode() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();


        String SQL_SELECT = "select code from category group by code";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("code"));
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
    public List<String> getAll_Header1() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String SQL_SELECT = "select header_lv1 from category group by header_lv1";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("header_lv1"));
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
    public List<String> getAll_Header2() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String SQL_SELECT = "select header_lv2 from category group by header_lv2";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("header_lv2"));
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
    public List<String> getAll_Header3() {
        QDatabase.getConnectionDB();
        List<String> result = new ArrayList<>();
        String SQL_SELECT = "select header_lv3 from category group by header_lv3";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("header_lv3"));
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
    public int create(Category category) {
        String SQL_SELECT = "begin transaction;insert into category(header_lv1,header_lv2,header_lv3,type_title, tructhuoc_id,code) values(?,?,?,?,?,?);commit;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, category.getHeader_lv1());
            preparedStatement.setString(2, category.getHeader_lv2());
            preparedStatement.setString(3, category.getHeader_lv3());
            preparedStatement.setString(4, category.getType_title());
            preparedStatement.setInt(5, category.getTructhuoc_id());
            preparedStatement.setString(6, category.getCode());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateAndDoNotConflic(Category category) {
        String SQL_SELECT = "insert into category(header_lv1, header_lv2,header_lv3,type_title,tructhuoc_id,code) \n" +
                "values(?,?,?,?,?,?) on conflict do nothing;";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, category.getHeader_lv1());
            preparedStatement.setString(2, category.getHeader_lv2());
            preparedStatement.setString(3, category.getHeader_lv3());
            preparedStatement.setString(4, category.getType_title());
            preparedStatement.setInt(5, category.getTructhuoc_id());
            preparedStatement.setString(6, category.getCode());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Category category) {
        String SQL_SELECT = "update category set header_lv1=?,header_lv2=?,header_lv3=?,type_title=?,tructhuoc_id=? where id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, category.getHeader_lv1());
            preparedStatement.setString(2, category.getHeader_lv2());
            preparedStatement.setString(3, category.getHeader_lv3());
            preparedStatement.setInt(4, category.getTructhuoc_id());
            preparedStatement.setInt(5,category.getId());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateType(Category category) {
        String SQL_SELECT = "update category set type_title=?, code=? where tructhuoc_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, category.getType_title());
            preparedStatement.setString(2, category.getCode());
            preparedStatement.setInt(3,category.getTructhuoc_id());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(int category_id) {
        return 0;
    }

    @Override
    public Category getTitleByttLpId(int tructhuoc_id, String type) {
        QDatabase.getConnectionDB();


        String SQL_SELECT = "Select * from category where tructhuoc_id=? and type_title=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tructhuoc_id);
            preparedStatement.setString(2, type);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String headerLv1 = resultSet.getString("header_lv1");
                String headerLv2 = resultSet.getString("header_lv2");
                String headerLv3 = resultSet.getString("header_lv3");
                String typeTitle = resultSet.getString("type_title");
                int tructhuocId = resultSet.getInt("tructhuoc_id");
                String code = resultSet.getString("code");
                return new Category(id,headerLv1,headerLv2,headerLv3,typeTitle,tructhuocId, code);
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
    public Category findByCode(String code, String type) {
        QDatabase.getConnectionDB();
        String SQL_SELECT = "Select * from category where code=? and type_title=?";
        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String headerLv1 = resultSet.getString("header_lv1");
                String headerLv2 = resultSet.getString("header_lv2");
                String headerLv3 = resultSet.getString("header_lv3");
                String typeTitle = resultSet.getString("type_title");
                int tructhuocId = resultSet.getInt("tructhuoc_id");
                String code2 = resultSet.getString("code");
                return new Category(id,headerLv1,headerLv2,headerLv3,typeTitle,tructhuocId, code2);
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
    public List<Category> getCategoryByTructhuocId(int tructhuocId) {
        QDatabase.getConnectionDB();
        List<Category> list = new ArrayList<>();

        String SQL_SELECT = "Select * from category where tructhuoc_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tructhuocId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String headerLv1 = resultSet.getString("header_lv1");
                String headerLv2 = resultSet.getString("header_lv2");
                String headerLv3 = resultSet.getString("header_lv3");
                String typeTitle = resultSet.getString("type_title");
                int tructhuoc_id = resultSet.getInt("tructhuoc_id");
                String code = resultSet.getString("code");
                list.add(new Category(headerLv1,headerLv2,headerLv3,typeTitle,tructhuoc_id, code));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int deleteBytructhuocId(int tructhuocId) {
        String SQL_SELECT = "delete from category where tructhuoc_id=?";

        // auto close connection and preparedStatement
        try {
            PreparedStatement preparedStatement = QDatabase.conn.prepareStatement(SQL_SELECT);
            preparedStatement.setInt(1, tructhuocId);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
