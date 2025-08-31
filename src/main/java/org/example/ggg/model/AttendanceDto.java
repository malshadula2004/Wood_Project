package org.example.ggg.model;

import javafx.beans.property.SimpleStringProperty;

public class AttendanceDto {
    private final SimpleStringProperty attendanceId;
    private final SimpleStringProperty employeeId;
    private final SimpleStringProperty date;
    private final SimpleStringProperty status;
    private final SimpleStringProperty remarks;

    public AttendanceDto(String attendanceId, String employeeId, String date, String status, String remarks) {
        this.attendanceId = new SimpleStringProperty(attendanceId == null ? "" : attendanceId);
        this.employeeId = new SimpleStringProperty(employeeId == null ? "" : employeeId);
        this.date = new SimpleStringProperty(date == null ? "" : date);
        this.status = new SimpleStringProperty(status == null ? "" : status);
        this.remarks = new SimpleStringProperty(remarks == null ? "" : remarks);
    }

    public SimpleStringProperty attendanceIdProperty() {
        return attendanceId;
    }

    public SimpleStringProperty employeeIdProperty() {
        return employeeId;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public SimpleStringProperty remarksProperty() {
        return remarks;
    }

    public String getAttendanceId() {
        return attendanceId.get();
    }

    public String getEmployeeId() {
        return employeeId.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getRemarks() {
        return remarks.get();
    }
}
