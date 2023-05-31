package com.emsi.scientist_manage.dao.impl;

import com.emsi.scientist_manage.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private Connection conn= DB.getConnection();

    @Override
    public boolean verifyPassword(String username, String password) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT password FROM user WHERE name = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
