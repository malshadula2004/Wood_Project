package org.example.ggg.dao.impl;

import org.example.ggg.dbconnection.DBConnection;
import org.example.ggg.model.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {

    public static String Sign(UserDto userDto) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        if (userDto.getJobRole().equals("Owner")) {
            String checkOwnerSql = "SELECT COUNT(*) FROM Users WHERE Role = 'Owner'";
            PreparedStatement checkOwnerStmt = connection.prepareStatement(checkOwnerSql);
            ResultSet rs = checkOwnerStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return "An Owner is already registered!";
            }
        }

        if (userDto.getJobRole().equals("Accountant")) {
            String checkAccountantSql = "SELECT COUNT(*) FROM Users WHERE Role = 'Accountant'";
            PreparedStatement checkAccountantStmt = connection.prepareStatement(checkAccountantSql);
            ResultSet rs = checkAccountantStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return "An Accountant is already registered!";
            }
        }

        if (userDto.getJobRole().equals("Cashier")) {
            String checkCashierSql = "SELECT COUNT(*) FROM Users WHERE Role = 'Cashier'";
            PreparedStatement checkCashierStmt = connection.prepareStatement(checkCashierSql);
            ResultSet rs = checkCashierStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return "A Cashier is already registered!";
            }
        }

        String sql = "INSERT INTO Users (Username, Password, Role) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, userDto.getUserName());
        statement.setString(2, userDto.getPassword());
        statement.setString(3, userDto.getJobRole());

        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "User successfully registered!" : "Failed to register user!";
    }

    public static ArrayList<UserDto> getAllUsers() throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Users";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        ArrayList<UserDto> userDtos = new ArrayList<>();
        while (rs.next()) {
            userDtos.add(new UserDto(
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Role")
            ));
        }
        return userDtos;
    }

    public static UserDto searchUser(String userName) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Users WHERE Username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userName);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            return new UserDto(
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Role")
            );
        }
        return null; // Return null if no user is found
    }
}
