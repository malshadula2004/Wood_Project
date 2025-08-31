package org.example.ggg.model;

public class TaskAssignmentsDto {
    private String taskId;
    private String employeeId;
    private String taskDescription;
    private String assignedDate;
    private String dueDate;
    private String status;

    public TaskAssignmentsDto() {
    }

    public TaskAssignmentsDto(String taskId, String employeeId, String taskDescription, String assignedDate, String dueDate, String status) {
        this.taskId = taskId;
        this.employeeId = employeeId;
        this.taskDescription = taskDescription;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskAssignmentsDto{" +
                "taskId='" + taskId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", assignedDate='" + assignedDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
