package com.example.demo11.sevice;

import com.example.demo11.model.User;

import java.sql.SQLException;

public interface IUserDAO {
    void addUser(User user) throws SQLException, ClassNotFoundException;
}
