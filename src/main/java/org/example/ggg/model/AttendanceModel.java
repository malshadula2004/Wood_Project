package org.example.ggg.model;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.dto.AttendanceDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceModel {

    // Save Attendance
    // Save Attendance (without AttendanceID)
    public static String addAttendance(AttendanceDto attendanceDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Attendance (EmployeeID, Date, Status, Remarks) VALUES (?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                attendanceDto.getEmployeeId(),
                attendanceDto.getDate(),
                attendanceDto.getStatus(),
                attendanceDto.getRemarks()
        );
        return isAdded ? "Successfully added attendance!" : "Failed to add attendance!";
    }

    // Update Attendance
    public static String updateAttendance(AttendanceDto attendanceDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Attendance SET EmployeeID = ?, Date = ?, Status = ?, Remarks = ? WHERE AttendanceID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                attendanceDto.getEmployeeId(),
                attendanceDto.getDate(),
                attendanceDto.getStatus(),
                attendanceDto.getRemarks(),
                attendanceDto.getAttendanceId()
        );
        return isUpdated ? "Successfully updated attendance!" : "Failed to update attendance!";
    }

    // Delete Attendance
    public static String deleteAttendance(String attendanceId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Attendance WHERE AttendanceID = ?";
        boolean isDeleted = CrudUtil.execute(sql, attendanceId);
        return isDeleted ? "Successfully deleted attendance!" : "Failed to delete attendance!";
    }

    // Get all Attendance records
    public static ArrayList<AttendanceDto> getAllAttendance() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Attendance";
        ResultSet resultSet = CrudUtil.executeQuery(sql); // Assuming `CrudUtil.executeQuery` returns ResultSet
        ArrayList<AttendanceDto> attendanceList = new ArrayList<>();

        while (resultSet.next()) {
            attendanceList.add(new AttendanceDto(
                    resultSet.getString("AttendanceID"),
                    resultSet.getString("EmployeeID"),
                    resultSet.getString("Date"),
                    resultSet.getString("Status"),
                    resultSet.getString("Remarks")
            ));
        }
        return attendanceList;
    }
}
