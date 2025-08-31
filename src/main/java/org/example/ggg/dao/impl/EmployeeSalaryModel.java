package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.EmployeeSalaryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeSalaryModel {

    public static String saveEmployeeSalary(EmployeeSalaryDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO EmployeeSalary (emp_id, name, monthly_salary) VALUES (?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                dto.getEmployeeId(),
                dto.getEmployeeName(),
                dto.getMonthlySalary()
        );
        return isAdded ? "Successfully added employee salary!" : "Failed to add employee salary!";
    }

    public static String updateEmployeeSalary(EmployeeSalaryDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE EmployeeSalary SET name = ?, monthly_salary = ? WHERE emp_id = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                dto.getEmployeeName(),
                dto.getMonthlySalary(),
                dto.getEmployeeId()
        );
        return isUpdated ? "Successfully updated employee salary!" : "Failed to update employee salary!";
    }

    public static String deleteEmployeeSalary(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM EmployeeSalary WHERE emp_id = ?";
        boolean isDeleted = CrudUtil.execute(sql, employeeId);
        return isDeleted ? "Successfully deleted employee salary!" : "Failed to delete employee salary!";
    }

    public static ArrayList<EmployeeSalaryDto> getAllEmployeeSalaries() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM EmployeeSalary";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<EmployeeSalaryDto> employeeSalaries = new ArrayList<>();

        while (resultSet.next()) {
            employeeSalaries.add(new EmployeeSalaryDto(
                    resultSet.getString("emp_id"),
                    resultSet.getString("name"),
                    resultSet.getString("monthly_salary")
            ));
        }
        return employeeSalaries;
    }

    public static int getDaysWorkedThisMonth(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) AS DaysWorked FROM Attendance WHERE EmployeeID = ? AND Status = 'Present' AND MONTH(Date) = MONTH(CURRENT_DATE()) AND YEAR(Date) = YEAR(CURRENT_DATE())";
        ResultSet rs = CrudUtil.executeQuery(sql, Integer.parseInt(employeeId));
        if (rs.next()) {
            return rs.getInt("DaysWorked");
        }
        return 0;
    }

    public static int getTaskCompletionDays(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(DATEDIFF(DueDate, AssignedDate) + 1) AS TaskDays FROM TaskAssignments WHERE EmployeeID = ? AND Status = 'Completed'";
        ResultSet rs = CrudUtil.executeQuery(sql, Integer.parseInt(employeeId));
        if (rs.next()) {
            return rs.getInt("TaskDays");
        }
        return 0;
    }

    public static int calculateTotalSalary(String employeeId) throws SQLException, ClassNotFoundException {
        int attendanceDays = getDaysWorkedThisMonth(employeeId);
        int taskDays = getTaskCompletionDays(employeeId);
        int totalWorkedDays = attendanceDays + taskDays;
        return totalWorkedDays * 1500;
    }

    public static String getEmployeeName(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT Name FROM Employees WHERE EmployeeID = ?";
        ResultSet rs = CrudUtil.executeQuery(sql, Integer.parseInt(employeeId));
        if (rs.next()) {
            return rs.getString("Name");
        }
        return "";
    }
}
