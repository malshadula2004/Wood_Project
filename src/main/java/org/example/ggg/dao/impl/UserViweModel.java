package org.example.ggg.dao.impl;

import org.example.ggg.dbconnection.DBConnection;
import org.example.ggg.model.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserViweModel {

    public static boolean deleteUser(String userName) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM Users WHERE Username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userName);

        return statement.executeUpdate() > 0;
    }

    public static ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT Username, Password, Role FROM Users";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        ArrayList<UserDto> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new UserDto(
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Role")
            ));
        }
        return users;
    }
}
