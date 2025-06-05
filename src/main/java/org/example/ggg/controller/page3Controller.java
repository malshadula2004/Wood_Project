package org.example.ggg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.ggg.dto.UserDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class page3Controller {

    public AnchorPane loginPage;
    public TextField UserName;
    public PasswordField Password; // PasswordField for security
    public CheckBox CBOwner;
    public CheckBox CBCashier;
    public CheckBox CBAccountant;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/wood";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public void GoToDashboardActtion(ActionEvent actionEvent) throws IOException {
        String username = UserName.getText();
        String password = Password.getText();

        String role = null;
        if (CBOwner.isSelected()) {
            role = "Owner";
        } else if (CBCashier.isSelected()) {
            role = "Cashier";
        } else if (CBAccountant.isSelected()) {
            role = "Accountant";
        }

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Please fill in all fields and select a role.");
            return;
        }

        UserDto savedUser = getPasswordByUsername(username);

        if (savedUser != null && username.equalsIgnoreCase(savedUser.getUserName())
                && password.equals(savedUser.getPassword())
                && role.equalsIgnoreCase(savedUser.getJobRole())) {

            String fxmlPath = null;
            if (role.equalsIgnoreCase("Owner")) {
                fxmlPath = "/org/example/ggg/view/OwnerDashboard.fxml";
            } else if (role.equalsIgnoreCase("Cashier")) {
                fxmlPath = "/org/example/ggg/view/cashierDashboard.fxml";
            } else if (role.equalsIgnoreCase("Accountant")) {
                fxmlPath = "/org/example/ggg/view/AccountantDashboard.fxml";
            }

            if (fxmlPath != null) {
                AnchorPane dashboardPage = FXMLLoader.load(getClass().getResource(fxmlPath));
                Stage newStage = new Stage();
                Scene dashboardScene = new Scene(dashboardPage);

                newStage.setScene(dashboardScene);
                newStage.initStyle(javafx.stage.StageStyle.DECORATED);


                newStage.setWidth(1576);
                newStage.setHeight(860);

                // Optionally, you can center the window on screen
                newStage.centerOnScreen();

                newStage.show();

                Stage currentStage = (Stage) loginPage.getScene().getWindow();
                currentStage.close();
            }
        }}
            private UserDto getPasswordByUsername(String username) {
        UserDto userDto = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT username, password, role FROM Users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userDto = new UserDto();
                userDto.setUserName(resultSet.getString("username"));
                userDto.setPassword(resultSet.getString("password"));
                userDto.setJobRole(resultSet.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database.");
        }
        return userDto;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
