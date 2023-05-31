package com.emsi.scientist_manage.dao;

public interface UserDao {
    boolean verifyPassword(String username, String password);
}
