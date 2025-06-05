package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.EmployeeSalaryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeSalaryModel {

    // Save Employee Salary
    public static String saveEmployeeSalary(EmployeeSalaryDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Employees_Salary (EmployeeID, EmployeeName, MonthlySalary) VALUES (?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                dto.getEmployeeId(),
                dto.getEmployeeName(),
                dto.getMonthlySalary()
        );
        return isAdded ? "Successfully added employee salary!" : "Failed to add employee salary!";
    }

    // Update Employee Salary
    public static String updateEmployeeSalary(EmployeeSalaryDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Employees_Salary SET EmployeeName = ?, MonthlySalary = ? WHERE EmployeeID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                dto.getEmployeeName(),
                dto.getMonthlySalary(),
                dto.getEmployeeId()
        );
        return isUpdated ? "Successfully updated employee salary!" : "Failed to update employee salary!";
    }

    // Delete Employee Salary
    public static String deleteEmployeeSalary(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Employees_Salary WHERE EmployeeID = ?";
        boolean isDeleted = CrudUtil.execute(sql, employeeId);
        return isDeleted ? "Successfully deleted employee salary!" : "Failed to delete employee salary!";
    }

    // Get All Employee Salaries
    public static ArrayList<EmployeeSalaryDto> getAllEmployeeSalaries() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employees_Salary";
        ResultSet resultSet = CrudUtil.executeQuery(sql); // Assuming `CrudUtil.executeQuery` returns ResultSet
        ArrayList<EmployeeSalaryDto> employeeSalaries = new ArrayList<>();

        while (resultSet.next()) {
            employeeSalaries.add(new EmployeeSalaryDto(
                    resultSet.getString("EmployeeID"),
                    resultSet.getString("EmployeeName"),
                    resultSet.getString("MonthlySalary")
            ));
        }
        return employeeSalaries;
    }
}
