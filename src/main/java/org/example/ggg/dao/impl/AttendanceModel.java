package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.AttendanceDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {
    public static String addAttendance(AttendanceDto attendanceDto) {
        String sql = "INSERT INTO Attendance (EmployeeID, Date, Status, Remarks) VALUES (?, ?, ?, ?)";
        try {
            boolean isAdded = CrudUtil.execute(sql,
                    attendanceDto.getEmployeeId(),
                    attendanceDto.getDate(),
                    attendanceDto.getStatus(),
                    attendanceDto.getRemarks()
            );
            return isAdded ? "Successfully added attendance!" : "Failed to add attendance!";
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                return "Attendance for this EmployeeID already exists!";
            }
            return "Database Error: " + e.getMessage();
        } catch (ClassNotFoundException e) {
            return "Driver Not Found: " + e.getMessage();
        }
    }

    public static String updateAttendance(AttendanceDto attendanceDto) {
        String sql = "UPDATE Attendance SET EmployeeID = ?, Date = ?, Status = ?, Remarks = ? WHERE AttendanceID = ?";
        try {
            boolean isUpdated = CrudUtil.execute(sql,
                    attendanceDto.getEmployeeId(),
                    attendanceDto.getDate(),
                    attendanceDto.getStatus(),
                    attendanceDto.getRemarks(),
                    attendanceDto.getAttendanceId()
            );
            return isUpdated ? "Successfully updated attendance!" : "Failed to update attendance!";
        } catch (SQLException | ClassNotFoundException e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String deleteAttendance(String attendanceId) {
        String sql = "DELETE FROM Attendance WHERE AttendanceID = ?";
        try {
            boolean isDeleted = CrudUtil.execute(sql, attendanceId);
            return isDeleted ? "Successfully deleted attendance!" : "Failed to delete attendance!";
        } catch (SQLException | ClassNotFoundException e) {
            return "Error: " + e.getMessage();
        }
    }

    public static ArrayList<AttendanceDto> getAllAttendance() {
        String sql = "SELECT * FROM Attendance";
        ArrayList<AttendanceDto> attendanceList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.executeQuery(sql);
            while (resultSet.next()) {
                attendanceList.add(new AttendanceDto(
                        resultSet.getString("AttendanceID"),
                        resultSet.getString("EmployeeID"),
                        resultSet.getString("Date"),
                        resultSet.getString("Status"),
                        resultSet.getString("Remarks")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return attendanceList;
    }

    public static List<String> getAllEmployeeIds() {
        String sql = "SELECT EmployeeID FROM Employees";
        List<String> employeeIds = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.executeQuery(sql);
            while (resultSet.next()) {
                employeeIds.add(resultSet.getString("EmployeeID"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employeeIds;
    }

    public static List<AttendanceDto> searchAttendance(String keyword) {
        String sql = "SELECT * FROM Attendance WHERE EmployeeID LIKE ? OR Date LIKE ? OR Status LIKE ? OR Remarks LIKE ?";
        List<AttendanceDto> searchResults = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
            while (resultSet.next()) {
                searchResults.add(new AttendanceDto(
                        resultSet.getString("AttendanceID"),
                        resultSet.getString("EmployeeID"),
                        resultSet.getString("Date"),
                        resultSet.getString("Status"),
                        resultSet.getString("Remarks")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
