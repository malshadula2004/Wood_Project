package org.example.ggg.model;

public class UserDto {

    private String UserName;
    private String Password;
    private String JobRole;

    // Default constructor
    public UserDto() {}

    // Parameterized constructor
    public UserDto(String userName, String password, String jobRole) {
        this.UserName = userName;
        this.Password = password;
        this.JobRole = jobRole;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getJobRole() {
        return JobRole;
    }

    public void setJobRole(String jobRole) {
        this.JobRole = jobRole;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", JobRole='" + JobRole + '\'' +
                '}';
    }
}
