package org.example.ggg.dao.impl;

import org.example.ggg.Util.CrudUtil;
import org.example.ggg.model.TaskAssignmentsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskAssignmentsModel {

    public static String saveTaskAssignment(TaskAssignmentsDto taskAssignmentDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO TaskAssignments (EmployeeID, TaskDescription, AssignedDate, DueDate, Status) VALUES (?, ?, ?, ?, ?)";
        boolean isAdded = CrudUtil.execute(sql,
                taskAssignmentDto.getEmployeeId(),
                taskAssignmentDto.getTaskDescription(),
                taskAssignmentDto.getAssignedDate(),
                taskAssignmentDto.getDueDate(),
                taskAssignmentDto.getStatus()
        );
        return isAdded ? "Task assignment successfully added!" : "Failed to add task assignment.";
    }

    public static String updateTaskAssignment(TaskAssignmentsDto taskAssignmentDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE TaskAssignments SET EmployeeID = ?, TaskDescription = ?, AssignedDate = ?, DueDate = ?, Status = ? WHERE TaskID = ?";
        boolean isUpdated = CrudUtil.execute(sql,
                taskAssignmentDto.getEmployeeId(),
                taskAssignmentDto.getTaskDescription(),
                taskAssignmentDto.getAssignedDate(),
                taskAssignmentDto.getDueDate(),
                taskAssignmentDto.getStatus(),
                taskAssignmentDto.getTaskId()
        );
        return isUpdated ? "Task assignment successfully updated!" : "Failed to update task assignment.";
    }

    public static String deleteTaskAssignment(String taskId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM TaskAssignments WHERE TaskID = ?";
        boolean isDeleted = CrudUtil.execute(sql, taskId);
        return isDeleted ? "Task assignment successfully deleted!" : "Failed to delete task assignment.";
    }

    public static ArrayList<TaskAssignmentsDto> getAllTaskAssignments() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM TaskAssignments";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<TaskAssignmentsDto> taskAssignments = new ArrayList<>();
        while (resultSet.next()) {
            taskAssignments.add(new TaskAssignmentsDto(
                    resultSet.getString("TaskID"),
                    resultSet.getString("EmployeeID"),
                    resultSet.getString("TaskDescription"),
                    resultSet.getString("AssignedDate"),
                    resultSet.getString("DueDate"),
                    resultSet.getString("Status")
            ));
        }
        return taskAssignments;
    }

    public static ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT EmployeeID FROM Employees";
        ResultSet resultSet = CrudUtil.executeQuery(sql);
        ArrayList<String> employeeIds = new ArrayList<>();
        while (resultSet.next()) {
            employeeIds.add(resultSet.getString("EmployeeID"));
        }
        return employeeIds;
    }
}
