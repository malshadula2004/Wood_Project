package org.example.ggg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class cashierController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/wood";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    @FXML
    public void updateCredentials(ActionEvent actionEvent) {
        String newUsername = txtUsername.getText();
        String newPassword = txtPassword.getText();

        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Both fields are required!").show();
            return;
        }

        // Update the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateQuery = "UPDATE Users SET Username = ?, Password = ? WHERE UserID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newUsername);
                preparedStatement.setString(2, newPassword); // You should hash this password in production
                preparedStatement.setInt(3, getLoggedInUserID()); // Fetch current user's ID

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Credentials updated successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update credentials. Please try again.").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    public void cancelAction(ActionEvent actionEvent) {
        txtUsername.clear();
        txtPassword.clear();
    }

    private int getLoggedInUserID() {
        // Replace this method with actual logic to fetch the currently logged-in user's ID
        return 1; // Example UserID
    }
}
