package com.emsi.scientist_manage.service;

import com.emsi.scientist_manage.dao.ScientistDao;
import com.emsi.scientist_manage.dao.UserDao;
import com.emsi.scientist_manage.dao.impl.ScientistDaoImpl;
import com.emsi.scientist_manage.dao.impl.UserDaoImpl;
import com.emsi.scientist_manage.entities.Scientist;

import java.util.List;

public class UserService {
    private static UserDao userDao = new UserDaoImpl();

    public static boolean verifyPassword(String username, String password) {
        return userDao.verifyPassword(username,password);
    }

}
