package com.JhayKrish.dao;

import com.JhayKrish.db.DbConnection;
import com.JhayKrish.models.Category;
import com.JhayKrish.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDao {

    private final Connection connection;

    public CategoryDao() {
        this.connection = DbConnection.getConnection();
    }
    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.SELECT_CATEGORY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Category category = new Category(Integer.parseInt(resultSet.getString("id")),resultSet.getString("categoryName"));
                categories.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    public void addCategory(String categoryName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.INSERT_CATEGORY);
            preparedStatement.setString(1,categoryName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCategory(int categoryId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.DELETE_CATEGORY);
            preparedStatement.setInt(1,categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
