package org.example.ggg.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AttendanceDto {

    private final StringProperty attendanceId;
    private final StringProperty employeeId;
    private final StringProperty date;
    private final StringProperty status;
    private final StringProperty remarks;

    public AttendanceDto(String attendanceId, String employeeId, String date, String status, String remarks) {
        this.attendanceId = new SimpleStringProperty(attendanceId);
        this.employeeId = new SimpleStringProperty(employeeId);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);
        this.remarks = new SimpleStringProperty(remarks);
    }

    public StringProperty attendanceIdProperty() {
        return attendanceId;
    }

    public String getAttendanceId() {
        return attendanceId.get();
    }

    public void setAttendanceId(String value) {
        attendanceId.set(value);
    }

    public StringProperty employeeIdProperty() {
        return employeeId;
    }

    public String getEmployeeId() {
        return employeeId.get();
    }

    public void setEmployeeId(String value) {
        employeeId.set(value);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String value) {
        date.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public String getRemarks() {
        return remarks.get();
    }

    public void setRemarks(String value) {
        remarks.set(value);
    }
    @Override
    public String toString() {
        return "AttendanceDto{" +
                "attendanceId='" + attendanceId.get() + '\'' +
                ", employeeId='" + employeeId.get() + '\'' +
                ", date='" + date.get() + '\'' +
                ", status='" + status.get() + '\'' +
                ", remarks='" + remarks.get() + '\'' +
                '}';
    }
}
