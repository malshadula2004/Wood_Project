package org.example.ggg.model;

public class EmployeesDto {

    private String employeesId;
    private String name;
    private String role;
    private String contactInfo;

    public EmployeesDto() {}

    public EmployeesDto(String employeesId, String name, String role, String contactInfo) {

        this.employeesId = employeesId;
        this.name = name;
        this.role = role;
        this.contactInfo = contactInfo;

    }

    public String getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(String employeesId) {
        this.employeesId = employeesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "EmployeesDto{" +
                "employeesId='" + employeesId + '\'' +
                ", name='" + name + '\'' +
                ", Role='" + role + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}
