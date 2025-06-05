package org.example.ggg.model;

import org.example.ggg.dbconnection.DBConnection;
import org.example.ggg.dto.EmployeesDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeesModel {

    // Save an employee to the database
    public static String saveEmployee(EmployeesDto employeesDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Employees (Name, Role, ContactInfo) VALUES (?, ?, ?)";
        try (java.sql.Connection connection = DBConnection.getInstance().getConnection();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeesDto.getName());
            statement.setString(2, employeesDto.getRole());
            statement.setString(3, employeesDto.getContactInfo());
            return statement.executeUpdate() > 0 ? "Successfully added employee!" : "Failed to add employee!";
        }
    }



    // Update an existing employee in the database
    public static String updateEmployee(EmployeesDto employeesDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Employees SET Name = ?, Role = ?, ContactInfo = ? WHERE EmployeeID = ?";
        try (java.sql.Connection connection = DBConnection.getInstance().getConnection();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeesDto.getName()); // Name update
            statement.setString(2, employeesDto.getRole()); // Role update
            statement.setString(3, employeesDto.getContactInfo()); // ContactInfo update
            statement.setInt(4, Integer.parseInt(employeesDto.getEmployeesId())); // EmployeeID filter

            return statement.executeUpdate() > 0 ? "Successfully updated employee!" : "Failed to update employee!";
        }
    }


    // Delete an employee from the database
    public static String deleteEmployee(String employeesId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Employees WHERE EmployeeID = ?";
        try (java.sql.Connection connection = DBConnection.getInstance().getConnection();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(employeesId));

            return statement.executeUpdate() > 0 ? "Successfully deleted employee!" : "Failed to delete employee!";
        }
    }

    // Search for a specific employee in the database
    public static EmployeesDto searchEmployee(String employeesId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employees WHERE EmployeeID = ?";
        try (java.sql.Connection connection = DBConnection.getInstance().getConnection();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(employeesId));
            java.sql.ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new EmployeesDto(
                        String.valueOf(resultSet.getInt("EmployeeID")),
                        resultSet.getString("Name"),
                        resultSet.getString("Role"),
                        resultSet.getString("ContactInfo")
                );
            }
        }
        return null; // Employee not found
    }

    // Retrieve all employees from the database
    public static ArrayList<EmployeesDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employees";
        try (java.sql.Connection connection = DBConnection.getInstance().getConnection();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql);
             java.sql.ResultSet resultSet = statement.executeQuery()) {

            ArrayList<EmployeesDto> employeesList = new ArrayList<>();
            while (resultSet.next()) {
                employeesList.add(new EmployeesDto(
                        String.valueOf(resultSet.getInt("EmployeeID")),
                        resultSet.getString("Name"),
                        resultSet.getString("Role"),
                        resultSet.getString("ContactInfo")
                ));
            }
            return employeesList;
        }
    }
}
