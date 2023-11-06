package com.JhayKrish.dao;

import com.JhayKrish.db.DbConnection;
import com.JhayKrish.models.Role;
import com.JhayKrish.models.User;
import com.JhayKrish.utils.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final Connection connection;

    public UserDao() {
        this.connection = DbConnection.getConnection();
    }

    public User validateUser(String email, String password) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.SELECT_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Role role = new Role(Integer.parseInt(resultSet.getString("roleId")),resultSet.getString("roleName"));
                user = new User(Integer.parseInt(resultSet.getString("id")), resultSet.getString("email"), resultSet.getString("userPassword"),
                        role);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public boolean getUser(String email) {
        boolean isExistingUser=false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.SELECT_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                if(email.trim().equals(resultSet.getString("email")))
                {
                    isExistingUser=true;break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExistingUser;

    }

    public void registerUser(String email, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryUtil.REGISTER_USER);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            preparedStatement.setInt(3,2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
