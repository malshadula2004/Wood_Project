package org.example.ggg.dto;

public class EmployeeSalaryDto {
    private String employeeId;
    private String employeeName;
    private String monthlySalary;

    public EmployeeSalaryDto() {
    }

    public EmployeeSalaryDto(String employeeId, String employeeName, String monthlySalary) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.monthlySalary = monthlySalary;
    }

    // Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getMonthlySalary() {
        return monthlySalary;
    }

    // Setters
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setMonthlySalary(String monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    // toString Method
    @Override
    public String toString() {
        return "EmployeeSalaryDto{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", monthlySalary='" + monthlySalary + '\'' +
                '}';
    }
}
