package com.JhayKrish.dao;

import com.JhayKrish.db.DbConnection;
import com.JhayKrish.models.Cart;
import com.JhayKrish.models.Category;
import com.JhayKrish.models.Product;
import com.JhayKrish.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.JhayKrish.utils.UserUtil.getLoggedInUser;

public class CartDao {

    private final Connection connection;

    public CartDao() {
        this.connection = DbConnection.getConnection();
    }
    public ArrayList<Cart> getCartItems(int userId) {
        ArrayList<Cart> carts = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.SELECT_USER_CART);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Category category = new Category(Integer.parseInt(resultSet.getString("categoryId")),resultSet.getString("categoryName"));
                Product product = new Product(Integer.parseInt(resultSet.getString("productId")),category,resultSet.getString("productName"),Double.parseDouble(resultSet.getString("price")));
                Cart cart = new Cart(Integer.parseInt(resultSet.getString("id")),getLoggedInUser(),product,Integer.parseInt(resultSet.getString("count")));
                carts.add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carts;
    }


    public void AddItemToCart(int productId, int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.INSERT_CART_ITEM);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            preparedStatement.setInt(3,1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateCartItem(int productId, int userId) {
        int count = getItemCount(productId,userId);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.UPDATE_CART_ITEM);
            preparedStatement.setInt(1,count+1);
            preparedStatement.setInt(2,userId);
            preparedStatement.setInt(3,productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getItemCount(int productId, int userId) {
        int count =0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.SELECT_ITEM_COUNT);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                count= Integer.parseInt(resultSet.getString("count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }


    public void updateCart(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.UPDATE_CART_ORDER);
            preparedStatement.setInt(1,userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
